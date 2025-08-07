package com.harmonia.store.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Harmonia Music Store API")
                        .description("API REST completa para gestión de una tienda de instrumentos musicales. " +
                                   "Incluye funcionalidades avanzadas como caché, búsquedas optimizadas, " +
                                   "SQL plano, estructuras de datos avanzadas y análisis estadísticos.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Jaime Villamizar")
                                .email("developer@harmonia.com")
                                .url("https://github.com/harmonia-music-store"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de desarrollo"),
                        new Server()
                                .url("https://api.harmonia.com")
                                .description("Servidor de producción")
                ));
    }
} 