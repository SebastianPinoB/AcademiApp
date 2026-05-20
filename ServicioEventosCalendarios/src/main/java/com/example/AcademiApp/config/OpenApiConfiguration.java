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
                  .title("Eventos y calendarios")
               .description("Gestionar el calendario estudiantil y el muro digital"));
   }
}
