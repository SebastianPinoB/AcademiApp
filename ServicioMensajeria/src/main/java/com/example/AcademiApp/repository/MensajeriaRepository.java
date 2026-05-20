package com.example.AcademiApp.repository;

import com.example.AcademiApp.model.entities.Mensajeria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MensajeriaRepository extends JpaRepository<Mensajeria, Integer> {

    //bandeja de entrada
    List<Mensajeria> findByMsjIdReceptor(int receptorId);

    //mensajes enviados
    List<Mensajeria> findByMsjIdEmisor(int emisorId);

    //conversacion entre dos usuarios
    List<Mensajeria> findByMsjIdEmisorAndMsjIdReceptor(int emisorId, int receptorId);

    //mensajes colectivos por curso
    List<Mensajeria> findByCursoId(int cursoId);
}