package com.example.AcademiApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AcademiApp.model.entities.Curso;
import com.example.AcademiApp.model.request.CursoRequest;
import com.example.AcademiApp.service.CursoService;

@RestController
@RequestMapping("/cursos")
public class CursoController {

   @Autowired
   private CursoService cursoService;

   @PostMapping
   public ResponseEntity<Curso> crearCurso(@RequestBody CursoRequest request) {
      Curso nuevoCurso = cursoService.registrarCurso(request);
      return new ResponseEntity<>(nuevoCurso, HttpStatus.CREATED);
   }

}
