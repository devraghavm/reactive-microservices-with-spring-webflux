package com.raghav.webfluxdemo.exceptionhandler;

import com.raghav.webfluxdemo.dto.InputFieldValidationResponse;
import com.raghav.webfluxdemo.exception.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InputValidationHandler {
    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputFieldValidationResponse> handleException(InputValidationException ex) {
        InputFieldValidationResponse response = new InputFieldValidationResponse();
        response.setErrorCode(ex.getErrorCode());
        response.setInput(ex.getInput());
        response.setMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
