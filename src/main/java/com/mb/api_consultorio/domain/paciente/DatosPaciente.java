package com.mb.api_consultorio.domain.paciente;

import com.mb.api_consultorio.domain.direccion.DatosDireccion;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DatosPaciente(
        @NotBlank String nombre,
        @NotBlank @Email  String email,
        @NotBlank  String telefono,
        @NotBlank @Pattern(regexp = "\\d{7,9}") String documento,
        @NotNull @Valid DatosDireccion direccion
) {
}
