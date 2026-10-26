package com.matias.ventaentradas.tickettest;

import com.matias.ventaentradas.controller.TicketController;
import com.matias.ventaentradas.dto.ticket.CompraTickets;
import com.matias.ventaentradas.dto.ticket.TicketResponse;
import com.matias.ventaentradas.globalexceptionhandler.excepciones.EventoNoEncontradoException;
import com.matias.ventaentradas.globalexceptionhandler.excepciones.SinCapacidadException;
import com.matias.ventaentradas.model.Evento;
import com.matias.ventaentradas.service.TicketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TicketController.class)
public class TicketControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private TicketService ticketService;

    @Test
    void comprarTicket_da404SiNoEncuentraEvento() throws Exception{
        CompraTickets compraTickets = new CompraTickets(99L,"gus@gmail.com",4);
        when(ticketService.comprarTicket(any(CompraTickets.class))).thenThrow(new EventoNoEncontradoException("No se ha encontrado este evento"));

        mockMvc.perform(post("/api/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraTickets)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.mensaje").value("No se ha encontrado este evento"));
    }

    @Test
    void comprarTicket_da409SiSinCapacidad()throws Exception{
        CompraTickets compraTickets = new CompraTickets(1L,"test@gmail.com",4);

        when(ticketService.comprarTicket(any(CompraTickets.class))).thenThrow(new SinCapacidadException("Test"));

        mockMvc.perform(post("/api/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraTickets)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(HttpStatus.CONFLICT.value()))
                .andExpect(jsonPath("$.mensaje").value("Test"));
    }
    @Test
    void comprarTicket_da400CuandoFaltanCampos() throws Exception{
        CompraTickets compraTickets = new CompraTickets(null,"",-4);

        mockMvc.perform(post("/api/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraTickets)))
                .andExpect(status().isBadRequest());

        verify(ticketService,never()).comprarTicket(any());
    }

    @Test
    void comprarTicket_da201CuandoCorrecto() throws Exception{

        CompraTickets compraTickets = new CompraTickets(1L,"test@gmail.com",4);

        TicketResponse response = new TicketResponse(3L,1L,"Test","test@gmail.com",
                4, LocalDateTime.now());

        when(ticketService.comprarTicket(any(CompraTickets.class))).thenReturn(response);

        mockMvc.perform(post("/api/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraTickets)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.emailComprador").value("test@gmail.com"))
                .andExpect(jsonPath("$.cantidad").value(4));
    }


}
