package br.com.ForumHub.forum.domain.topico;


import br.com.ForumHub.forum.domain.curso.Curso;
import br.com.ForumHub.forum.domain.resposta.Resposta;
import br.com.ForumHub.forum.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "usuarios")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensagem;

    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_topico")
    private Status statusTopico;

    @ManyToOne
    @JoinColumn(name = "idAutor")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "idCurso")
    private Curso curso;

    @OneToMany(mappedBy = "topico",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Resposta> respostas = new ArrayList<>();

    private boolean ativo;


    public void ativar() {
        this.ativo = true;
    }


    public void desativar() {
        this.ativo = false;
    }



    public Topico(@Valid DadosCadastroTopico dados) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.statusTopico = Status.NAO_RESPONDIDO;
        this.dataCriacao = LocalDateTime.now();
    }

    public void atualizar(@Valid DadosAtualizacaoTopico d){
        if(d.titulo() != null) this.titulo = d.titulo();
        if(d.mensagem() != null) this.mensagem = d.mensagem();
        if(d.status_topico() != null) this.statusTopico = d.status_topico();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public Status getStatusTopico() {
        return statusTopico;
    }

    public Usuario getAutor() {
        return autor;
    }

    public Curso getCurso() {
        return curso;
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public void setStatusTopico(Status statusTopico) {
        this.statusTopico = statusTopico;
    }
}
