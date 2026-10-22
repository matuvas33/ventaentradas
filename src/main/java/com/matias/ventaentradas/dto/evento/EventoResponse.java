package com.matias.ventaentradas.dto.evento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventoResponse(
        Long id,
        String nombre,
        LocalDateTime fecha,
        Integer capacidadTotal,
        Integer capacidadDisponible,
        BigDecimal precio
) {
}
