package com.mb.api_consultorio.domain.paciente;

import com.mb.api_consultorio.domain.direccion.DatosDireccion;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarPaciente(
        @NotNull
        Long id,
        String nombre,
        String telefono,
        DatosDireccion direccion) {
}