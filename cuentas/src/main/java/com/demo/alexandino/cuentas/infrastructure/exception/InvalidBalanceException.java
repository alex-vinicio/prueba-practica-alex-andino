package com.demo.alexandino.cuentas.infrastructure.exception;

public class InvalidBalanceException extends RuntimeException{
    public InvalidBalanceException(String message) {
        super(message);
    }
}
