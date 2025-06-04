package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.exception.BadRequestException;
import br.com.fiap.aquamind.model.Usuario;
import br.com.fiap.aquamind.repository.UsuarioRepository;
import br.com.fiap.aquamind.security.CustomUserDetailsService;
import br.com.fiap.aquamind.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoints para autenticação (login e registro)
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // DTOs internos para receber/retornar dados
    public static class AuthRequest {
        public String email;
        public String senha;
    }
    public static class AuthResponse {
        public String token;
        public AuthResponse(String token) { this.token = token; }
    }
    public static class RegisterRequest {
        public String nome;
        public String email;
        public String senha;
        public String tipoUsuario; // ex.: "produtor" / "admin" / "tecnico"
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> fazerLogin(@RequestBody AuthRequest body) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(body.email, body.senha)
            );
        } catch (AuthenticationException ex) {
            throw new BadRequestException("Credenciais inválidas");
        }
        // Se chegou aqui, credenciais corretas → gerar token
        var userDetails = userDetailsService.loadUserByUsername(body.email);
        String token = jwtUtil.gerarToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    // POST /api/auth/register
    @PostMapping("/register")
    public ResponseEntity<Usuario> registrar(@RequestBody RegisterRequest req) {
        if (usuarioRepository.existsByEmail(req.email)) {
            throw new BadRequestException("Já existe usuário com esse e-mail");
        }
        Usuario u = new Usuario();
        u.setNome(req.nome);
        u.setEmail(req.email);
        u.setSenha(passwordEncoder.encode(req.senha));
        u.setTipoUsuario(req.tipoUsuario);
        u.setAtivo(true);
        usuarioRepository.save(u);
        return ResponseEntity.ok(u);
    }
}
