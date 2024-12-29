package br.com.ForumHub.forum.controller;


import br.com.ForumHub.forum.domain.curso.Categoria;
import br.com.ForumHub.forum.domain.curso.CursoRepository;
import br.com.ForumHub.forum.domain.resposta.RespostaRepository;
import br.com.ForumHub.forum.domain.resposta.RespostaService;
import br.com.ForumHub.forum.domain.topico.*;
import br.com.ForumHub.forum.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;

@RestController
@RequestMapping("/topico")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private RespostaService respostaService;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroTopico dados, UriComponentsBuilder uriBuilder){
        var topico = new Topico(dados);
        topico.setCurso(cursoRepository.getReferenceById(dados.idCurso()));
        topico.setAutor(usuarioRepository.getReferenceById(dados.idAutor()));

        if(topicoRepository.existsByTituloOrMensagem(dados.titulo(),dados.mensagem())) throw new ValidationException("Tópico já existente, insira outro título e mensagem e tente novamente.");
        System.out.println(topico);
        topicoRepository.save(topico);

        var uri = uriBuilder.path("/topico/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoTopico>> listar (@PageableDefault(size = 10,sort = "dataCriacao",direction = Sort.Direction.DESC) Pageable paginacao){
        var topicos = topicoRepository.findAll(paginacao).map(DadosDetalhamentoTopico::new);
        return ResponseEntity.ok().body(topicos);

    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar (@PathVariable Long id){
        var topico = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok().body(new DadosDetalhamentoTopico((topico)));

    }

    @DeleteMapping("/{id}")
    @Transactional
    public  ResponseEntity excluir(@PathVariable Long id){
        var topicoBuscado = topicoRepository.findById(id);
        if(topicoBuscado.isEmpty()) throw new ValidationException("Não existe um tópico com o ID informado.");

        topicoRepository.deleteById(topicoBuscado.get().getId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoTopico dados, @PathVariable Long id){
        var topicoBuscado = topicoRepository.findById(id);
        if(topicoBuscado.isEmpty()) throw new ValidationException("Não existe um tópico com o ID informado.");

        var topico = topicoBuscado.get();
        topico.atualizar(dados);
        if(dados.idAutor() != null) topico.setAutor(usuarioRepository.getReferenceById(dados.idAutor()));
        if(dados.idCurso() != null) topico.setCurso(cursoRepository.getReferenceById(dados.idCurso()));

        return ResponseEntity.ok().body(new DadosDetalhamentoTopico(topico));
    }

    @PostMapping("/responder={idResposta}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico>  responderTopico(@PathVariable Long idResposta){
        var resposta = respostaService.obterResposta(idResposta);
        resposta.responder();

        var topico = resposta.getTopico();
        topico.setStatusTopico(Status.RESPONDIDO);

        topicoRepository.save(topico);

        return ResponseEntity.ok().body(new DadosDetalhamentoTopico(topico));
    }

    @GetMapping("id={idTopico}/resposta")
    public ResponseEntity respostaTopico(Long idTopico){
        var topico = topicoRepository.findById(idTopico)
                .orElseThrow(() -> new ValidationException(String.format("Tópico com ID %d não existe.", idTopico)));

        var resposta = respostaService.obterRespostaTopicoRespondido(topico);

        return ResponseEntity.ok().body(new DadosDetalhamentoTopicoRespondido(topico,resposta));
    }


    @GetMapping("/curso/{nomeCurso}")
    public ResponseEntity<Page<DadosDetalhamentoTopico>> listarPorNomeDoCurso(@PathVariable String nomeCurso,Pageable paginacao){
        var topicos = topicoRepository.buscarTopicosPorNomeDoCurso(paginacao,nomeCurso).map(DadosDetalhamentoTopico::new);
        return ResponseEntity.ok().body(topicos);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<Page<DadosDetalhamentoTopico>> listarPorCategoriaDoCurso(@PathVariable String categoria, Pageable paginacao){
        var categoriaBuscada = Arrays.stream(Categoria.values()).filter(
                        c -> c.name().contains(categoria.toUpperCase()))
                .findFirst();

        if(!categoriaBuscada.isPresent()) throw new ValidationException("A categoria de curso informada não existe.");

        System.out.println(categoriaBuscada);

        var topicos = topicoRepository.buscarTopicosPorCategoria(paginacao,categoriaBuscada.get()).map(DadosDetalhamentoTopico::new);
        return ResponseEntity.ok().body(topicos);
    }

    @GetMapping("/ano/{ano}")
    public ResponseEntity<Page<DadosDetalhamentoTopico>> listarPorAnoCriacao(@PathVariable int ano, Pageable paginacao){
        var topicos = topicoRepository.buscarTopicosPorAnoDeCriacao(paginacao,ano).map(DadosDetalhamentoTopico::new);
        return ResponseEntity.ok().body(topicos);
    }
}
