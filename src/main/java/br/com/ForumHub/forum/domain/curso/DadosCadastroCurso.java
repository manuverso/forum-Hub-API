package br.com.ForumHub.forum.domain.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroCurso(
        @NotBlank
        String nome,

        @NotNull
        Categoria categoria
) {
}
