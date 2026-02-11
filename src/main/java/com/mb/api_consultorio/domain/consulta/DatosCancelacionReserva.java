package com.mb.api_consultorio.domain.consulta;

import jakarta.validation.constraints.NotNull;

public record DatosCancelacionReserva(
    @NotNull Long idConsulta,
    @NotNull MotivoCancelamiento motivo
) {
}
