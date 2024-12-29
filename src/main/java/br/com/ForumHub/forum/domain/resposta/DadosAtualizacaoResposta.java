package br.com.ForumHub.forum.domain.resposta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoResposta(
        @NotNull
        Long idResposta,

        @NotBlank
        String mensagem
) {
}
