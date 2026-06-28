package com.example.AcademiApp.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.AcademiApp.model.Entities.Usuario;
import com.example.AcademiApp.repository.UsuarioRepository;

@Service
public class AuthService {
   @Autowired
   private UsuarioRepository usuarioRepository;

   public boolean validarUsuario(String email, String password) {
      // Buscamos el usuario en la BD por su email
      Optional<Usuario> usuarioOpt = usuarioRepository.findByUsuEmail(email);

      if (usuarioOpt.isPresent()) {
         Usuario usuario = usuarioOpt.get();
         // Si no las has encriptado, es una simple comparación (no recomendado)
         return usuario.getUsu_pass().equals(password);
      }
      return false;
   }
}
