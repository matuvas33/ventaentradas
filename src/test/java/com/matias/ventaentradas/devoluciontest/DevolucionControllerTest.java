package com.matias.ventaentradas.devoluciontest;

import com.matias.ventaentradas.controller.DevolucionController;
import com.matias.ventaentradas.dto.devolucion.DevolucionEntrada;
import com.matias.ventaentradas.dto.devolucion.DevolucionResponse;
import com.matias.ventaentradas.globalexceptionhandler.excepciones.CompraNoEncontradaException;
import com.matias.ventaentradas.globalexceptionhandler.excepciones.DevolucionCantidadException;
import com.matias.ventaentradas.model.Devolucion;
import com.matias.ventaentradas.model.Ticket;
import com.matias.ventaentradas.service.DevolucionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DevolucionController.class)
public class DevolucionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private DevolucionService devolucionService;

    @Test
    void devolucionTicket_da404SiNoEncuentraIdCompra() throws Exception{
        DevolucionEntrada devolucion = new DevolucionEntrada(5);

        when(devolucionService.devolucionTicket(eq(99L),any(DevolucionEntrada.class)))
                .thenThrow(new CompraNoEncontradaException("No se ha encontrado compra con ese ID"));

        mockMvc.perform(post("/api/tickets/99/devoluciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(devolucion)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.mensaje").value("No se ha encontrado compra con ese ID"));
    }

    @Test
    void devolucionTicket_da400CuandoFaltanCampos()throws Exception{
        Ticket ticket;
        ticket = new Ticket(null,"test@prueba.com",5);
        ticket.setId(1L);
        DevolucionEntrada devolucionEntrada = new DevolucionEntrada(-4);

        mockMvc.perform(post("/api/tickets/1/devoluciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(devolucionEntrada)))
                .andExpect(status().isBadRequest());

        verify(devolucionService,never()).devolucionTicket(eq(1L),any());
    }

    @Test
    void devolucionTicket_da409SiDevuelveMasDeLasQueCompro() throws Exception{
        Ticket ticket;
        ticket=new Ticket(null,"test@prueba.com",3);
        ticket.setId(1L);
        DevolucionEntrada devolucionEntrada = new DevolucionEntrada(5);

        when(devolucionService.devolucionTicket(eq(1L),any(DevolucionEntrada.class)))
                .thenThrow(new DevolucionCantidadException("Test"));

        mockMvc.perform(post("/api/tickets/1/devoluciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(devolucionEntrada)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(HttpStatus.CONFLICT.value()))
                .andExpect(jsonPath("$.mensaje").value("Test"));
    }

    @Test
    void devolucionTicket_da201SiCreadoCorrectamente() throws Exception{

        LocalDateTime fecha = LocalDateTime.of(2026,10,5,0,0);

        DevolucionEntrada devolucionEntrada = new DevolucionEntrada(5);
        DevolucionResponse resultado = new DevolucionResponse(1L,3L,"test@gmail.com",5,fecha);

        when(devolucionService.devolucionTicket(eq(1L),any(DevolucionEntrada.class)))
                .thenReturn(resultado);

        mockMvc.perform(post("/api/tickets/1/devoluciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(devolucionEntrada)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.emailComprador").value("test@gmail.com"))
                .andExpect(jsonPath("$.cantidad").value(5));

    }
}
