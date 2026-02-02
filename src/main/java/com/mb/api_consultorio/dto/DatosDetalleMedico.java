package com.mb.api_consultorio.dto;

import com.mb.api_consultorio.model.Direccion;
import com.mb.api_consultorio.model.Medico;

public record DatosDetalleMedico(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        Especialidad especialidad,
        Direccion direccion
) {
    public DatosDetalleMedico(Medico m) {
        this(m.getId(), m.getNombre(), m.getEmail(), m.getTelefono(), m.getDocumento(), m.getEspecialidad(), m.getDireccion());
    }
}
