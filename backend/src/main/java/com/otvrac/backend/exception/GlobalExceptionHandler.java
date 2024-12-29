package com.otvrac.backend.exception;

import com.otvrac.backend.wrapper.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNotFound(NoHandlerFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseWrapper("Not Found", "The requested endpoint does not exist", null));
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<?> handleNotImplemented(UnsupportedOperationException ex) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body(new ResponseWrapper("Not Implemented", ex.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseWrapper("Error", "An unexpected error occurred: " + ex.getMessage(), null));
    }
}
