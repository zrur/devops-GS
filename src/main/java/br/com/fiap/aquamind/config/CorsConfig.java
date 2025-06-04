package br.com.fiap.aquamind.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuração global de CORS para permitir requests de outras origens.
 *
 * Caso você queira restringir apenas ao domínio do seu frontend (por ex. http://localhost:3000),
 * basta trocar o valor de allowedOrigins para aquele domínio específico.
 */
@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")           // aplica CORS em todas as rotas
                        .allowedOrigins("*")         // permite requests de qualquer origem
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(false)     // se você precisar enviar cookies ou autoriz. via cabeçalho, coloque true
                        .maxAge(3600);               // 1 hora em cache para pré‐flight
            }
        };
    }
}
