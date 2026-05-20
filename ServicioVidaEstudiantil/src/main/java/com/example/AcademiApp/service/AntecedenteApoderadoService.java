package com.example.AcademiApp.service;

import com.example.AcademiApp.model.entities.AntecedenteApoderado;
import com.example.AcademiApp.model.entities.HojaVidaEstudiante;
import com.example.AcademiApp.model.request.AntecedenteApoderadoRequest;
import com.example.AcademiApp.repository.AntecedenteApoderadoRepository;
import com.example.AcademiApp.repository.HojaVidaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AntecedenteApoderadoService {

    @Autowired
    private AntecedenteApoderadoRepository antecedenteApoderadoRepository;
    @Autowired
    private HojaVidaRepository hojaVidaRepository;

    @Transactional
    public AntecedenteApoderado agregar(int hojaId, AntecedenteApoderadoRequest request) {
        HojaVidaEstudiante hoja = hojaVidaRepository.findById(hojaId)
                .orElseThrow(() -> new IllegalArgumentException(
                    "No existe hoja de vida con ID: " + hojaId
                ));

        if (antecedenteApoderadoRepository.existsByHojaVida_HojaIdAndApoderadoId(hojaId, request.getApoderadoId())) {
            throw new IllegalArgumentException(
                "El apoderado con ID " + request.getApoderadoId() + " ya está registrado en esta hoja de vida."
            );
        }

        AntecedenteApoderado antecedente = new AntecedenteApoderado();
        antecedente.setApoderadoId(request.getApoderadoId());
        antecedente.setAntApoNumTelf(request.getAntApoNumTelf());
        antecedente.setAntApoMail(request.getAntApoMail());
        antecedente.setAntApoProfesion(request.getAntApoProfesion());
        antecedente.setAntApoLugarTrab(request.getAntApoLugarTrab());
        antecedente.setHojaVida(hoja);

        return antecedenteApoderadoRepository.save(antecedente);
    }

    public List<AntecedenteApoderado> listarPorHoja(int hojaId) {
        return antecedenteApoderadoRepository.findByHojaVida_HojaId(hojaId);
    }

    @Transactional
    public AntecedenteApoderado actualizar(int antApoId, AntecedenteApoderadoRequest request) {
        AntecedenteApoderado existente = antecedenteApoderadoRepository.findById(antApoId)
                .orElseThrow(() -> new IllegalArgumentException(
                    "No se encontró antecedente de apoderado con ID: " + antApoId
                ));

        existente.setAntApoNumTelf(request.getAntApoNumTelf());
        existente.setAntApoMail(request.getAntApoMail());
        existente.setAntApoProfesion(request.getAntApoProfesion());
        existente.setAntApoLugarTrab(request.getAntApoLugarTrab());

        return antecedenteApoderadoRepository.save(existente);
    }

    public void eliminar(int antApoId) {
        if (!antecedenteApoderadoRepository.existsById(antApoId)) {
            throw new IllegalArgumentException("No se encontró antecedente de apoderado con ID: " + antApoId);
        }
        antecedenteApoderadoRepository.deleteById(antApoId);
    }
}