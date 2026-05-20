package com.example.AcademiApp.service;

import com.example.AcademiApp.model.entities.Mensajeria;
import com.example.AcademiApp.model.request.MensajeColectivoRequest;
import com.example.AcademiApp.model.request.MensajeIndividualRequest;
import com.example.AcademiApp.repository.MensajeriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MensajeriaService {

    @Autowired
    private MensajeriaRepository mensajeriaRepository;

    @Transactional
    public Mensajeria enviarIndividual(MensajeIndividualRequest request) {
        if (request.getMsjIdEmisor().equals(request.getMsjIdReceptor())) {
            throw new IllegalArgumentException("El emisor y receptor no pueden ser el mismo usuario.");
        }

        Mensajeria mensaje = new Mensajeria();
        mensaje.setMsjIdEmisor(request.getMsjIdEmisor());
        mensaje.setMsjIdReceptor(request.getMsjIdReceptor());
        mensaje.setMsjContenido(request.getMsjContenido());
        mensaje.setMsjFechaEnvio(LocalDateTime.now());
        mensaje.setMsjTipo("INDIVIDUAL");
        mensaje.setMsjEstado("ENTREGADO");

        return mensajeriaRepository.save(mensaje);
    }

    @Transactional
    public Mensajeria enviarColectivo(MensajeColectivoRequest request) {
        Mensajeria mensaje = new Mensajeria();
        mensaje.setMsjIdEmisor(request.getMsjIdEmisor());
        mensaje.setCursoId(request.getCursoId());
        mensaje.setMsjContenido(request.getMsjContenido());
        mensaje.setMsjFechaEnvio(LocalDateTime.now());
        mensaje.setMsjTipo("COLECTIVO");
        mensaje.setMsjEstado("ENTREGADO");

        return mensajeriaRepository.save(mensaje);
    }

    public List<Mensajeria> bandejEntrada(int receptorId) {
        return mensajeriaRepository.findByMsjIdReceptor(receptorId);
    }

    public List<Mensajeria> mensajesEnviados(int emisorId) {
        return mensajeriaRepository.findByMsjIdEmisor(emisorId);
    }

    public List<Mensajeria> conversacion(int emisorId, int receptorId) {
        return mensajeriaRepository.findByMsjIdEmisorAndMsjIdReceptor(emisorId, receptorId);
    }

    public List<Mensajeria> mensajesColectivosCurso(int cursoId) {
        return mensajeriaRepository.findByCursoId(cursoId);
    }

    public Mensajeria obtenerPorId(int id) {
        return mensajeriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                    "No se encontró mensaje con ID: " + id
                ));
    }

    @Transactional
    public Mensajeria marcarLeido(int id) {
        Mensajeria mensaje = obtenerPorId(id);
        mensaje.setMsjEstado("LEIDO");
        return mensajeriaRepository.save(mensaje);
    }

    public void eliminar(int id) {
        if (!mensajeriaRepository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró mensaje con ID: " + id);
        }
        mensajeriaRepository.deleteById(id);
    }
}