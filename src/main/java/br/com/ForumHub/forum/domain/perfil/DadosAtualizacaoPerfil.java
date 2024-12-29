package br.com.ForumHub.forum.domain.perfil;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoPerfil(
        @NotNull
        Long id,

        @NotBlank
        TipoPerfil nome
) {
}
