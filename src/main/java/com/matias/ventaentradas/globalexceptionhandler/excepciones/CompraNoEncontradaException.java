package com.matias.ventaentradas.globalexceptionhandler.excepciones;

public class CompraNoEncontradaException extends RuntimeException{
    public CompraNoEncontradaException(String message) {
        super(message);
    }
}
