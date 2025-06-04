package br.com.fiap.aquamind.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro que lê o token JWT do header "Authorization" em cada requisição,
 * valida-o e, se válido, configura o contexto de segurança (SecurityContextHolder).
 * Deve ser anotado com @Component para que o Spring possa injetar JwtUtil e UserDetailsService.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService; // normalmente, o CustomUserDetailsService

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1) Extrai o header Authorization
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            String token = headerAuth.substring(7); // remove "Bearer "

            try {
                // 2) Extrai o username (e-mail) do token
                String username = jwtUtil.getUsernameFromToken(token);

                // 3) Se username não for null e não houver autenticação atual definida...
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // 4) Carrega UserDetails a partir do banco
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    // 5) Se o token for válido para esse userDetails, seta no SecurityContext
                    if (jwtUtil.validarToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities()
                                );
                        authToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            } catch (ExpiredJwtException ex) {
                // Aqui você pode logar que o token expirou, se quiser.
                // Mas não interrompemos a cadeia de filtros; ao final, se a rota exigir auth, virá 401.
            } catch (Exception ex) {
                // Qualquer outro erro de parsing / validação do token, apenas ignora a autenticação.
            }
        }

        // Continua a cadeia de filtros
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Faz com que o filtro pule qualquer rota que comece com "/api/auth/"
        // (por ex., "/api/auth/login", "/api/auth/register"), pois estas são públicas.
        String path = request.getServletPath();
        return path.startsWith("/api/auth/");
    }
}
