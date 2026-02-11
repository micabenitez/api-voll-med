package com.mb.api_consultorio.domain.consulta.validaciones;

import com.mb.api_consultorio.domain.consulta.DatosReservaConsulta;
import com.mb.api_consultorio.infra.exceptions.ValidacionException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidacionConsultaConAnticipacion implements ValidadorDeConsultas{

    public void validar(DatosReservaConsulta datos) {
        var fechaConsulta = datos.fecha();
        var ahora = LocalDateTime.now();
        var diferenciaEnMinutos = Duration.between(ahora, fechaConsulta).toMinutes();
        if(diferenciaEnMinutos < 30) throw new ValidacionException("Horario seleccionado mcon menos de 30min de anticipacion");
    }
}
