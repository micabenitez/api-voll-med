package com.mb.api_consultorio.domain.consulta.validaciones;

import com.mb.api_consultorio.domain.consulta.ConsultaRepository;
import com.mb.api_consultorio.domain.consulta.DatosReservaConsulta;
import com.mb.api_consultorio.infra.exceptions.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionPacienteSinOtraConsultaEnElMismoDia implements ValidadorDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(DatosReservaConsulta datos) {
        var primerHorario = datos.fecha().withHour(7);
        var ultimoHorario = datos.fecha().withHour(18);

        var pacienteTieneOtraConsultaEnElDia = consultaRepository.existsByPacienteIdAndFechaBetween(datos.idPaciente(), primerHorario, ultimoHorario);
        if(pacienteTieneOtraConsultaEnElDia) throw new ValidacionException("El paciente ya tiene una consulta reservada para ese dia");
    }
}
