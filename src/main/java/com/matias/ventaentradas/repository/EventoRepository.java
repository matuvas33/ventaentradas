package com.matias.ventaentradas.repository;

import com.matias.ventaentradas.globalexceptionhandler.excepciones.EventoNoEncontradoException;
import com.matias.ventaentradas.model.Evento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface EventoRepository extends JpaRepository<Evento,Long> {
    boolean existsByNombre(String nombre);

    default Evento findByIdOrThrow(Long id){
        return findById(id).orElseThrow(()->
                new EventoNoEncontradoException("No se ha encontrado evento con ID "+id));
    }

    Page<Evento> findByFecha(LocalDate fecha, Pageable pageable);

    Page<Evento> findByCapacidadDisponibleGreaterThan(int capacidad,Pageable pageable);

    Page<Evento> findByFechaCapacidadDisponibleGreaterThan(LocalDate fecha,int capacidad,Pageable pageable);
}
