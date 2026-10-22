package com.matias.ventaentradas.globalexceptionhandler;

import com.matias.ventaentradas.dto.error.MensajeError;
import com.matias.ventaentradas.globalexceptionhandler.excepciones.EventoDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EventoDuplicadoException.class)
    public MensajeError eventoDuplicado(EventoDuplicadoException ex){
        return new MensajeError(
                HttpStatus.CONFLICT.value(),
                "CONFLICT",
                ex.getMessage(),
                LocalDateTime.now()
        );
    }

}
