package br.com.ForumHub.forum.domain.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    Page<Usuario> findByAtivoTrue(Pageable paginacao);

    UserDetails findByLogin(String subject);

    boolean existsByLogin(String email);
}
