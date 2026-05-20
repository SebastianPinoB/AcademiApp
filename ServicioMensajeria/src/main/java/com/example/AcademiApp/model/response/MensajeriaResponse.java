package com.example.AcademiApp.model.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MensajeriaResponse {
    private int msjId;
    private int msjIdEmisor;
    private Integer msjIdReceptor;
    private Integer cursoId;
    private String msjContenido;
    private LocalDateTime msjFechaEnvio;
    private String msjTipo;
    private String msjEstado;
}