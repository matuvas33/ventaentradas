package com.matias.ventaentradas.dto.error;

import java.time.LocalDateTime;

public record MensajeError (
        Integer status,
        String error,
        String mensaje,
        LocalDateTime localDateTime
){
}
