package com.example.AcademiApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.example.AcademiApp.model.Entities.Apoderado;
import com.example.AcademiApp.model.Entities.Estudiante;
import com.example.AcademiApp.model.dto.UsuarioDto;
import com.example.AcademiApp.model.request.CrearApoderado;
import com.example.AcademiApp.model.request.CrearEstudiante;
import com.example.AcademiApp.repository.ApoderadoRepository;
import com.example.AcademiApp.repository.EstudianteRepository;

import jakarta.transaction.Transactional;

@Service
public class EstudianteService {

   @Autowired
   public EstudianteRepository estudianteRepository;
   @Autowired
   public ApoderadoRepository apoderadoRepository;
   @Autowired
   private WebClient usuarioWebClient;

   public List<Estudiante> obtenerTodos() {
      return estudianteRepository.findAll();
   }

   public Estudiante obtenerPorId(int idEstudiante) {
        Estudiante estudiante = estudianteRepository.findById(idEstudiante).orElse(null);
        if (estudiante == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado");
        }
        return estudiante;
    }

   @Transactional
   public void registrarEstudianteCompleto(CrearEstudiante nuevoEst, CrearApoderado nuevoApo) {
      // Creacion apoderado
      UsuarioDto usuario = null;
      try {
         usuario = usuarioWebClient.get()
               .uri("/usuario/{idUsuario}", nuevoApo.getUsu_id())
               .retrieve()
               .bodyToMono(UsuarioDto.class)
               .block();

      } catch (Exception e) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ENDPOINT -> Usuario para apoderado no encontrado");
      }
      if (usuario == null) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario (Para apoderado) No encontrado");
      }

      Apoderado apoderado = new Apoderado();
      apoderado.setUsu_id(nuevoApo.getUsu_id());
      apoderado.setApode_parentesco(nuevoApo.getApode_parentesco());
      apoderado = apoderadoRepository.save(apoderado);

      // Creacion Estudiante una vez creado el apoderado
      usuario = null;
      try {
         usuario = usuarioWebClient.get()
               .uri("/usuario/{idUsuario}", nuevoEst.getUsu_id())
               .retrieve()
               .bodyToMono(UsuarioDto.class)
               .block();

      } catch (Exception e) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ENDPOINT -> Usuario para estudiante no encontrado");
      }
      if (usuario == null) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario (Para estudiante) No encontrado");
      }

      Estudiante estudiante = new Estudiante();
      estudiante.setUsu_id(nuevoEst.getUsu_id());
      estudiante.setEstu_parentesco(nuevoEst.getEstu_parentesco());
      estudiante.setApoderado(apoderado);

      estudianteRepository.save(estudiante);
   }

   public String eliminarEstudiante(int idUsuario) {
      if (estudianteRepository.existsById(idUsuario)) {
         estudianteRepository.deleteById(idUsuario);
         return "Usuario eliminado correctamente";
      } else {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
      }
   }
}
