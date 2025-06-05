package br.com.fiap.aquamind.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configurações do OpenAPI/Swagger para expor o botão de Authorize (Bearer JWT).
 *
 * Após rodar a aplicação, a documentação estará disponível em:
 *   - JSON:  /v3/api-docs
 *   - Swagger UI: /swagger-ui/index.html (ou /swagger-ui/index.html#/)
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Nome do esquema de segurança (irá aparecer no botão Authorize)
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                // 1) Informação básica da API (titulo, versão, contato etc.)
                .info(new Info()
                        .title("AquaMind API")
                        .version("v1")
                        .description("Documentação dos endpoints REST da aplicação AquaMind")
                        .contact(new Contact()
                                .name("Equipe AquaMind")
                                .email("suporte@aquamind.com.br")
                                .url("https://www.aquamind.com.br")
                        )
                )
                // 2) Declaração do componente (Scheme) de segurança JWT Bearer
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)           // Tipo: HTTP
                                        .scheme("bearer")                         // Esquema: bearer
                                        .bearerFormat("JWT")                      // Formato: JWT
                                        .in(SecurityScheme.In.HEADER)             // Onde passa o token: header
                                        .description("Insira o JWT no formato: Bearer {token}")
                        )
                )
                // 3) Exige que todas as operações usem o securitySchemeName ("bearerAuth")
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName));
    }

}
