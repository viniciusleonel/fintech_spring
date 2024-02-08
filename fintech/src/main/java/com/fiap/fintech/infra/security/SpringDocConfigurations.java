package com.fiap.fintech.infra.security;

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
                        .title("Fintech API")
                        .description("API Rest da aplicação Fintech, contendo as funcionalidades de CRUD de usuários com controle de autenticação usando Json Web Token.")
                        .contact(new Contact()
                                .name("Vinícius Leonel")
                                .url("https://www.linkedin.com/in/viniciuslps/")
                                .email("viniciuslps.cms@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://github.com/viniciusleonel/fintech_spring")));
    }
}
