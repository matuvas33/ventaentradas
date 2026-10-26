package com.matias.ventaentradas.service;

import com.matias.ventaentradas.dto.ticket.CompraTickets;
import com.matias.ventaentradas.dto.ticket.TicketMapper;
import com.matias.ventaentradas.dto.ticket.TicketResponse;
import com.matias.ventaentradas.globalexceptionhandler.excepciones.SinCapacidadException;
import com.matias.ventaentradas.model.Evento;
import com.matias.ventaentradas.model.Ticket;
import com.matias.ventaentradas.repository.EventoRepository;
import com.matias.ventaentradas.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Service
@AllArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final EventoRepository eventoRepository;
    private final TicketMapper mapper;


    @Transactional
    public TicketResponse comprarTicket(CompraTickets compraTickets){
        Evento evento = eventoRepository.findByIdOrThrow(compraTickets.eventoId());

        if (compraTickets.cantidad()>evento.getCapacidadDisponible()){
            throw new SinCapacidadException("No puedes comprar mas entradas de las que hay disponibles!");
        }

        Integer resta = evento.getCapacidadDisponible()-compraTickets.cantidad();
        evento.setCapacidadDisponible(resta);
        eventoRepository.save(evento);

        Ticket comprarTicket = mapper.toEntity(compraTickets,evento);

        Ticket guardar = ticketRepository.save(comprarTicket);

        return mapper.toDto(guardar);
    }



}
