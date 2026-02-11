package com.mb.api_consultorio.domain.consulta.validaciones;

import com.mb.api_consultorio.domain.consulta.DatosReservaConsulta;
import com.mb.api_consultorio.domain.medico.MedicoRepository;
import com.mb.api_consultorio.infra.exceptions.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionMedicoActivo  implements ValidadorDeConsultas{
    @Autowired
    private MedicoRepository medicoRepository;

    public void validar(DatosReservaConsulta datos) {
       if(datos.idMedico() == null) return;

       var medicoEstaActivo = medicoRepository.findActivoById(datos.idMedico());
       if(!medicoEstaActivo) throw new ValidacionException("La consulta no puede ser reservada con un medico inactivo");
    }
}
