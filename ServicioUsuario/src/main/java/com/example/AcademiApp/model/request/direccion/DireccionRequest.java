package com.example.AcademiApp.model.request.direccion;

public record DireccionRequest(
            String calle,
            int numero,
            String letra,
            String nombreComuna,
            String nombreCiudad,
            String nombreRegion,
            String nombrePais) {
}
