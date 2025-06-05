package br.com.fiap.aquamind.repository;

import br.com.fiap.aquamind.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    /**
     * Usado em AuthController para verificar se já existe um usuário com aquele e-mail.
     */
    boolean existsByEmail(String email);

    /**
     * Usado pelo CustomUserDetailsService para carregar o usuário pelo e-mail.
     */
    Optional<Usuario> findByEmail(String email);
}
