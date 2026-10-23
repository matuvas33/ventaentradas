package com.matias.ventaentradas.globalexceptionhandler.excepciones;

public class SinCapacidadException extends RuntimeException{
    public SinCapacidadException(String message) {
        super(message);
    }
}
