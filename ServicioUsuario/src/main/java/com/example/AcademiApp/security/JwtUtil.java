package com.example.AcademiApp.security;

import java.security.Key;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
   private static final String SECRET = "techstore-clavesecreta23-faltaban-caracteres"; // 32 caracteres
   private static final long EXPIRATION = 3600000; // en milisegundos ( 1 hora)

   private Key getKey() {
      return Keys.hmacShaKeyFor(SECRET.getBytes());
   }

   public String generarToken(String username, String role) {
    return Jwts.builder()
            .setSubject(username)
            .claim("role", role) // Esto guarda el rol dentro del token
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact();
}

   public String obtenerUsername(String token) {
      return Jwts.parserBuilder()
            .setSigningKey(getKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
   }

   public String obtenerRole(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(getKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .get("role", String.class);
}

   public boolean validarToken(String token) {
      try {
         Jwts.parserBuilder()
               .setSigningKey(getKey())
               .build()
               .parseClaimsJws(token);
         return true;
      } catch (JwtException e) {
         return false;
      }
   }
}
