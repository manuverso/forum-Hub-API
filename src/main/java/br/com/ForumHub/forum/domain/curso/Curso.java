package br.com.ForumHub.forum.domain.curso;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

@Table(name = "cursos")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    private boolean ativo;


    public void ativar() {
        this.ativo = true;
    }

    public void desativar() {
        this.ativo = false;
    }

    public Curso(@Valid DadosCadastroCurso dados) {
        this.nome = dados.nome();
        this.categoria = dados.categoria();
    }

    public void atualizar(@Valid DadosAtualizacaoCurso dados) {
        this.nome = dados.nome();
        this.categoria = dados.categoria();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public boolean isAtivo() {
        return ativo;
    }
}
