package br.com.ForumHub.forum.domain.perfil;

import jakarta.validation.constraints.NotNull;

public record DadosCadastroPerfil(
        @NotNull
        TipoPerfil nome
) {
}
