package com.example.AcademiApp.service;

import com.example.AcademiApp.model.entities.HojaVidaEstudiante;
import com.example.AcademiApp.model.request.HojaVidaRequest;
import com.example.AcademiApp.model.response.AntecedenteAcademicoResponse;
import com.example.AcademiApp.model.response.AntecedenteApoderadoResponse;
import com.example.AcademiApp.model.response.AntecedenteMedicoResponse;
import com.example.AcademiApp.model.response.HojaVidaResponse;
import com.example.AcademiApp.repository.HojaVidaRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HojaVidaService {

    @Autowired
    private UsuarioClientService usuarioClientService;

    @Transactional
    public HojaVidaEstudiante crear(HojaVidaRequest request) {
        // Verifica que el estudiante exista en el ServicioUsuario
        usuarioClientService.obtenerEstudiante(request.getEstudianteId());

        if (hojaVidaRepository.existsByEstudianteId(request.getEstudianteId())) {
            throw new IllegalArgumentException(
                "Ya existe una hoja de vida para el estudiante con ID: " + request.getEstudianteId()
            );
        }
        HojaVidaEstudiante nueva = new HojaVidaEstudiante();
        nueva.setEstudianteId(request.getEstudianteId());
        return hojaVidaRepository.save(nueva);
    }

    @Autowired
    private HojaVidaRepository hojaVidaRepository;

    public HojaVidaEstudiante obtenerPorEstudiante(int estudianteId) {
        return hojaVidaRepository.findByEstudianteId(estudianteId)
                .orElseThrow(() -> new IllegalArgumentException(
                    "No se encontró hoja de vida para el estudiante con ID: " + estudianteId
                ));
    }

    public HojaVidaEstudiante obtenerPorId(int hojaId) {
        return hojaVidaRepository.findById(hojaId)
                .orElseThrow(() -> new IllegalArgumentException(
                    "No se encontró hoja de vida con ID: " + hojaId
                ));
    }

    public List<HojaVidaEstudiante> listarTodas() {
        return hojaVidaRepository.findAll();
    }

    public HojaVidaResponse mapearAResponse(HojaVidaEstudiante hoja) {
        HojaVidaResponse response = new HojaVidaResponse();
        response.setHojaId(hoja.getHojaId());
        response.setEstudianteId(hoja.getEstudianteId());

        if (hoja.getAntecedenteMedico() != null) {
            AntecedenteMedicoResponse am = new AntecedenteMedicoResponse();
            am.setAntMedId(hoja.getAntecedenteMedico().getAntMedId());
            am.setAntMedEdad(hoja.getAntecedenteMedico().getAntMedEdad());
            am.setAntMedPeso(hoja.getAntecedenteMedico().getAntMedPeso());
            am.setAntMedAltura(hoja.getAntecedenteMedico().getAntMedAltura());
            am.setAntMedGrupoSang(hoja.getAntecedenteMedico().getAntMedGrupoSang());
            am.setAntMedPats(hoja.getAntecedenteMedico().getAntMedPats());
            am.setAntMedFarmaco(hoja.getAntecedenteMedico().getAntMedFarmaco());
            am.setAntMedObs(hoja.getAntecedenteMedico().getAntMedObs());
            response.setAntecedenteMedico(am);
        }

        response.setAntecedentesAcademicos(
            hoja.getAntecedentesAcademicos().stream().map(aa -> {
                AntecedenteAcademicoResponse r = new AntecedenteAcademicoResponse();
                r.setAntAcaId(aa.getAntAcaId());
                r.setAntAcaAnio(aa.getAntAcaAnio());
                r.setAntAcaPromGen(aa.getAntAcaPromGen());
                r.setAntAcaObs(aa.getAntAcaObs());
                r.setAntAcaCompor(aa.getAntAcaCompor());
                return r;
            }).toList()
        );

        response.setAntecedentesApoderado(
            hoja.getAntecedentesApoderado().stream().map(ap -> {
                AntecedenteApoderadoResponse r = new AntecedenteApoderadoResponse();
                r.setAntApoId(ap.getAntApoId());
                r.setApoderadoId(ap.getApoderadoId());
                r.setAntApoNumTelf(ap.getAntApoNumTelf());
                r.setAntApoMail(ap.getAntApoMail());
                r.setAntApoProfesion(ap.getAntApoProfesion());
                r.setAntApoLugarTrab(ap.getAntApoLugarTrab());
                return r;
            }).toList()
        );

        return response;
    }

}
