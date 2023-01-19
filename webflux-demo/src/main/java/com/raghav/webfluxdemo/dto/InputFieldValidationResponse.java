package com.raghav.webfluxdemo.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InputFieldValidationResponse {
    private int errorCode;
    private int input;
    private String message;
}
