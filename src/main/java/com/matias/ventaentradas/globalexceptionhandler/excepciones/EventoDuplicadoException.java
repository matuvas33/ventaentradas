package com.matias.ventaentradas.globalexceptionhandler.excepciones;

public class EventoDuplicadoException extends RuntimeException{
    public EventoDuplicadoException(String message) {
        super(message);
    }
}
