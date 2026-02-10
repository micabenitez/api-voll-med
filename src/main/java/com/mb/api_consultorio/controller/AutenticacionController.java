package com.mb.api_consultorio.controller;

import com.mb.api_consultorio.dto.DatosAutenticacion;
import com.mb.api_consultorio.infra.security.DatosTokenJWT;
import com.mb.api_consultorio.infra.security.TokenService;
import com.mb.api_consultorio.model.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager manager;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid DatosAutenticacion datos) {
        System.out.println(datos);
        var authenticationToken = new UsernamePasswordAuthenticationToken(datos.user(), datos.contrasenia());
        var autenticacion = manager.authenticate(authenticationToken);
        var tokenJWT = tokenService.generarToken((Usuario) autenticacion.getPrincipal());
        return ResponseEntity.ok(new DatosTokenJWT(tokenJWT));
    }
}