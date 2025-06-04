package br.com.fiap.aquamind.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configurações básicas do OpenAPI/Swagger usando Springdoc-OpenAPI.
 *
 * Após rodar a aplicação, a documentação estará disponível em:
 *   - JSON:  /v3/api-docs
 *   - Swagger UI: /swagger-ui.html (ou /swagger-ui/index.html)
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AquaMind API")
                        .version("v1")
                        .description("Documentação dos endpoints REST da aplicação AquaMind")
                        .contact(new Contact()
                                .name("Equipe AquaMind")
                                .email("suporte@aquamind.com.br")
                                .url("https://www.aquamind.com.br")
                        )
                );
    }
}
