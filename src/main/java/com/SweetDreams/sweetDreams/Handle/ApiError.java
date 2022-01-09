package com.SweetDreams.sweetDreams.Handle;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ApiError {

    private HttpStatus status;
    private Integer code;
    private String message;
    private List<String> errors;


    public ApiError(HttpStatus status, Integer code, String message, List<String> errors) {
        super();
        this.status = status;
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, Integer code, String message, String error) {
        super();
        this.status = status;
        this.code = code;
        this.message = message;
        errors = Arrays.asList(error);
    }

    public ApiError(HttpStatus status, Integer code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public ApiError() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


}
