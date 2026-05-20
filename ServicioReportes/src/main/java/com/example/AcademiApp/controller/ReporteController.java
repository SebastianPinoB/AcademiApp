package com.example.AcademiApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AcademiApp.models.dtos.ActaDTO;
import com.example.AcademiApp.models.dtos.CitaDTO;
import com.example.AcademiApp.models.requests.ActaRequest;
import com.example.AcademiApp.models.requests.CitaIndividualRequest;
import com.example.AcademiApp.services.ReporteService;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService service;

    /**
     * REGISTRAR REUNIÓN GENERAL (RF-58)
     * Endpoint destinado a Consejos de Profesores y reuniones de equipo directivo.
     */
    @PostMapping("/reunion-general")
    public ResponseEntity<ActaDTO> crearReunionGeneral(@RequestBody ActaRequest request) {
        // --- COMENTARIO DE SEGURIDAD (RF-59) ---
        // El API Gateway debe validar que el token pertenece a un Administrador (Actor 01).
        // Este endpoint es restringido para personal directivo.
        
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarReunionGeneral(request));
    }

    /**
     * REGISTRAR REUNIÓN DE APODERADOS (RF-57)
     * Endpoint para reuniones colectivas por curso, presididas por el docente.
     */
    @PostMapping("/reunion-apoderados")
    public ResponseEntity<ActaDTO> crearReunionApoderados(@RequestBody ActaRequest request) {
        // --- COMENTARIO DE SEGURIDAD (RF-59) ---
        // Validar que el usuario sea Docente (Actor 02) con carga académica en el curso.
        
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarReunionApoderados(request));
    }

    /**
     * REGISTRAR CITA DE APODERADO (CU-REP-01)
     * Registro de entrevistas individuales vinculadas a la Hoja de Vida Integral.
     */
    @PostMapping("/cita-individual")
    public ResponseEntity<CitaDTO> crearCitaIndividual(@RequestBody CitaIndividualRequest request) {
        // --- COMENTARIO DE SEGURIDAD Y AUDITORÍA (RF-60, RF-61) ---
        // El sistema debe registrar automáticamente quién realiza el acta para la auditoría.
        // Se debe verificar el PIN de firma electrónica enviado en el request (RF-29).

        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarCitaIndividual(request));
    }

    /**
     * Opcional: Generar Reporte PDF (RF-30)
     * Endpoint para exportar los datos en formato oficial para fiscalización.
     */
    @GetMapping("/exportar/{tipo}/{id}")
    public ResponseEntity<?> descargarReporte(@PathVariable String tipo, @PathVariable Long id) {
        // --- COMENTARIO TÉCNICO ---
        // Este endpoint llamaría a un motor de renderizado (ej. iText) usando los datos 
        // del service y la dirección (usu_dir) obtenida del microservicio Auth.
        return ResponseEntity.ok("Archivo PDF generado exitosamente.");
    }
}