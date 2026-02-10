package com.mb.api_consultorio.model;

import com.mb.api_consultorio.dto.MotivoCancelamiento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "consultas")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private LocalDateTime fecha;
    private MotivoCancelamiento motivoCancelamiento;

    public void cancelar(@NotNull MotivoCancelamiento motivo) {
        this.motivoCancelamiento = motivo;
    }

    //TODO: EJECUTAR APP PARA Q AGREGUE MIGRACION V6 y LUEGO AGREGAR MIGRACION V7
}
