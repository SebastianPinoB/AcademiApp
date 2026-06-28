package com.example.AcademiApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.AcademiApp.model.Entities.Usuario;
import com.example.AcademiApp.repository.UsuarioRepository;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
   @Autowired
   private UsuarioRepository usuarioRepository;

   @Override
   public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      // Buscamos al usuario en la BD
      Usuario usuario = usuarioRepository.findByUsuEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

      // Retornamos el objeto User que espera Spring Security
      // ROLE_ + rol que tengas en tu BD (ej: "ADMIN" -> "ROLE_ADMIN")
      return new org.springframework.security.core.userdetails.User(
            usuario.getUsuEmail(),
            usuario.getUsu_pass(),
            Collections.singletonList(
                  new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + usuario.getRole().toUpperCase())));
   }
}
