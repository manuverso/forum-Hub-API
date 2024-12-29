package br.com.ForumHub.forum.domain.topico;

import br.com.ForumHub.forum.domain.curso.DadosDetalhamentoCurso;
import br.com.ForumHub.forum.domain.usuario.DadosDetalhamentoUsuario;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        Status status,
        DadosDetalhamentoUsuario usuario,
        DadosDetalhamentoCurso Curso
) {

    public DadosDetalhamentoTopico(Topico topico) {
        this(
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatusTopico(),
                new DadosDetalhamentoUsuario(topico.getAutor()),
                new DadosDetalhamentoCurso(topico.getCurso())
        );
    }
}
