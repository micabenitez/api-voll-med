package com.mb.api_consultorio.dto;

import jakarta.validation.constraints.NotNull;

public record DatosCancelacionReserva(
    @NotNull Long idConsulta,
    @NotNull MotivoCancelamiento motivo
) {
}
