package com.matias.ventaentradas.dto.ticket;

import jakarta.validation.constraints.*;

public record CompraTickets (
        @NotNull
        Long eventoId,
        @NotBlank
        @Email
        String emailComprador,
        @NotNull
        @Min(value = 1)
        @Max(value = 5)
        Integer cantidad

){

}
