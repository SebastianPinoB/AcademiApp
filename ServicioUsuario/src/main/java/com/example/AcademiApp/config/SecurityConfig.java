package com.example.AcademiApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

      http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                  .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                  .requestMatchers("/api/auth/**").permitAll()
                  .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/api-docs/**", "/*.html").permitAll()

                  .requestMatchers(HttpMethod.GET, "/registro/alumno/**").permitAll()
                  .requestMatchers(HttpMethod.GET, "/registro/apoderado").permitAll()
                  .requestMatchers(HttpMethod.GET, "/registro/funcionario/**").permitAll()
                  .requestMatchers("/error").permitAll()

                  .requestMatchers(HttpMethod.POST, "/registro/alumno").hasRole("ADMIN")
                  .requestMatchers(HttpMethod.POST, "/registro/funcionario/**").hasRole("ADMIN")
                  .requestMatchers(HttpMethod.PUT, "/registro/funcionario/**").hasRole("ADMIN")
                  .requestMatchers(HttpMethod.DELETE, "/registro/funcionario/**").hasRole("ADMIN")
                  .requestMatchers(HttpMethod.PUT, "/registro/alumno/**").hasRole("ADMIN")
                  .requestMatchers(HttpMethod.DELETE, "/registro/alumno/**").hasRole("ADMIN")

                  .anyRequest().authenticated()
            );

      http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

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