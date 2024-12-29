package br.com.ForumHub.forum.domain.topico;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoTopico(
        @NotNull
        Long id,

        String titulo,

        String mensagem,

        Status status_topico,

        Long idAutor,

        Long idCurso
) {
}
