package com.ellixo.healthcare.rest.exception;

import com.ellixo.healthcare.exception.UnknownObjectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class GlobalControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(UnknownObjectException.class)
    public ResponseEntity<String> handle(UnknownObjectException e) {
        LOG.warn("Rest Error", e);
        return new ResponseEntity<>("{\"error\":\"" + e.getMessage() + "\"}", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(Exception e) {
        LOG.error("Rest Error", e);
        return new ResponseEntity<>("{\"error\":\"" + e.getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}