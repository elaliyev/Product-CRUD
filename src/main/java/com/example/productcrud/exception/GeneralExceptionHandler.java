package com.example.productcrud.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(NotFoundException ex) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "Resource Not Found" ,details);

        return new ResponseEntity<>(apiError, apiError.getStatus());

    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(Exception ex) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Bad Request" ,details);

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }



    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {

        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Error occurred" ,details);

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
