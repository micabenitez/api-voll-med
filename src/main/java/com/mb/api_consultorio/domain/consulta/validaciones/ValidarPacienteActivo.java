package com.mb.api_consultorio.domain.consulta.validaciones;

import com.mb.api_consultorio.domain.consulta.DatosReservaConsulta;
import com.mb.api_consultorio.domain.paciente.PacienteRepository;
import com.mb.api_consultorio.infra.exceptions.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarPacienteActivo implements ValidadorDeConsultas {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public void validar(DatosReservaConsulta datos) {
        var pacienteEstaActivo = pacienteRepository.findActivoById(datos.idPaciente());

        if(!pacienteEstaActivo) throw new ValidacionException("La consulta no puede ser reservada por un paciente inactivo");
    }
}
