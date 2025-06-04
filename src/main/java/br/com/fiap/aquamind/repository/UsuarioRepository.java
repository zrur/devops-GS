package br.com.fiap.aquamind.repository;

import br.com.fiap.aquamind.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    /**
     * Método usado no AuthController para verificar existência de e-mail
     */
    boolean existsByEmail(String email);

    /**
     * Método usado internamente pelo Spring Security (CustomUserDetailsService) para carregar o usuário por e-mail
     */
    Optional<Usuario> findByEmail(String email);
}
