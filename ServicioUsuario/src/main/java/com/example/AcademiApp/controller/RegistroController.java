package com.example.AcademiApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AcademiApp.model.request.RegistroRequest;
import com.example.AcademiApp.service.RegistroService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/registro")
public class RegistroController {

   @Autowired
   private RegistroService registroService;

   @PostMapping("")
   public ResponseEntity<String> registrar(@RequestBody RegistroRequest nuevoRegistro) {
      registroService.registrarAlumno(nuevoRegistro);
      return ResponseEntity.ok("Registro de apoderado y alumno exitoso.");
   }

}
