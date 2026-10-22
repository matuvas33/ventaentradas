package com.matias.ventaentradas.repository;

import com.matias.ventaentradas.globalexceptionhandler.excepciones.EventoNoEncontradoException;
import com.matias.ventaentradas.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento,Long> {
    boolean existsByNombre(String nombre);

    default Evento findByIdOrThrow(Long id){
        return findById(id).orElseThrow(()->
                new EventoNoEncontradoException("No se ha encontrado evento con ID "+id));
    }
}
