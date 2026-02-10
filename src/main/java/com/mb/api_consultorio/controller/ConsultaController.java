package com.mb.api_consultorio.controller;

import com.mb.api_consultorio.dto.DatosCancelacionReserva;
import com.mb.api_consultorio.dto.DatosDetalleConsulta;
import com.mb.api_consultorio.dto.DatosReservaConsulta;
import com.mb.api_consultorio.service.ReservaDeConsulta;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private ReservaDeConsulta reserva;

    @PostMapping
    @Transactional
    public ResponseEntity reservar(@RequestBody @Valid DatosReservaConsulta datos) {
        reserva.reservar(datos);
        return ResponseEntity.ok(new DatosDetalleConsulta(null,null,null,null));
    }

    @DeleteMapping("/cancelar-consulta")
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DatosCancelacionReserva datos) {
        reserva.cancelar(datos);
        return ResponseEntity.noContent().build();
    }
}
