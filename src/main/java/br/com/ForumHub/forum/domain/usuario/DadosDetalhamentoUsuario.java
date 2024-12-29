package br.com.ForumHub.forum.domain.usuario;

public record DadosDetalhamentoUsuario(
        Long id,
        String nome,
        String email,
        Long idPerfil
) {

    public DadosDetalhamentoUsuario(Usuario usuario){
        this(usuario.getId(),usuario.getNome(),usuario.getLogin(),usuario.getPerfil().getId());
    }
}
