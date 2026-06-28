package com.example.AcademiApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.AcademiApp.model.Entities.Usuario;
import com.example.AcademiApp.repository.UsuarioRepository;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verificamos si ya existe alguien para no duplicarlo cada vez que reinicias

        // ADMIN
        if (usuarioRepository.findByUsuEmail("admin@colegio.cl").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setUsuEmail("admin@colegio.cl");
            // Usamos el PasswordEncoder para que la contraseña esté cifrada
            admin.setUsu_pass(passwordEncoder.encode("123456"));
            admin.setRole("ADMIN"); // Asegúrate de que este rol coincida con lo que esperas

            usuarioRepository.save(admin);
            System.out.println("Usuario de prueba creado: admin@colegio.cl");
        }

    }
}
