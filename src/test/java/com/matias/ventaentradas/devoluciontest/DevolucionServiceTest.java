package com.matias.ventaentradas.devoluciontest;

import com.matias.ventaentradas.dto.devolucion.DevolucionEntrada;
import com.matias.ventaentradas.dto.devolucion.DevolucionMapper;
import com.matias.ventaentradas.dto.devolucion.DevolucionResponse;
import com.matias.ventaentradas.globalexceptionhandler.excepciones.CompraNoEncontradaException;
import com.matias.ventaentradas.globalexceptionhandler.excepciones.DevolucionCantidadException;
import com.matias.ventaentradas.model.Devolucion;
import com.matias.ventaentradas.model.Evento;
import com.matias.ventaentradas.model.Ticket;
import com.matias.ventaentradas.repository.DevolucionRepository;
import com.matias.ventaentradas.repository.EventoRepository;
import com.matias.ventaentradas.repository.TicketRepository;
import com.matias.ventaentradas.service.DevolucionService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DevolucionServiceTest {
    @Mock
    private DevolucionRepository devolucionRepository;
    @Mock
    private EventoRepository eventoRepository;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private DevolucionMapper mapper;
    @InjectMocks
    private DevolucionService service;

    @BeforeEach
    void setUp(){
        DevolucionMapper devolucionMapper = new DevolucionMapper();
        service=new DevolucionService(devolucionRepository,ticketRepository,eventoRepository,devolucionMapper);
    }

    @Test
    void devolucionTicket_lanzaCompraNoEncontradaException(){
        Long id = 99L;
        DevolucionEntrada devolucionEntrada=new DevolucionEntrada(4);

        when(ticketRepository.findByIdOrThrow(id)).thenThrow(new CompraNoEncontradaException("No se ha encontrado compra con ese id"));

        assertThrows(CompraNoEncontradaException.class,
                ()-> service.devolucionTicket(id,devolucionEntrada));

        verify(ticketRepository,never()).save(any());
        verify(devolucionRepository,never()).save(any());
    }

    @Test
    void devolucionTicket_lanzaDevolucionCantidadException(){
        Ticket ticket;
        ticket = new Ticket(null,"test@gmail.com",3);
        Long id = 99L;
        DevolucionEntrada devolucionEntrada = new DevolucionEntrada(5);

        when(ticketRepository.findByIdOrThrow(id)).thenReturn(ticket);

        assertThrows(DevolucionCantidadException.class,
                ()-> service.devolucionTicket(id,devolucionEntrada));

        verify(ticketRepository,never()).save(any());
        verify(devolucionRepository,never()).save(any());

    }

    @Test
    void devolucionTicket_haceLaDevolucionRestaCantidadEntradasCompradasYSumaEntradasDisponibles(){
        LocalDate fecha = LocalDate.of(2026,5,12);
        Evento evento;
        evento = new Evento("Test",fecha,120,117,new BigDecimal("20.20"));
        evento.setId(1L);
        evento.setCapacidadDisponible(117);

        Ticket ticket;
        ticket = new Ticket(evento,"test@gmail.com",3);
        ticket.setId(2L);
        DevolucionEntrada devolucionEntrada= new DevolucionEntrada(2);

        when(ticketRepository.findByIdOrThrow(2L))
                .thenReturn(ticket);
        when(ticketRepository.save(any(Ticket.class)))
                .thenAnswer(inv-> inv.getArgument(0));
        when(eventoRepository.save(any(Evento.class)))
                .thenAnswer(inv -> inv.getArgument(0));
        when(devolucionRepository.save(any(Devolucion.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        DevolucionResponse resultado = service.devolucionTicket(2L,devolucionEntrada);

        assertEquals(1,ticket.getCantidad());
        assertEquals(119,evento.getCapacidadDisponible());
        assertEquals(2,resultado.cantidad());

        verify(ticketRepository).save(ticket);
        verify(eventoRepository).save(evento);
        verify(devolucionRepository).save(any(Devolucion.class));
    }
}
