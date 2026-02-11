package com.mb.api_consultorio.domain.paciente;

import com.mb.api_consultorio.domain.direccion.DatosDireccion;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroPaciente(
        @NotBlank
        String nombre,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String telefono,
        @NotBlank
        String documento,
        @NotNull @Valid DatosDireccion direccion
) {
}