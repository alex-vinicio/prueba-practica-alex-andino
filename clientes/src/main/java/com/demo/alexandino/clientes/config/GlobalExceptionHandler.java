package com.demo.alexandino.clientes.config;

import com.demo.alexandino.clientes.dto.ErrorMessage;
import com.demo.alexandino.clientes.exception.AesGcmFailCryptoException;
import com.demo.alexandino.clientes.exception.ClientDuplicatedException;
import com.demo.alexandino.clientes.exception.ClientNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.id.IdentifierGenerationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static final String DUPLICATED_CLIENT = "Duplicated client";
    private static final String NOT_FOUND_CLIENT = "Not Found";
    private static final String ERROR_DUPLICATED_LOG = "Error duplicated client: {}";
    private static final String ERROR_NOT_FOUND_LOG = "Error not found client: {}";
    private static final String ERROR_AES_ALGORITHM = "Error AES-GCM: {}";
    private static final String FAIL_AES_CRYPTO = "FAIL ENCRYPTED/DECRYPTED";
    private static final String INVALID_BODY = "Invalid request";
    private static final String INVALID_BODY_MESSAGE = "error en parametros recibidos";
    private static final String ERROR_INVALID_BODY_LOG = "Error request account: {}";

    @ExceptionHandler(ClientDuplicatedException.class)
    public ResponseEntity<ErrorMessage> handleClientDuplicatedException(ClientDuplicatedException ex) {
        log.error(ERROR_DUPLICATED_LOG, ex.getMessage());
        ErrorMessage body = new ErrorMessage(DUPLICATED_CLIENT,ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleClientNotFoundException(ClientNotFoundException ex) {
        log.error(ERROR_NOT_FOUND_LOG, ex.getMessage());
        ErrorMessage body = new ErrorMessage(NOT_FOUND_CLIENT,ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AesGcmFailCryptoException.class)
    public ResponseEntity<ErrorMessage> handleAesGcmFailCryptoException(AesGcmFailCryptoException ex) {
        log.error(ERROR_AES_ALGORITHM, ex.getMessage());
        ErrorMessage body = new ErrorMessage(FAIL_AES_CRYPTO,ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IdentifierGenerationException.class)
    public ResponseEntity<ErrorMessage> handleIdentifierGenerationException(IdentifierGenerationException ex) {
        log.error(ERROR_INVALID_BODY_LOG, ex.getMessage());
        ErrorMessage body = new ErrorMessage(INVALID_BODY,INVALID_BODY_MESSAGE);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Error validate body: {}", ex.getMessage());
        ErrorMessage body = new ErrorMessage(INVALID_BODY,INVALID_BODY_MESSAGE);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Error enum: {} ", ex.getMessage());
        ErrorMessage body = new ErrorMessage(INVALID_BODY,INVALID_BODY_MESSAGE);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
