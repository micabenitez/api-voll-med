package com.mb.api_consultorio.service;

import com.mb.api_consultorio.dto.DatosCancelacionReserva;
import com.mb.api_consultorio.dto.DatosDetalleConsulta;
import com.mb.api_consultorio.dto.DatosReservaConsulta;
import com.mb.api_consultorio.infra.exceptions.ValidacionException;
import com.mb.api_consultorio.model.Consulta;
import com.mb.api_consultorio.model.Medico;
import com.mb.api_consultorio.repository.ConsultaRepository;
import com.mb.api_consultorio.repository.MedicoRepository;
import com.mb.api_consultorio.repository.PacienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservaDeConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    public DatosDetalleConsulta reservar(DatosReservaConsulta datos) {

        if(!pacienteRepository.existsById(datos.idPaciente())){
            throw new ValidacionException("No existe un paciente con el id informado");
        }

        if(datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())) throw new ValidacionException("No existe un medico con el id informado");

        var medico = elegirMedico(datos);

        var paciente = pacienteRepository.findById(datos.idPaciente()).get();
        var consulta = new Consulta(null, medico, paciente, datos.fecha(), null);
        consultaRepository.save(consulta);
        return new DatosDetalleConsulta(consulta.getId(), datos.idMedico(), datos.idPaciente(), datos.fecha());
    }

    private Medico elegirMedico(DatosReservaConsulta datos) {
        if(datos.idMedico() != null) return medicoRepository.getReferenceById(datos.idMedico());

        if(datos.especialidad() == null) throw new ValidacionException("Es necesario elegir una especialidad cuando no se elige un medico");

        medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(datos.especialidad(), datos.fecha());
        return null;

    }

    public void cancelar(@Valid DatosCancelacionReserva datos) {
        if(!consultaRepository.existsById(datos.idConsulta())) throw new ValidacionException("Id de la consulta informada no existe");

        var consulta = consultaRepository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());
    }
}


