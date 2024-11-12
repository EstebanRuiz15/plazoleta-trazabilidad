package com.restaurants.trazabilidad.domain.exception;

public class ErrorFeignException extends RuntimeException {
    public ErrorFeignException(String message){
        super (message);
    }
}
