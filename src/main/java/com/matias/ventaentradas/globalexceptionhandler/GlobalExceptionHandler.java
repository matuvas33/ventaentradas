package com.matias.ventaentradas.globalexceptionhandler;

import com.matias.ventaentradas.dto.error.MensajeError;
import com.matias.ventaentradas.globalexceptionhandler.excepciones.EventoDuplicadoException;
import com.matias.ventaentradas.globalexceptionhandler.excepciones.EventoNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Locale;

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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EventoNoEncontradoException.class)
    public MensajeError eventoNoEncontrado(EventoNoEncontradoException ex){
        return new MensajeError(
                HttpStatus.NOT_FOUND.value(),
                "NOT FOUND",
                ex.getMessage(),
                LocalDateTime.now()
        );
    }

}
