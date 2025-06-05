package br.com.fiap.aquamind.service;

import br.com.fiap.aquamind.exception.BadRequestException;
import br.com.fiap.aquamind.model.Usuario;
import br.com.fiap.aquamind.repository.UsuarioRepository;
import br.com.fiap.aquamind.security.CustomUserDetailsService;
import br.com.fiap.aquamind.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Serviço para lidar com registro e login de usuários.
 */
@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Realiza o login. Se as credenciais estiverem corretas, retorna um token JWT.
     * Caso contrário, lança BadRequestException.
     */
    public String login(String email, String senha) {
        try {
            // Tenta autenticar via Spring Security
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, senha)
            );
        } catch (AuthenticationException ex) {
            throw new BadRequestException("Credenciais inválidas");
        }

        // Se chegou aqui, credenciais corretas. Geramos token:
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return jwtUtil.gerarToken(userDetails);
    }

    /**
     * Registra um novo usuário. Se já existir outro com o mesmo e‐mail, lança BadRequestException.
     */
    public Usuario register(String nome, String email, String senha, String tipoUsuario) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new BadRequestException("Já existe usuário com esse e-mail");
        }
        Usuario u = new Usuario();
        u.setNome(nome);
        u.setEmail(email);
        u.setSenha(passwordEncoder.encode(senha));
        u.setTipoUsuario(tipoUsuario);
        u.setAtivo(true);
        return usuarioRepository.save(u);
    }

    /**
     * Pesquisa um usuário por e-mail. Retorna Optional<Usuario> (ou vazio se não achar).
     * (Pode ser usado em outros contextos, se necessário.)
     */
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
