package com.SweetDreams.sweetDreams.Handle;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.List;


@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ErrorObject> errors = ApiError.getErrors((MethodArgumentNotValidException) ex);
        ErrorResponse errorResponse = ApiError.getErrorResponse(ex, status, errors);
        return new ResponseEntity<>(errorResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request){
        String errors =  "Parâmetro(s) inválidos " + ex.getParameterName();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, status.value() ,ex.getLocalizedMessage(),errors);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(),apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request){
        String errors ="Caminho não especificado: "+ ex.getHttpMethod()+" "+ex.getRequestURL();
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, status.value(), ex.getLocalizedMessage(),errors);
        return new ResponseEntity<>(apiError,new HttpHeaders(), apiError.getStatus());
    }




}

