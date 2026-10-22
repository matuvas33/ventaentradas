package com.matias.ventaentradas.dto.ticket;

import com.matias.ventaentradas.model.Evento;
import com.matias.ventaentradas.model.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    public Ticket toEntity(CompraTickets dto, Evento evento){
        if (dto==null)return null;
        Ticket ticket = new Ticket();
        ticket.setEvento(evento);
        ticket.setEmailComprador(dto.emailComprador());
        ticket.setCantidad(dto.cantidad());

        return ticket;
    }

    public TicketResponse toDto(Ticket ticket){
        if (ticket == null)return null;
        return new TicketResponse(
                ticket.getId(),
                ticket.getEvento().getId(),
                ticket.getEvento().getNombre(),
                ticket.getEmailComprador(),
                ticket.getCantidad(),
                ticket.getFechaCompra()
        );
    }
}
