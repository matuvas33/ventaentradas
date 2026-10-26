package com.matias.ventaentradas.repository;

import com.matias.ventaentradas.globalexceptionhandler.excepciones.CompraNoEncontradaException;
import com.matias.ventaentradas.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,Long> {

    default Ticket findByIdOrThrow(Long id){
        return findById(id).orElseThrow(()->
                new CompraNoEncontradaException("No existe compra con este ID"));
    }


}
