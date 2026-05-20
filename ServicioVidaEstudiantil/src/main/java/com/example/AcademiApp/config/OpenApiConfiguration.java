package com.example.AcademiApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class OpenApiConfiguration {
   @Bean
   public OpenAPI documentacioApi() {
      return new OpenAPI()
            .info(new Info()
                  .title("Gestion vida estudiantil")
               .description("Hoja de vida, antecedentes medicos y academicos de cada alumno junto a los antecedentes del apoderado"));
   }
}
