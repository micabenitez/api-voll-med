package com.mb.api_consultorio.domain.consulta.validaciones;

import com.mb.api_consultorio.domain.consulta.DatosReservaConsulta;
import com.mb.api_consultorio.infra.exceptions.ValidacionException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidacionFueraHorarioConsulta implements ValidadorDeConsultas {

    public void validar(DatosReservaConsulta datos) {
        var fechaConsulta = datos.fecha();
        var domingo = fechaConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var horarioAnstesDeApertura = fechaConsulta.getHour() < 7;
        var horarioDespuesDeCierre = fechaConsulta.getHour() > 18;

        if(domingo || horarioAnstesDeApertura || horarioDespuesDeCierre){
            throw new ValidacionException("Horario seleccionado fuera del horario de atencion del consultorio");
        }
    }
}
