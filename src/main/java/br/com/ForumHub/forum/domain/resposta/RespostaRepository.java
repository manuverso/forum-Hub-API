package br.com.ForumHub.forum.domain.resposta;

import br.com.ForumHub.forum.domain.topico.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {
    @Query("""
            SELECT r FROM Resposta r
            JOIN r.topico t
            WHERE t = :topico
            AND r.ativo = true
            """)
    List<Resposta> obterRespostasAtivasPorTopico(Topico topico);

    @Query("""
            SELECT r FROM Resposta r
            JOIN r.topico t
            WHERE t = :topico
            AND r.solucao = true
            """)
    Resposta obterRespostaRespondidaPorTopico(Topico topico);
}
