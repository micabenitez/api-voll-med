package com.mb.api_consultorio.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByActivoTrue(Pageable paginacion);

    //TODO: medico atienda en varios horarios
    @Query("""
            SELECT m from Medico m
            WHERE m.activo = true AND m.especialidad = :especialidad
            AND m.id NOT IN(
               SELECT c.medico.id FROM Consulta c
                WHERE c.fecha = :fecha
            )
            ORDER BY rand()
            LIMIT 1
           """)
    Medico elegirMedicoAleatorioDisponibleEnLaFecha(Especialidad especialidad, LocalDateTime fecha);

    @Query("""
            select m.activo
            from Medico m
            where
            m.id = :idMedico
            """)
    boolean findActivoById(Long idMedico);
}
