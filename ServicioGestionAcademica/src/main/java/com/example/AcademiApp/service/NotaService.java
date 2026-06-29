package com.example.AcademiApp.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.AcademiApp.model.entities.Evaluacion;
import com.example.AcademiApp.model.entities.Nota;
import com.example.AcademiApp.model.request.NotaRequest;
import com.example.AcademiApp.repository.EvaluacionRepository;
import com.example.AcademiApp.repository.NotaRepository;

@Service
public class NotaService {

    @Autowired
    private NotaRepository notaRepository;
    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Transactional
    public Nota crearNota(NotaRequest request) {
        Evaluacion evaluacion = evaluacionRepository.findById(request.getEvaluacionId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Evaluación no encontrada con ID: " + request.getEvaluacionId()));

        Nota nota = new Nota();
        nota.setNotaValor(request.getNotaValor());
        nota.setNotaFecha(request.getNotaFecha());
        nota.setEstudianteId(request.getEstudianteId());
        nota.setEvaluacion(evaluacion);

        return notaRepository.save(nota);
    }

    public List<Nota> obtenerTodas() {
        return notaRepository.findAll();
    }

    public List<Nota> obtenerPorEvaluacion(int evaId) {
        return notaRepository.findByEvaluacionEvaId(evaId);
    }

    public List<Nota> obtenerPorEstudiante(int estudianteId) {
        return notaRepository.findByEstudianteId(estudianteId);
    }

    public Nota obtenerPorId(int id) {
        return notaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nota no encontrada con ID: " + id));
    }

    @Transactional
    public Nota actualizarNota(int id, NotaRequest request) {
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nota no encontrada con ID: " + id));

        Evaluacion evaluacion = evaluacionRepository.findById(request.getEvaluacionId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Evaluación no encontrada con ID: " + request.getEvaluacionId()));

        nota.setNotaValor(request.getNotaValor());
        nota.setNotaFecha(request.getNotaFecha());
        nota.setEstudianteId(request.getEstudianteId());
        nota.setEvaluacion(evaluacion);

        return notaRepository.save(nota);
    }

    @Transactional
    public void eliminarNota(int id) {
        if (!notaRepository.existsById(id)) {
            throw new IllegalArgumentException("Nota no encontrada con ID: " + id);
        }
        notaRepository.deleteById(id);
    }
}