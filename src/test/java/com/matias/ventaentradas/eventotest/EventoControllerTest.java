package com.matias.ventaentradas.eventotest;

import com.matias.ventaentradas.controller.EventoController;
import com.matias.ventaentradas.dto.evento.CrearEvento;
import com.matias.ventaentradas.dto.evento.EventoResponse;
import com.matias.ventaentradas.globalexceptionhandler.excepciones.EventoDuplicadoException;
import com.matias.ventaentradas.service.EventoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventoController.class)
public class EventoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private EventoService eventoService;

    @Test
    void crearEvento_da409CuandoEventoDuplicado() throws Exception{
        LocalDateTime fechaEsperada = LocalDateTime.of(2026,12,23,20,0);
        BigDecimal precioEsperado = BigDecimal.valueOf(25.00);

        CrearEvento crearEvento = new CrearEvento("Prueba",fechaEsperada,120,precioEsperado);

        when(eventoService.crearEvento(any(CrearEvento.class)))
                .thenThrow(new EventoDuplicadoException("No puede tener el mismo nombre que otro evento!"+crearEvento.nombre()));

        mockMvc.perform(post("/api/eventos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(crearEvento)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.mensaje").value("No puede tener el mismo nombre que otro evento!"+crearEvento.nombre()));

    }
    @Test
    void crearEvento_da400CuandoFaltanDatosObligatorios() throws Exception{
       CrearEvento evento = new CrearEvento("",null,120,new BigDecimal("20.00"));

       mockMvc.perform(post("/api/eventos")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(evento)))
               .andExpect(status().isBadRequest());

       verify(eventoService,never()).crearEvento(any());

    }

    @Test
    void crearEvento_da201CuandoCorrecto() throws Exception{
        LocalDateTime fecha = LocalDateTime.of(2026,12,23,20,0);

        CrearEvento evento = new CrearEvento("Gustavo",fecha,120,new BigDecimal("20.50"));

        EventoResponse eventoResponse = new EventoResponse(1L,"Gustavo",fecha,
                120,120,new BigDecimal("20.50"));

        when(eventoService.crearEvento(any(CrearEvento.class))).thenReturn(eventoResponse);

        mockMvc.perform(post("/api/eventos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(evento)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Gustavo"))
                .andExpect(jsonPath("$.precio").value(20.50));

    }



}
