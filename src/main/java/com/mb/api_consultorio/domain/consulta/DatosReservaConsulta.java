package com.mb.api_consultorio.domain.consulta;

import com.mb.api_consultorio.domain.medico.Especialidad;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosReservaConsulta(
        Long idMedico,
        @NotNull Long idPaciente,
        @NotNull @Future LocalDateTime fecha,
        Especialidad especialidad
        ) {
}
