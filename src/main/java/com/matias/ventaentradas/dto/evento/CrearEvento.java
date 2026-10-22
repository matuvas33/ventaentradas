package com.matias.ventaentradas.dto.evento;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CrearEvento (
        @NotBlank
        String nombre,
        @NotNull
        LocalDateTime fecha,
        @NotNull
        @Min(value = 1)
        Integer capacidadTotal,
        @NotNull
        @Digits(integer = 8,fraction = 2)
        BigDecimal precio
){
}
