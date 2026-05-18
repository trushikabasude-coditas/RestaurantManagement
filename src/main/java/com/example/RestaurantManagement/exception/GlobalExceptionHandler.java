package com.example.RestaurantManagement.exception;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public Response<Map<String,Object>> handleRuntime(RuntimeException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleValidation(MethodArgumentNotValidException ex) throws  Exception{

    String message =ex.getBindingResult().getFieldErrors()
            .stream()
            .map(e->e.getField()+ e.getDefaultMessage())
            .orElse("Validation failed");
        return buildResponse(Htt)
    }
}
