package com.matias.ventaentradas.repository;

import com.matias.ventaentradas.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,Long> {

}
