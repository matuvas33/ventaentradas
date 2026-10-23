package com.matias.ventaentradas.controller;

import com.matias.ventaentradas.dto.ticket.CompraTickets;
import com.matias.ventaentradas.dto.ticket.TicketResponse;
import com.matias.ventaentradas.service.TicketService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponse> comprarTicket(@Valid @RequestBody CompraTickets compraTickets){
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.comprarTicket(compraTickets));
    }
}
