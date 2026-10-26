package com.matias.ventaentradas.repository;

import com.matias.ventaentradas.model.Devolucion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevolucionRepository extends JpaRepository<Devolucion,Long> {
}
