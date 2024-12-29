package br.com.ForumHub.forum.controller;


import br.com.ForumHub.forum.domain.perfil.PerfilRepository;
import br.com.ForumHub.forum.domain.usuario.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuario")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar (@RequestBody @Valid DadosCadastroUsuario dados, UriComponentsBuilder uriBuilder){
        var usuario = new Usuario(dados);
        var senha = encoder.encode(usuario.getPassword());
        usuario.setSenha(senha);

        var existente = usuarioRepository.existsByLogin(dados.email());
        if(existente) throw new ValidationException("Já existe um usuário com o email inserido.");

        usuario.setPerfil(perfilRepository.getReferenceById(dados.idPerfil()));
        usuarioRepository.save(usuario);

        var uri = uriBuilder.path("/curso/{id}").buildAndExpand(usuario.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoUsuario(usuario));
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoUsuario>> listar(@PageableDefault(sort = "id") Pageable paginacao){
        var usuarios = usuarioRepository.findByAtivoTrue(paginacao).map(DadosDetalhamentoUsuario::new);
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoUsuario dados){
        var usuario = usuarioRepository.getReferenceById(dados.id());
        usuario.atualizar(dados);
        if(dados.idPerfil() != null){
            usuario.setPerfil(perfilRepository.getReferenceById(dados.idPerfil()));
        }
        return  ResponseEntity.ok().body(new DadosDetalhamentoUsuario(usuario));
    }


    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        var usuario = usuarioRepository.getReferenceById(id);
        return ResponseEntity.ok().body(new DadosDetalhamentoUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity desativar(@PathVariable Long id){
        var usuario = usuarioRepository.getReferenceById(id);
        usuario.desativar();
        return ResponseEntity.noContent().build();
    }
}
