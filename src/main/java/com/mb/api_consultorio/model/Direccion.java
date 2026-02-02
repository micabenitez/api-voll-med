package com.mb.api_consultorio.model;

import com.mb.api_consultorio.dto.DatosDireccion;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Direccion {
    private String calle;
    private String numero;
    private String barrio;
    private String ciudad;
    private String codigoPostal;
    private String provincia;

    public Direccion(DatosDireccion datosDireccion) {
        this.calle = datosDireccion.calle();
        this.numero = datosDireccion.numero();
        this.barrio = datosDireccion.barrio();
        this.ciudad = datosDireccion.ciudad();
        this.codigoPostal = datosDireccion.codigoPostal();
        this.provincia = datosDireccion.provincia();
    }

    public void actualizarDireccion(DatosDireccion datos) {
        if(datos.calle() != null) this.calle = datos.calle();
        if(datos.numero() != null) this.numero = datos.numero();
        if(datos.barrio() != null) this.barrio = datos.barrio();
        if(datos.ciudad() != null) this.ciudad = datos.ciudad();
        if(datos.codigoPostal() != null) this.codigoPostal = datos.codigoPostal();
        if(datos.provincia() != null) this.provincia = datos.provincia();
    }
}
