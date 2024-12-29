package br.com.ForumHub.forum.controller;

import br.com.ForumHub.forum.domain.resposta.*;
import br.com.ForumHub.forum.domain.topico.TopicoRepository;
import br.com.ForumHub.forum.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/resposta")
@SecurityRequirement(name = "bearer-key")
public class RespostaController {

    @Autowired
    private RespostaService respostaService;

    @Autowired
    private RespostaRepository respostaRepository;


    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroResposta dados, UriComponentsBuilder uriBuilder){
        var resposta = respostaService.cadastrarResposta(dados);
        respostaRepository.save(resposta);

        var uri = uriBuilder.path("/{id}")
                .buildAndExpand(resposta.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoResposta(resposta));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoResposta dados){
        var resposta = respostaService.atualizarResposta(dados);
        respostaRepository.save(resposta);

        return ResponseEntity.ok().body(new DadosDetalhamentoResposta(resposta));
    }

    @GetMapping("/topico={idTopico}")
    public ResponseEntity<List<DadosDetalhamentoResposta>> listarRespostasPorTopico(@PathVariable Long idTopico){
        var respostas = respostaService.buscarRespostasPorTopico(idTopico);
        var detalhes = respostas.stream().map(DadosDetalhamentoResposta::new).toList();
        return ResponseEntity.ok().body(detalhes);
    }

    @DeleteMapping("/{idResposta}")
    @Transactional
    public ResponseEntity excluirResposta(@PathVariable Long idResposta){
        var resposta = respostaService.obterResposta(idResposta);
        resposta.desativar();
        return ResponseEntity.noContent().build();
    }
}
