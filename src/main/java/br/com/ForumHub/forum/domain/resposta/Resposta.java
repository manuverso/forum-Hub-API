package br.com.ForumHub.forum.domain.resposta;

import br.com.ForumHub.forum.domain.topico.Topico;
import br.com.ForumHub.forum.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "respostas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idTopico")
    private Topico topico;

    private String mensagem;

    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "idAutor")
    private Usuario autor;

    private boolean solucao;

    private boolean ativo;

    public Resposta(@Valid DadosCadastroResposta dados) {
        this.mensagem = dados.mensagem();
        this.dataCriacao = LocalDateTime.now();
        this.solucao = false;
        this.ativo = true;
    }


    public void atualizar(@Valid DadosAtualizacaoResposta dados) {
        this.mensagem = dados.mensagem();
    }

    public void responder(){
        this.solucao = true;
    }

    public void ativar() {
        this.ativo = true;
    }

    public void desativar() {
        this.ativo = false;
    }

    public Long getId() {
        return id;
    }

    public Topico getTopico() {
        return topico;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public Usuario getAutor() {
        return autor;
    }

    public boolean isSolucao() {
        return solucao;
    }

    public boolean isAtivo() {
        return ativo;
    }


    public void setTopico(Topico topico) {
        this.topico = topico;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }
}
