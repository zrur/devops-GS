package br.com.fiap.aquamind.security;

import br.com.fiap.aquamind.model.Usuario;
import br.com.fiap.aquamind.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Serviço que o Spring Security usa para carregar os dados do usuário com base no username (e-mail, aqui).
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca o usuário pelo e-mail. Se não encontrar, lança UsernameNotFoundException.
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuário não encontrado com e-mail: " + email)
                );

        // Cria um UserDetails (classe do Spring) com username, senha e lista de autoridades/vazias.
        // Se quiser atribuir "roles", pode converter o campo usuario.getTipoUsuario() em GrantedAuthority.
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .authorities(Collections.emptyList())
                .build();
    }
}
