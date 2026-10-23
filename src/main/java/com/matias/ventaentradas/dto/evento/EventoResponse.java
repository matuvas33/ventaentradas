package com.matias.ventaentradas.dto.evento;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EventoResponse(
        Long id,
        String nombre,
        LocalDate fecha,
        Integer capacidadTotal,
        Integer capacidadDisponible,
        BigDecimal precio
) {
}
