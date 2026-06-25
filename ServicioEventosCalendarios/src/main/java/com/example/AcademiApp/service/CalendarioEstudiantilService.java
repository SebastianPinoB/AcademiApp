package com.example.AcademiApp.service;

import com.example.AcademiApp.model.entities.CalendarioEstudiantil;
import com.example.AcademiApp.model.request.CalendarioEstudiantilRequest;
import com.example.AcademiApp.repository.CalendarioEstudiantilRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class CalendarioEstudiantilService {

    @Autowired
    private CalendarioEstudiantilRepository calendarioRepository;

    @Autowired
    private UsuarioClientService usuarioClientService;

    @Transactional
    public CalendarioEstudiantil crear(CalendarioEstudiantilRequest request) {
        usuarioClientService.obtenerDocente(request.getDocenteId());

        if (request.getCalEstFecha().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha no puede ser en el pasado.");
        }

        CalendarioEstudiantil evento = new CalendarioEstudiantil();
        evento.setCursoId(request.getCursoId());
        evento.setAsignaturaId(request.getAsignaturaId());
        evento.setDocenteId(request.getDocenteId());
        evento.setCalEstFecha(request.getCalEstFecha());
        evento.setCalEstDescripcion(request.getCalEstDescripcion());
        evento.setCalEstOa(request.getCalEstOa());
        evento.setCalEstTipo(request.getCalEstTipo());

        return calendarioRepository.save(evento);
    }

    public List<CalendarioEstudiantil> listarTodos() {
        return calendarioRepository.findAll();
    }

    public CalendarioEstudiantil obtenerPorId(int id) {
        return calendarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                    "No se encontró evento con ID: " + id
                ));
    }

    public List<CalendarioEstudiantil> listarPorCurso(int cursoId) {
        return calendarioRepository.findByCursoId(cursoId);
    }

    public List<CalendarioEstudiantil> listarPorAsignatura(int asignaturaId) {
        return calendarioRepository.findByAsignaturaId(asignaturaId);
    }

    @Transactional
    public CalendarioEstudiantil actualizar(int id, CalendarioEstudiantilRequest request) {
        CalendarioEstudiantil existente = obtenerPorId(id);
        existente.setCalEstFecha(request.getCalEstFecha());
        existente.setCalEstDescripcion(request.getCalEstDescripcion());
        existente.setCalEstOa(request.getCalEstOa());
        existente.setCalEstTipo(request.getCalEstTipo());
        return calendarioRepository.save(existente);
    }

    public void eliminar(int id) {
        if (!calendarioRepository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró evento con ID: " + id);
        }
        calendarioRepository.deleteById(id);
    }
}