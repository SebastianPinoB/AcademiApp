package com.example.AcademiApp.service;

import com.example.AcademiApp.model.entities.AntecedenteAcademico;
import com.example.AcademiApp.model.entities.HojaVidaEstudiante;
import com.example.AcademiApp.model.request.AntecedenteAcademicoRequest;
import com.example.AcademiApp.repository.AntecedenteAcademicoRepository;
import com.example.AcademiApp.repository.HojaVidaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AntecedenteAcademicoService {

    @Autowired
    private AntecedenteAcademicoRepository antecedenteAcademicoRepository;
    @Autowired
    private HojaVidaRepository hojaVidaRepository;

    @Transactional
    public AntecedenteAcademico agregar(int hojaId, AntecedenteAcademicoRequest request) {
        HojaVidaEstudiante hoja = hojaVidaRepository.findById(hojaId)
                .orElseThrow(() -> new IllegalArgumentException(
                    "No existe hoja de vida con ID: " + hojaId
                ));

        if (antecedenteAcademicoRepository.existsByHojaVida_HojaIdAndAntAcaAnio(hojaId, request.getAntAcaAnio())) {
            throw new IllegalArgumentException(
                "Ya existe un antecedente académico para el año " + request.getAntAcaAnio()
            );
        }

        AntecedenteAcademico antecedente = new AntecedenteAcademico();
        antecedente.setAntAcaAnio(request.getAntAcaAnio());
        antecedente.setAntAcaPromGen(request.getAntAcaPromGen());
        antecedente.setAntAcaObs(request.getAntAcaObs());
        antecedente.setAntAcaCompor(request.getAntAcaCompor());
        antecedente.setHojaVida(hoja);

        return antecedenteAcademicoRepository.save(antecedente);
    }

    public List<AntecedenteAcademico> listarPorHoja(int hojaId) {
        return antecedenteAcademicoRepository.findByHojaVida_HojaId(hojaId);
    }

    @Transactional
    public AntecedenteAcademico actualizar(int antAcaId, AntecedenteAcademicoRequest request) {
        AntecedenteAcademico existente = antecedenteAcademicoRepository.findById(antAcaId)
                .orElseThrow(() -> new IllegalArgumentException(
                    "No se encontró antecedente académico con ID: " + antAcaId
                ));

        existente.setAntAcaPromGen(request.getAntAcaPromGen());
        existente.setAntAcaObs(request.getAntAcaObs());
        existente.setAntAcaCompor(request.getAntAcaCompor());

        return antecedenteAcademicoRepository.save(existente);
    }
}