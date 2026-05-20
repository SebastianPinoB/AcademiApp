package com.example.AcademiApp.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MensajeIndividualRequest {

    @NotNull(message = "El emisor es obligatorio")
    @Positive
    private Integer msjIdEmisor;

    @NotNull(message = "El receptor es obligatorio")
    @Positive
    private Integer msjIdReceptor;

    @NotBlank(message = "El contenido no puede estar vacío")
    @Size(max = 1000, message = "El mensaje no puede superar los 1000 caracteres")
    private String msjContenido;
}