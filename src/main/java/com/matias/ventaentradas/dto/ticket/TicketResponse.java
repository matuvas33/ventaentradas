package com.matias.ventaentradas.dto.ticket;

import java.time.LocalDateTime;

public record TicketResponse (
        Long id,
        Long eventoId,
        String nombreEvento,
        String emailComprador,
        Integer cantidad,
        LocalDateTime fechaCompra
){
}
