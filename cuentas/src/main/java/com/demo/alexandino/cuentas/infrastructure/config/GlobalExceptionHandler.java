package com.demo.alexandino.cuentas.infrastructure.config;

import com.demo.alexandino.cuentas.infrastructure.exception.AccountDuplicatedException;
import com.demo.alexandino.cuentas.infrastructure.exception.AccountNotFoundException;
import com.demo.alexandino.cuentas.infrastructure.exception.InvalidBalanceException;
import com.demo.alexandino.cuentas.infrastructure.exception.MovementNotFoundException;
import com.demo.alexandino.cuentas.infrastructure.web.dto.ErrorMessage;
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
    private static final String NOT_FOUND_CLIENT = "Not Found";
    private static final String INVALID_BODY = "Invalid request";
    private static final String BAD_REQUEST = "Bad Request";
    private static final String INVALID_BODY_MESSAGE = "error en parametros recibidos";
    private static final String ERROR_DUPLICATED_LOG = "Error duplicated account: {}";
    private static final String ERROR_NOT_FOUND_LOG = "Error not found account: {}";
    private static final String ERROR_INVALID_BODY_LOG = "Error request account: {}";

    @ExceptionHandler(AccountDuplicatedException.class)
    public ResponseEntity<ErrorMessage> handleAccountDuplicatedException(AccountDuplicatedException ex) {
        log.error(ERROR_DUPLICATED_LOG, ex.getMessage());
        ErrorMessage body = new ErrorMessage(BAD_REQUEST,ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleAccountDuplicatedException(AccountNotFoundException ex) {
        log.error(ERROR_NOT_FOUND_LOG, ex.getMessage());
        ErrorMessage body = new ErrorMessage(NOT_FOUND_CLIENT,ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
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

    @ExceptionHandler(MovementNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleMovementNotFoundException(MovementNotFoundException ex) {
        log.error("Error not found movement: {} ", ex.getMessage());
        ErrorMessage body = new ErrorMessage(NOT_FOUND_CLIENT,ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidBalanceException.class)
    public ResponseEntity<ErrorMessage> handleInvalidBalanceException(InvalidBalanceException ex) {
        log.error("Error fondos movement: {} ", ex.getMessage());
        ErrorMessage body = new ErrorMessage(BAD_REQUEST,ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
