package com.demo.alexandino.clientes.exception;

public class ClientDuplicatedException extends RuntimeException{
    public ClientDuplicatedException(String message) {
        super(message);
    }
}
