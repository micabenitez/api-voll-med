package com.mb.api_consultorio.domain.direccion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DatosDireccion(
        @NotBlank String calle,
        String numero,
        @NotBlank String barrio,
        @NotBlank String ciudad,
        @NotBlank @Pattern(regexp = "\\d{4}") String codigoPostal,
        @NotBlank String provincia
) {
}
