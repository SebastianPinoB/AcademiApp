package com.example.AcademiApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.AcademiApp.models.dtos.ActaDTO;
import com.example.AcademiApp.models.dtos.CitaDTO;
import com.example.AcademiApp.models.entities.BitacoraCitaApoderado;
import com.example.AcademiApp.models.entities.BitacoraReuApoderados;
import com.example.AcademiApp.models.entities.BitacoraReuGeneral;
import com.example.AcademiApp.models.requests.ActaRequest;
import com.example.AcademiApp.models.requests.CitaIndividualRequest;
import com.example.AcademiApp.repository.BitacoraCitaApoderadoRepository;
import com.example.AcademiApp.repository.BitacoraReuApoderadosRepository;
import com.example.AcademiApp.repository.BitacoraReuGeneralRepository;

@Service
public class ReporteService {

    @Autowired
    private BitacoraReuGeneralRepository generalRepo;
    
    @Autowired
    private BitacoraReuApoderadosRepository apoderadosRepo;
    
    @Autowired
    private BitacoraCitaApoderadoRepository citaRepo;

    /**
     * 1. REGISTRAR REUNIÓN GENERAL 
     * Actas de Consejos de Profesores y reuniones de equipo directivo.
     */
    public ActaDTO registrarReunionGeneral(ActaRequest request) {
        
        // --- COMENTARIO DE CONEXIÓN: SERVICIO AUTH ---
        // Aquí se debería validar que el 'idFuncionario' existe y tiene el rol de 
        // Administrador (Actor 01) o Docente (Actor 02) antes de proceder
        
        BitacoraReuGeneral general = new BitacoraReuGeneral();
        general.setFecha(request.getFecha());
        general.setHora(request.getHora());
        general.setTemasTratados(request.getTemas());
        general.setAcuerdos(request.getAcuerdos());
        general.setObservaciones(request.getObservaciones());
        general.setIdFuncionario(request.getUsuIdResponsable()); // ID proveniente de Auth

        BitacoraReuGeneral guardada = generalRepo.save(general);
        
        return new ActaDTO(
            guardada.getIdReunGeneral(), 
            "GENERAL", 
            guardada.getFecha(), 
            guardada.getAcuerdos(), 
            false
        );
    }

    /**
     * 2. REGISTRAR REUNIÓN DE APODERADOS
     * Reuniones colectivas de curso presididas por el profesor jefe.
     */
    public ActaDTO registrarReunionApoderados(ActaRequest request) {
        
        // --- COMENTARIO DE CONEXIÓN: GESTIÓN ACADÉMICA ---
        // Validar que el 'idCurso' existe en el sistema 
        // Según el MERE, aquí se podrían heredar temas de una 'BitacoraReuGeneral'
        
        // --- COMENTARIO DE CONEXIÓN: SERVICIO AUTH ---
        // Validar que el funcionario responsable sea el profesor jefe asignado al curso.

        BitacoraReuApoderados apo = new BitacoraReuApoderados();
        apo.setFecha(request.getFecha());
        apo.setTemasTratados(request.getTemas());
        apo.setAcuerdos(request.getAcuerdos());
        apo.setCursoId(request.getCursoId()); // Referencia a Gestión Académica

        var guardada = apoderadosRepo.save(apo);
        
        return new ActaDTO(
            guardada.getIdBitacoraReuApoderados(), 
            "APODERADOS", 
            guardada.getFecha(),
            guardada.getAcuerdos(),
            false
        );
    }

    /**
     * 3. REGISTRAR CITA DE APODERADO 
     * Entrevistas individuales vinculadas a la Hoja de Vida Integral.
     */
    public CitaDTO registrarCitaIndividual(CitaIndividualRequest request) {
        
        // --- COMENTARIO DE CONEXIÓN: SERVICIO AUTH/ESTUDIANTES ---
        // Validar que el 'idEstudiante' existe y tiene matrícula vigente
        // Obtener la dirección actualizada ('usu_dir') del apoderado para el reporte oficial 

        // --- COMENTARIO DE CONEXIÓN: VIDA ESTUDIANTIL ---
        // Al finalizar el guardado, se debe gatillar un evento para que esta cita 
        // aparezca en la Hoja de Vida Integral del alumno

        BitacoraCitaApoderado cita = new BitacoraCitaApoderado();
        cita.setFecha(request.getFecha());
        cita.setTemasTratados(request.getTemas());
        cita.setAcuerdos(request.getAcuerdos()); // Compromisos obligatorios
        cita.setObservaciones(request.getObservaciones());
        cita.setUsuId(request.getUsuIdEstudiante()); // ID proveniente de Auth

        // --- COMENTARIO DE SEGURIDAD: FIRMA ELECTRÓNICA ---
        // Validar el 'pinFirma' mediante el Servicio Auth. Si es correcto, 
        // el registro se guarda y se bloquea para ediciones futuras
        boolean esFirmado = (request.getPinFirma() != null); 

        var guardada = citaRepo.save(cita);
        
        return new CitaDTO(
            guardada.getIdBitacoraCitaApoderado(), 
            guardada.getFecha(), 
            guardada.getUsuId(), 
            guardada.getAcuerdos(), 
            esFirmado
        );
    }
}