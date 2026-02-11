package com.mb.api_consultorio.domain.paciente;

import com.mb.api_consultorio.domain.direccion.Direccion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;

    @Embedded
    @Column(name = "datos_direccion")
    private Direccion direccion;
    private Boolean activo;


    public Paciente(DatosRegistroPaciente datos) {
        this.activo = true;
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.telefono = datos.telefono();
        this.documento = datos.documento();
        this.direccion = new Direccion(datos.direccion());
    }

    public void actualizarInformaciones(DatosActualizarPaciente datos) {
        if (datos.nombre() != null) {
            this.nombre = datos.nombre();
        }
        if (datos.telefono() != null) {
            this.telefono = datos.telefono();
        }
        if (datos.direccion() != null) {
            this.direccion.actualizarDatos(datos.direccion());
        }
    }

    public void eliminar() {
        this.activo = false;
    }
}
