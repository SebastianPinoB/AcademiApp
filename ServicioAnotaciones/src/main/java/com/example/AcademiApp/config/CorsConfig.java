package com.example.AcademiApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Aplica el CORS a todos los endpoints de tu API
                        .allowedOrigins("http://localhost:5173") // La URL exacta de tu frontend en Vite
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Los métodos HTTP de tu CRUD
                        .allowedHeaders("*") // Permite cualquier cabecera (incluyendo Authorization para futuros JWT)
                        .allowCredentials(true); // Permite el envío de cookies o credenciales de sesión
            }
        };
    }
}