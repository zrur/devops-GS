package br.com.fiap.aquamind.config;

import br.com.fiap.aquamind.security.CustomUserDetailsService;
import br.com.fiap.aquamind.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuração do Spring Security para usar JWT e liberar corretamente as URLs do Swagger/OpenAPI.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Torna BCryptPasswordEncoder disponível como @Autowired em toda a aplicação.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Torna o AuthenticationManager disponível para injeção (necessário no AuthController).
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configura as regras de segurança HTTP, expõe endpoints públicos e registra o filtro JWT.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1) Desabilita CSRF (não usamos sessões stateful em nossa API REST)
                .csrf(csrf -> csrf.disable())

                // 2) Não criamos sessão, pois usamos JWT (STATELESS)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 3) Define quais endpoints são públicos e quais exigem autenticação
                .authorizeHttpRequests(auth -> auth
                        // libera todos os /api/auth/** (login, register, etc)
                        .requestMatchers("/api/auth/**").permitAll()

                        // —— LIBERAÇÃO PARA SWAGGER / SpringDoc ——
                        // OpenAPI JSON
                        .requestMatchers("/v3/api-docs", "/v3/api-docs/**").permitAll()
                        // YAML (se quiser)
                        .requestMatchers("/v3/api-docs.yaml", "/v3/api-docs.yaml/**").permitAll()

                        // Swagger UI estático e sub-rotas
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/index.html", "/swagger-ui/**").permitAll()

                        // Recursos estáticos (JavaScript, CSS, fontes) do Swagger: “webjars”
                        .requestMatchers("/webjars/**").permitAll()

                        // (Opcional) H2 Console
                        .requestMatchers("/h2-console/**").permitAll()

                        // quaisquer outras requisições devem estar autenticadas
                        .anyRequest().authenticated()
                )

                // 4) Se acesso a recurso protegido sem token (ou token inválido),
                //    devolve 401 (Unauthorized) em vez de redirecionar para login form
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage())
                        )
                )

                // 5) Registra nosso CustomUserDetailsService para autenticação
                .userDetailsService(userDetailsService)

                // 6) Desativa formulário de login padrão (só queremos usar JWT)
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults());

        // 7) Adiciona o filtro JWT ANTES do filtro padrão UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // 8) Caso use console H2, libere frameOptions para mesmo domínio
        http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }
}
