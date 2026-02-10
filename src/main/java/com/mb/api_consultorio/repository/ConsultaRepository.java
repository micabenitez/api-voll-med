package com.mb.api_consultorio.repository;

import com.mb.api_consultorio.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
}
