package com.mb.api_consultorio.domain.consulta;

import com.mb.api_consultorio.domain.consulta.validaciones.ValidadorDeConsultas;
import com.mb.api_consultorio.infra.exceptions.ValidacionException;
import com.mb.api_consultorio.domain.medico.Medico;
import com.mb.api_consultorio.domain.medico.MedicoRepository;
import com.mb.api_consultorio.domain.paciente.PacienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaDeConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private List<ValidadorDeConsultas> validadores;

    public DatosDetalleConsulta reservar(DatosReservaConsulta datos) {
        if(!pacienteRepository.existsById(datos.idPaciente())){
            throw new ValidacionException("No existe un paciente con el id informado");
        }

        if(datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())) throw new ValidacionException("No existe un medico con el id informado");

        validadores.forEach(v -> v.validar(datos));

        var medico = elegirMedico(datos);

        System.out.println(medico);

        if(medico == null) throw new ValidacionException("No existe un medico disponible en ese horario");

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


