package br.com.ForumHub.forum.domain.perfil;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "perfis")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private TipoPerfil nome;

    private boolean ativo;


    public Perfil(@Valid DadosCadastroPerfil dados) {
        this.nome = dados.nome();
        ativo = true;
    }

    public void ativar() {
        this.ativo = true;
    }

    public void desativar() {
        this.ativo = false;
    }

    public void atualizar(DadosAtualizacaoPerfil dados) {
        this.nome = dados.nome();
    }

    public Long getId() {
        return id;
    }

    public TipoPerfil getNome() {
        return nome;
    }

    public boolean isAtivo() {
        return ativo;
    }
}
