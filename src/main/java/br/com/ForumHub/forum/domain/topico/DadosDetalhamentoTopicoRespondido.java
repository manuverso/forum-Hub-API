package br.com.ForumHub.forum.domain.topico;

import br.com.ForumHub.forum.domain.curso.DadosDetalhamentoCurso;
import br.com.ForumHub.forum.domain.resposta.DadosDetalhamentoResposta;
import br.com.ForumHub.forum.domain.resposta.Resposta;
import br.com.ForumHub.forum.domain.usuario.DadosDetalhamentoUsuario;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopicoRespondido(
        Long id,
        String tiulo,
        String mensagem,
        LocalDateTime dataCriacao,
        Status status,
        DadosDetalhamentoUsuario autor,
        DadosDetalhamentoCurso curso,
        DadosDetalhamentoResposta resposta
) {

    public DadosDetalhamentoTopicoRespondido(Topico t, Resposta r) {
        this(
                t.getId(),
                t.getTitulo(),
                t.getMensagem(),
                t.getDataCriacao(),
                t.getStatusTopico(),
                new DadosDetalhamentoUsuario(t.getAutor()),
                new DadosDetalhamentoCurso(t.getCurso()),
                new DadosDetalhamentoResposta(r)
        );
    }
}
