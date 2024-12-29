package br.com.ForumHub.forum.controller;


import br.com.ForumHub.forum.domain.perfil.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/perfil")
@SecurityRequirement(name = "bearer-key")
public class PerfilController {

    @Autowired
    private PerfilRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPerfil dados, UriComponentsBuilder uriBuilder){
        var perfil = new Perfil(dados);
        repository.save(perfil);

        var uri = uriBuilder.path("/perfil/{id}").buildAndExpand(perfil.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoPerfil(perfil));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody DadosAtualizacaoPerfil dados){
        var perfil = repository.getReferenceById(dados.id());
        perfil.atualizar(dados);
        return ResponseEntity.ok().body(new DadosDetalhamentoPerfil(perfil));
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoPerfil>> listar(@PageableDefault(sort = "id") Pageable paginacao){
        var perfis = repository.findByAtivoTrue(paginacao).map(DadosDetalhamentoPerfil::new);
        return ResponseEntity.ok().body(perfis);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        var perfil = repository.getReferenceById(id);
        return ResponseEntity.ok().body(new DadosDetalhamentoPerfil(perfil));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity desativar(@PathVariable Long id){
        var perfil = repository.getReferenceById(id);
        perfil.desativar();
        return ResponseEntity.noContent().build();
    }
}
