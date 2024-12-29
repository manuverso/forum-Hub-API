package br.com.ForumHub.forum.infra.springDoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("FórumHub API")
                        .description("API Rest do ForumHub, contendo as funcionalidades de criação,atualização,exclusão e exibição de perfis,usuários,cursos, topicos e respostas")
                        .contact(new Contact()
                                .name("Emanuelle Santana")
                                .email("emanul01santana@gmail.com"))
                                .license(new License()
                                .name("Apache 2.0")
                                .url("http://forum-hub/api/licenca")));
    }
}
