package com.matias.ventaentradas.globalexceptionhandler.excepciones;

public class EventoNoEncontradoException extends RuntimeException{
    public EventoNoEncontradoException(String message) {
        super(message);
    }
}
