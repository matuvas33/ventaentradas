package com.matias.ventaentradas.eventotest;

import com.matias.ventaentradas.dto.evento.CrearEvento;
import com.matias.ventaentradas.dto.evento.EventoMapper;
import com.matias.ventaentradas.dto.evento.EventoResponse;
import com.matias.ventaentradas.globalexceptionhandler.excepciones.EventoDuplicadoException;
import com.matias.ventaentradas.globalexceptionhandler.excepciones.EventoNoEncontradoException;
import com.matias.ventaentradas.model.Evento;
import com.matias.ventaentradas.repository.EventoRepository;
import com.matias.ventaentradas.service.EventoService;
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
public class EventoServiceTest {
    @Mock
    private EventoRepository eventoRepository;
    @InjectMocks
    private EventoService eventoService;

    @BeforeEach
    void setUp(){
        EventoMapper eventoMapper = new EventoMapper();
        eventoService = new EventoService(eventoRepository,eventoMapper);
    }

    @Test
    void crearEvento_lanzaEventoDuplicado(){
        CrearEvento crearEvento  = new CrearEvento("Obra de Teatro: La Sombra del Olvido", LocalDate.now(),120, BigDecimal.valueOf(20.00));
        when(eventoRepository.existsByNombre("Obra de Teatro: La Sombra del Olvido")).thenReturn(true);

        assertThrows(EventoDuplicadoException.class,
                ()-> eventoService.crearEvento(crearEvento));

        verify(eventoRepository,never()).save(any());
    }

    @Test
    void crearEvento_creaSiNoHayNombreDuplicado(){

        LocalDate fechaEsperada = LocalDate.of(2026,11,30);
        BigDecimal precioEsperado = BigDecimal.valueOf(20.00);

        CrearEvento crearEvento = new CrearEvento("Festival B",fechaEsperada,120,precioEsperado);

        when(eventoRepository.existsByNombre("Festival B")).thenReturn(false);

        when(eventoRepository.save(any(Evento.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        EventoResponse resultado = eventoService.crearEvento(crearEvento);

        assertEquals("Festival B",resultado.nombre());
        assertEquals(fechaEsperada,resultado.fecha());
        assertEquals(120,resultado.capacidadTotal());
        assertEquals(120,resultado.capacidadDisponible());
        assertEquals(BigDecimal.valueOf(20.00),resultado.precio());

        verify(eventoRepository).save(any(Evento.class));

    }

    @Test
    void obtenerEventoId_lanzaEventoNoEncontradoSiNoEncuntraPorId(){
        when(eventoRepository.findByIdOrThrow(99L)).thenThrow(new EventoNoEncontradoException("No se ha encontrado evento"));

        assertThrows(EventoNoEncontradoException.class,
                ()-> eventoService.obtenerEventoId(99L));
    }

    @Test
    void obtenerEventoId_daEventoEncontrado(){
        LocalDate fecha = LocalDate.of(2026,11,20);
        BigDecimal precio = BigDecimal.valueOf(20.00);

        Evento evento = new Evento("Hola",fecha,120,120, precio);

        when(eventoRepository.findByIdOrThrow(1L)).thenReturn(evento);

        EventoResponse resultado = eventoService.obtenerEventoId(1L);

        assertEquals("Hola",resultado.nombre());
        assertEquals(120,resultado.capacidadDisponible());
        assertEquals(fecha.toString(),fecha.toString());

    }
}
//('Obra de Teatro: La Sombra del Olvido', '2026-11-20', 120, 40, 25.50),