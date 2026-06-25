package com.example.AcademiApp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient funcionarioWeb(@Value("${services.usuario.url}") String funcionarioUrl) {
        return WebClient.builder()
                .baseUrl(funcionarioUrl)
                .build();
    }

    @Bean
    public WebClient cursoWeb(@Value("${services.curso.url}") String cursoUrl) {
        return WebClient.builder()
                .baseUrl(cursoUrl)
                .build();
    }

    @Bean
    public WebClient alumnoWeb(@Value("${services.alumno.url}") String alumnoUrl) {
        return WebClient.builder()
                .baseUrl(alumnoUrl)
                .build();
    }
}
