package com.matias.ventaentradas.dto.devolucion;

import java.time.LocalDateTime;

public record DevolucionResponse(
        Long id,
        Long compraId,
        String emailComprador,
        Integer cantidad,
        LocalDateTime fechaDevolucion
) {

}
