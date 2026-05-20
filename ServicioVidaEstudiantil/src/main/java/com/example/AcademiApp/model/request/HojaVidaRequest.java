package com.example.AcademiApp.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HojaVidaRequest {
    
    @NotNull(message = "El ID del estudiante es obligatorio")
    private Integer estudianteId;

}
