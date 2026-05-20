package com.example.AcademiApp.service;

import com.example.AcademiApp.model.entities.AntecedenteMedico;
import com.example.AcademiApp.model.entities.HojaVidaEstudiante;
import com.example.AcademiApp.model.request.AntecedenteMedicoRequest;
import com.example.AcademiApp.repository.AntecedenteMedicoRepository;
import com.example.AcademiApp.repository.HojaVidaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AntecedenteMedicoService {

    @Autowired
    private AntecedenteMedicoRepository antecedenteMedicoRepository;
    @Autowired
    private HojaVidaRepository hojaVidaRepository;

    @Transactional
    public AntecedenteMedico guardar(int hojaId, AntecedenteMedicoRequest request) {
        HojaVidaEstudiante hoja = hojaVidaRepository.findById(hojaId)
                .orElseThrow(() -> new IllegalArgumentException(
                    "No existe hoja de vida con ID: " + hojaId
                ));

        AntecedenteMedico antecedente = antecedenteMedicoRepository
                .findByHojaVida_HojaId(hojaId)
                .orElse(new AntecedenteMedico());

        antecedente.setAntMedEdad(request.getAntMedEdad());
        antecedente.setAntMedPeso(request.getAntMedPeso() != null ? request.getAntMedPeso() : 0);
        antecedente.setAntMedAltura(request.getAntMedAltura() != null ? request.getAntMedAltura() : 0);
        antecedente.setAntMedGrupoSang(request.getAntMedGrupoSang());
        antecedente.setAntMedPats(request.getAntMedPats());
        antecedente.setAntMedFarmaco(request.getAntMedFarmaco());
        antecedente.setAntMedObs(request.getAntMedObs());
        antecedente.setHojaVida(hoja);

        return antecedenteMedicoRepository.save(antecedente);
    }

    public AntecedenteMedico obtenerPorHoja(int hojaId) {
        return antecedenteMedicoRepository.findByHojaVida_HojaId(hojaId)
                .orElseThrow(() -> new IllegalArgumentException(
                    "No hay antecedente médico para la hoja con ID: " + hojaId
                ));
    }
}