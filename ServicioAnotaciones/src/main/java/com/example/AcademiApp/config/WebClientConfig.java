package com.example.AcademiApp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient usuarioWeb(@Value("${services.usuario.url}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }

    @Bean
    public WebClient vidaEstudiantilWeb(@Value("${services.vidaestudiantil.url}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }

    @Bean
    public WebClient mensajeriaWeb(@Value("${services.mensajeria.url}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }
}