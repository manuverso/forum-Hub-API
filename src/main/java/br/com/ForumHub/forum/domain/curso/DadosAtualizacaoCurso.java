package br.com.ForumHub.forum.domain.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoCurso(
        @NotNull
        Long id,

        @NotBlank
        String nome,

        @NotNull
        Categoria categoria
) {
}