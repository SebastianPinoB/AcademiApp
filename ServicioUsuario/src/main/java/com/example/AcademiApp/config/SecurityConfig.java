package com.example.AcademiApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.AcademiApp.security.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
   @Autowired
   private JwtFilter jwtFilter;

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http)
         throws Exception {

      http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                  .requestMatchers("/api/auth/**").permitAll()
                  // Aquí definimos los roles para rutas específicas
                  // .requestMatchers("/api/alumnos/**").hasRole("ALUMNO")
                  // .requestMatchers("/api/profesores/**").hasRole("PROFESOR")
                  .requestMatchers("/registro/usuario/**").hasRole("ALUMNO")

                  // Cualquier otra cosa requiere estar autenticado
                  .anyRequest().authenticated());

      http
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

      return http.build();
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();

   }

   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
      return config.getAuthenticationManager();
   }
}
