package br.com.ForumHub.forum.domain.perfil;

public record DadosDetalhamentoPerfil(
        Long id,
        TipoPerfil nome,
        Boolean ativo
) {

    public DadosDetalhamentoPerfil(Perfil perfil) {
        this(perfil.getId(),perfil.getNome(),perfil.isAtivo());
    }
}
