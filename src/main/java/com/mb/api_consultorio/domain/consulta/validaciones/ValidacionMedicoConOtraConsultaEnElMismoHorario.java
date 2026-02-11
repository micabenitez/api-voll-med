package com.mb.api_consultorio.domain.consulta.validaciones;

import com.mb.api_consultorio.domain.consulta.ConsultaRepository;
import com.mb.api_consultorio.domain.consulta.DatosReservaConsulta;
import com.mb.api_consultorio.infra.exceptions.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionMedicoConOtraConsultaEnElMismoHorario implements ValidadorDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DatosReservaConsulta datos) {
        var medicoTieneConsultaEnMismoHorario = consultaRepository.existsByMedicoIdAndFecha(datos.idMedico(), datos.fecha());
        if(medicoTieneConsultaEnMismoHorario) throw new ValidacionException("El medico ya tiene una consulta en esa fecha y horario");
    }
}
