package com.matias.ventaentradas.tickettest;

import com.matias.ventaentradas.dto.ticket.CompraTickets;
import com.matias.ventaentradas.dto.ticket.TicketMapper;
import com.matias.ventaentradas.dto.ticket.TicketResponse;
import com.matias.ventaentradas.globalexceptionhandler.excepciones.EventoNoEncontradoException;
import com.matias.ventaentradas.globalexceptionhandler.excepciones.SinCapacidadException;
import com.matias.ventaentradas.model.Evento;
import com.matias.ventaentradas.model.Ticket;
import com.matias.ventaentradas.repository.EventoRepository;
import com.matias.ventaentradas.repository.TicketRepository;
import com.matias.ventaentradas.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private EventoRepository eventoRepository;
    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    void setUp(){
      TicketMapper ticketMapper = new TicketMapper();
      ticketService= new TicketService(ticketRepository,eventoRepository,ticketMapper);
    }

    @Test
    void comprarTicket_lanzaEventoNoEncontrado(){

        CompraTickets compraTickets = new CompraTickets(99L,"prueba@gmail.com",3);

        when(eventoRepository.findByIdOrThrow(99L)).thenThrow(new EventoNoEncontradoException("No se ha encontrado evento con esa ID"));


        assertThrows(EventoNoEncontradoException.class,
                ()-> ticketService.comprarTicket(compraTickets));

        verify(ticketRepository,never()).save(any());
        verify(eventoRepository,never()).save(any());
    }

    @Test
    void comprarTicket_lanzaSinCapacidad(){
        LocalDate fecha = LocalDate.of(2026,5,12);
        Evento evento;
        evento = new Evento("Test",fecha,120,120,new BigDecimal("20.20"));
        evento.setCapacidadDisponible(2);

        CompraTickets compraTickets = new CompraTickets(1L,"gus@gmail.com",4);
        when(eventoRepository.findByIdOrThrow(1L)).thenReturn(evento);

        assertThrows(SinCapacidadException.class,
                ()->ticketService.comprarTicket(compraTickets));

        verify(ticketRepository,never()).save(any());
        verify(ticketRepository,never()).save(any());
    }

    @Test
    void comprarTicket_haceLaCompraYRestaCapacidadDisponibleSiTodoBien(){
        LocalDate fecha = LocalDate.of(2026,5,12);
        Evento evento;
        evento = new Evento("Test",fecha,120,120,new BigDecimal("20.20"));

        CompraTickets compraTickets = new CompraTickets(1L,"gus@gmail.com",4);
        when(eventoRepository.findByIdOrThrow(1L)).thenReturn(evento);
        when(eventoRepository.save(any(Evento.class)))
                .thenAnswer(inv -> inv.getArgument(0));
        when(ticketRepository.save(any(Ticket.class)))
                .thenAnswer(inv-> inv.getArgument(0));

        TicketResponse response = ticketService.comprarTicket(compraTickets);

        assertEquals("gus@gmail.com",response.emailComprador());
        assertEquals(4,response.cantidad());
        assertEquals(116,evento.getCapacidadDisponible());

        verify(ticketRepository).save(any(Ticket.class));
        verify(eventoRepository).save(any(Evento.class));

    }

}

