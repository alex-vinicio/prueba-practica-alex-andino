package com.demo.alexandino.cuentas.infrastructure.exception;

public class AccountDuplicatedException extends RuntimeException{
    public AccountDuplicatedException(String message) {
        super(message);
    }
}
