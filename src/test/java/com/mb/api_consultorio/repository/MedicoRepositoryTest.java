package com.mb.api_consultorio.repository;

import com.mb.api_consultorio.domain.medico.MedicoRepository;
import com.mb.api_consultorio.domain.direccion.DatosDireccion;
import com.mb.api_consultorio.domain.medico.DatosRegistroMedico;
import com.mb.api_consultorio.domain.paciente.DatosRegistroPaciente;
import com.mb.api_consultorio.domain.medico.Especialidad;
import com.mb.api_consultorio.domain.consulta.Consulta;
import com.mb.api_consultorio.domain.medico.Medico;
import com.mb.api_consultorio.domain.paciente.Paciente;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("Deberia devolver null cuando el medico buscado existe pero no esta disponible en esa fecha")
    void elegirMedicoAleatorioDisponibleEnLaFecha() {
        var lunesSiguienteAlas10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);

        var medico = registrarMedico("medico1", "mail@gmail.com", "12345678", Especialidad.CARDIOLOGIA);
        var paciente = registrarPaciente("paciente1", "email@gmail.com", "123456789");
        registrarConsulta(medico, paciente, lunesSiguienteAlas10);

        var medicoLibre = medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(Especialidad.CARDIOLOGIA, lunesSiguienteAlas10);
        Assertions.assertThat(medicoLibre).isNull();
    }

    @Test
    @DisplayName("Deberia devolver un medico cuando un medico este disponible en esa fecha")
    void elegirMedicoAleatorioDisponibleEnLaFechaEscenario2() {
        var lunesSiguienteAlas10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);

        var medico = registrarMedico("medico1", "mail@gmail.com", "12345678", Especialidad.CARDIOLOGIA);

        var medicoLibre = medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(Especialidad.CARDIOLOGIA, lunesSiguienteAlas10);
        Assertions.assertThat(medicoLibre).isEqualTo(medico);

    }



    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        em.persist(new Consulta(null, medico, paciente, fecha, null));
    }

    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad) {
        var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        em.persist(medico);
        return medico;
    }

    private Paciente registrarPaciente(String nombre, String email, String documento) {
        var paciente = new Paciente(datosPaciente(nombre, email, documento));
        em.persist(paciente);
        return paciente;
    }

    private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
        return new DatosRegistroMedico(
                nombre,
                email,
                "6145489789",
                documento,
                especialidad,
                datosDireccion()
        );
    }

    private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento) {
        return new DatosRegistroPaciente(
                nombre,
                email,
                "1234564",
                documento,
                datosDireccion()
        );
    }

    private DatosDireccion datosDireccion() {
        return new DatosDireccion(
                "calle x",
                "12345",
                "ciudad z",
                "ciudad",
                "12345",
                "provincia"
        );
    }
}