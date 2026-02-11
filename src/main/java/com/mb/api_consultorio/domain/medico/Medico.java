package com.mb.api_consultorio.domain.medico;

import com.mb.api_consultorio.domain.direccion.Direccion;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

@Table(name = "medicos")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode (of = "id")
public class Medico {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean activo;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;

    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    @Column(name = "datos_direccion")
    private Direccion direccion;

    public Medico(DatosRegistroMedico datos) {
        this.id = null;
        this.activo = true;
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.telefono = datos.telefono();
        this.documento = datos.documento();
        this.especialidad = datos.especialidad();
        this.direccion = new Direccion(datos.direccion());
    }

    public void actualizarInfo(@Valid DatosActualizacionMedico datos) {
        if (datos.nombre() != null) {
            this.nombre = datos.nombre();
        }

        if(datos.telefono() != null) {
            this.telefono = datos.telefono();
        }

        if(datos.direccion() != null) {
            this.direccion.actualizarDireccion(datos.direccion());
        }
    }

    public void eliminar() {
        this.activo = false;
    }
}
