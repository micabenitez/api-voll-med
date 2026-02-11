package com.mb.api_consultorio.controller;

import com.mb.api_consultorio.domain.direccion.DatosDireccion;
import com.mb.api_consultorio.domain.medico.*;
import com.mb.api_consultorio.domain.direccion.Direccion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DatosRegistroMedico> datosRegistroMedicoJson;

    @Autowired
    private JacksonTester<DatosDetalleMedico> datosDetalleMedicoJson;

    @MockitoBean
    private MedicoRepository repository;

    @Test
    @DisplayName("Debería devolver código http 400 cuando las informaciones son inválidas")
    @WithMockUser
    void registrarSinEnviarDatos() throws Exception {
        var response = mvc.perform(post("/medicos"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    @DisplayName("Deberia devolver http 200 cuando la request reciba un json valido")
    @WithMockUser
    void registrarMedicoConDatosValidos() throws Exception {
        var datosRegistro = new DatosRegistroMedico(
                "Medico",
                "medico@voll.med",
                "12345678",
                "1234567",
                Especialidad.CARDIOLOGIA,
                datosDireccion());

        when(repository.save(any())).thenReturn(new Medico(datosRegistro));

        var response = mvc.perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(datosRegistroMedicoJson.write(datosRegistro).getJson()))
                .andReturn().getResponse();

        var datosDetalle = new DatosDetalleMedico(
                null,
                datosRegistro.nombre(),
                datosRegistro.email(),
                datosRegistro.telefono(),
                datosRegistro.documento(),
                datosRegistro.especialidad(),
                new Direccion(datosRegistro.direccion())
        );

        var jsonEsperado = datosDetalleMedicoJson.write(
                datosDetalle
        ).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    private DatosDireccion datosDireccion() {
        return new DatosDireccion(
                "calle",
                "12",
                "barrio",
                "ciudad",
                "1234",
                "provincia"
        );
    }
}