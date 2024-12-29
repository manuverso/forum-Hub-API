package br.com.ForumHub.forum.domain.resposta;

import br.com.ForumHub.forum.domain.topico.DadosDetalhamentoTopico;
import br.com.ForumHub.forum.domain.usuario.DadosDetalhamentoUsuario;

import java.time.LocalDateTime;

public record DadosDetalhamentoResposta(
        Long id,
        String mensagem,
        DadosDetalhamentoTopico topico,
        LocalDateTime dataCriacao,
        DadosDetalhamentoUsuario autor,
        boolean solucao
) {

    public DadosDetalhamentoResposta(Resposta r) {
        this(
                r.getId(),
                r.getMensagem(),
                new DadosDetalhamentoTopico(r.getTopico()),
                r.getDataCriacao(),
                new DadosDetalhamentoUsuario(r.getAutor()),
                r.isSolucao()
        );
    }
}
