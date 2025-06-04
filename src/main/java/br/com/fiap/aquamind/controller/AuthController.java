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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Endpoints para autenticação (login e registro).
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
        @NotBlank(message = "O e-mail não pode ficar em branco")
        @Email(message = "Formato de e-mail inválido")
        public String email;

        @NotBlank(message = "A senha não pode ficar em branco")
        public String senha;
    }

    public static class AuthResponse {
        public String token;
        public AuthResponse(String token) { this.token = token; }
    }

    public static class RegisterRequest {
        @NotBlank(message = "O nome não pode ficar em branco")
        public String nome;

        @NotBlank(message = "O e-mail não pode ficar em branco")
        @Email(message = "Formato de e-mail inválido")
        public String email;

        @NotBlank(message = "A senha não pode ficar em branco")
        public String senha;

        @NotBlank(message = "O tipo de usuário não pode ficar em branco")
        public String tipoUsuario; // ex.: "produtor" / "admin" / "tecnico"
    }

    // ---------------------- LOGIN ----------------------

    /**
     * POST /api/auth/login
     * Método para autenticação: tenta autenticar com AuthenticationManager. Se der certo,
     * chama userDetailsService.loadUserByUsername(...) e gera token com JwtUtil. Caso credenciais
     * estejam erradas, lança BadRequestException("Credenciais inválidas").
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> fazerLogin(@Valid @RequestBody AuthRequest body) {
        try {
            // 1) Tenta autenticar via AuthenticationManager (usa UserDetailsService internamente)
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(body.email, body.senha)
            );
        } catch (AuthenticationException ex) {
            // se credenciais estiverem erradas, cai aqui
            throw new BadRequestException("Credenciais inválidas");
        }

        // 2) Se chegou até aqui, as credenciais estão corretas → carregar UserDetails
        UserDetails userDetails = userDetailsService.loadUserByUsername(body.email);

        // 3) Gerar token JWT
        String token = jwtUtil.gerarToken(userDetails);

        // 4) Retornar o token dentro de um JSON: { "token": "xxxxx" }
        return ResponseEntity.ok(new AuthResponse(token));
    }

    // ---------------------- REGISTER ----------------------

    /**
     * POST /api/auth/register
     * Cria um novo usuário. Verifica duplicidade de e-mail.
     * Criptografa a senha com PasswordEncoder e salva no banco.
     */
    @PostMapping("/register")
    public ResponseEntity<Usuario> registrar(@Valid @RequestBody RegisterRequest req) {
        // 1) Verifica se já existe usuário com o mesmo e-mail
        if (usuarioRepository.existsByEmail(req.email)) {
            throw new BadRequestException("Já existe usuário com esse e-mail");
        }

        // 2) Monta um novo usuário
        Usuario u = new Usuario();
        u.setNome(req.nome);
        u.setEmail(req.email);
        u.setSenha(passwordEncoder.encode(req.senha));
        u.setTipoUsuario(req.tipoUsuario);
        u.setAtivo(true);

        // 3) Salvar no banco
        Usuario salvo = usuarioRepository.save(u);

        // 4) Retorna o usuário criado (você pode desejar não retornar a senha, mas aqui é só um exemplo)
        return ResponseEntity.ok(salvo);
    }
}
