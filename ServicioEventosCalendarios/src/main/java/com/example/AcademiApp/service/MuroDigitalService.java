package com.example.AcademiApp.service;

import com.example.AcademiApp.model.entities.MuroDigital;
import com.example.AcademiApp.model.request.MuroDigitalRequest;
import com.example.AcademiApp.repository.MuroDigitalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MuroDigitalService {

    @Autowired
    private MuroDigitalRepository muroDigitalRepository;

    @Autowired
    private UsuarioClientService usuarioClientService;

    @Transactional
    public MuroDigital publicar(MuroDigitalRequest request) {
        usuarioClientService.obtenerDocente(request.getDocenteId());

        MuroDigital publicacion = new MuroDigital();
        publicacion.setDocenteId(request.getDocenteId());
        publicacion.setAsignaturaId(request.getAsignaturaId());
        publicacion.setMuroConte(request.getMuroConte());
        publicacion.setMuroFecPubli(LocalDateTime.now());
        publicacion.setMuroTipoConte(request.getMuroTipoConte());
        return muroDigitalRepository.save(publicacion);
    }

    public List<MuroDigital> listarTodos() {
        return muroDigitalRepository.findAll();
    }

    public MuroDigital obtenerPorId(int id) {
        return muroDigitalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                    "No se encontró publicación con ID: " + id
                ));
    }

    public List<MuroDigital> listarPorAsignatura(int asignaturaId) {
        return muroDigitalRepository.findByAsignaturaId(asignaturaId);
    }

    public List<MuroDigital> listarPorDocente(int docenteId) {
        return muroDigitalRepository.findByDocenteId(docenteId);
    }

    @Transactional
    public MuroDigital actualizar(int id, MuroDigitalRequest request) {
        MuroDigital existente = obtenerPorId(id);
        existente.setMuroConte(request.getMuroConte());
        existente.setMuroTipoConte(request.getMuroTipoConte());
        return muroDigitalRepository.save(existente);
    }

    public void eliminar(int id) {
        if (!muroDigitalRepository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró publicación con ID: " + id);
        }
        muroDigitalRepository.deleteById(id);
    }
}