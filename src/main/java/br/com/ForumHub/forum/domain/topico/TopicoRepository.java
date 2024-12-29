package br.com.ForumHub.forum.domain.topico;

import br.com.ForumHub.forum.domain.curso.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository extends JpaRepository<Topico,Long> {
    Page<Topico> findAllByAtivoTrue(Pageable paginacao);

    boolean existsByTituloOrMensagem(String titulo, String mensagem);

    @Query("""
        SELECT t FROM Topico t
        JOIN  t.curso c
        WHERE c.nome ILIKE %:nomeCurso%
    """)
    Page<Topico> buscarTopicosPorNomeDoCurso(Pageable paginacao, String nomeCurso);

    @Query("""
            SELECT t FROM Topico t
            JOIN t.curso c
            WHERE c.categoria = :categoria
            """)
    Page<Topico> buscarTopicosPorCategoria(Pageable paginacao, Categoria categoria);

    @Query("""
            SELECT t FROM Topico t
            WHERE YEAR(t.dataCriacao) = :ano
            """)
    Page<Topico> buscarTopicosPorAnoDeCriacao(Pageable paginacao, int ano);
}
