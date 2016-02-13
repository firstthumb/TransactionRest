package com.ekocaman.demo.advice;

import com.ekocaman.demo.exc.InvalidParameterException;
import com.ekocaman.demo.exc.TransactionNotFoundException;
import com.ekocaman.demo.response.ImmutableStatusResponse;
import com.ekocaman.demo.response.StatusResponse;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {
    private static Logger LOG = LoggerFactory.getLogger(ExceptionHandlerControllerAdvice.class);

    @ExceptionHandler(TransactionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleTransactionNotFoundException(Exception e) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        StatusResponse errorResponse = ImmutableStatusResponse.builder().status("error").build();
        LOG.error("Transaction not found ==> ", e);

        return new ResponseEntity<Object>(errorResponse, headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidParameterException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleInvalidParameterException(Exception e) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        StatusResponse errorResponse = ImmutableStatusResponse.builder().status("error").build();
        LOG.error("Invalid parameter ==> ", e);

        return new ResponseEntity<Object>(errorResponse, headers, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleIllegalStateException(Exception e) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        StatusResponse errorResponse = ImmutableStatusResponse.builder().status("error").build();
        LOG.error("Illegal state ==> ", e);

        return new ResponseEntity<Object>(errorResponse, headers, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleException(Exception e) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        StatusResponse errorResponse = ImmutableStatusResponse.builder().status("error").build();
        LOG.error("Internal Server Error ==> ", e);
        LOG.error("Exception details : {}", Throwables.getStackTraceAsString(e));

        return new ResponseEntity<Object>(errorResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
