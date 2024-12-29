package br.com.ForumHub.forum.domain.curso;

public record DadosDetalhamentoCurso(
        Long id,
        String nome,
        Categoria categoria
) {
    public DadosDetalhamentoCurso(Curso c){
        this(c.getId(),c.getNome(),c.getCategoria());
    }
}
