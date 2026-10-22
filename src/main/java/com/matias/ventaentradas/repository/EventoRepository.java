package com.matias.ventaentradas.repository;

import com.matias.ventaentradas.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento,Long> {
    boolean existsByNombre(String nombre);

}
