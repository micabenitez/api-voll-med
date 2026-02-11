package com.mb.api_consultorio.domain.medico;

import com.mb.api_consultorio.domain.direccion.DatosDireccion;
import jakarta.validation.constraints.NotNull;

public record DatosActualizacionMedico(
    @NotNull Long id,
    String nombre,
    String telefono,
    DatosDireccion direccion
) {
}
