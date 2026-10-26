package com.matias.ventaentradas.service;

import com.matias.ventaentradas.dto.devolucion.DevolucionEntrada;
import com.matias.ventaentradas.dto.devolucion.DevolucionMapper;
import com.matias.ventaentradas.dto.devolucion.DevolucionResponse;
import com.matias.ventaentradas.globalexceptionhandler.excepciones.DevolucionCantidadException;
import com.matias.ventaentradas.model.Devolucion;
import com.matias.ventaentradas.model.Evento;
import com.matias.ventaentradas.model.Ticket;
import com.matias.ventaentradas.repository.DevolucionRepository;
import com.matias.ventaentradas.repository.EventoRepository;
import com.matias.ventaentradas.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DevolucionService {
    private final DevolucionRepository devolucionRepository;
    private final TicketRepository ticketRepository;
    private final EventoRepository eventoRepository;
    private final DevolucionMapper mapper;

    @Transactional
    public DevolucionResponse devolucionTicket(Long id,DevolucionEntrada devolucionEntrada){
        Ticket ticket = ticketRepository.findByIdOrThrow(id);

        if (devolucionEntrada.cantidad()> ticket.getCantidad()){
            throw new DevolucionCantidadException("No puedes devolver mas entradas de las que has comprado!");
        }

        ticket.setCantidad(ticket.getCantidad()-devolucionEntrada.cantidad());

        Evento evento = ticket.getEvento();
        evento.setCapacidadDisponible(evento.getCapacidadDisponible()+devolucionEntrada.cantidad());

        ticketRepository.save(ticket);
        eventoRepository.save(evento);

        Devolucion devolucion = mapper.toEntity(devolucionEntrada,ticket);

        Devolucion guardarDevolucion = devolucionRepository.save(devolucion);

        return mapper.toDto(guardarDevolucion);

    }
}
