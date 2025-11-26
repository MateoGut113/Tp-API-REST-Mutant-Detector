package com.example.Mutantes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de control de ADNs")
                        .description("Esta API permite analizar AND y definir si es mutante o humano. " +
                                "Un humano es considerado mutante si se encuentran más de una secuencia de cuatro letras iguales (A, T, C, G) " +
                                "en dirección horizontal, vertical o diagonal en su matriz de ADN NxN.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Mateo Gutierrez")
                                .email("mateo.gutierrez@example.edu.ar")
                                .url("https://mateogut.edu.ar"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Servidor Local de Desarrollo"),
                        new Server().url("https://api-rest-mutantdetector.onrender.com").description("Servidor de Producción (Render)")));
    }

}
