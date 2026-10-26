package com.matias.ventaentradas.dto.devolucion;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record DevolucionEntrada(
        @NotNull
        Long compraId,
        @NotNull
        @Min(value = 1)
        Integer cantidad
) {
}
