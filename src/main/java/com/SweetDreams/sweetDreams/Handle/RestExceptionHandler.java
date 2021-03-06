package com.SweetDreams.sweetDreams.Handle;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, status.value(), "Par??metro(s) inv??lido", errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    //exception para tratar quando a requisi????o est?? com parametros faltando
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errors = "Par??metro(s) inv??lidos " + ex.getParameterName();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, status.value(), ex.getLocalizedMessage(), errors);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(AuthorizationExceptionHandle.class)
    public ResponseEntity<Object> authorization(AuthorizationExceptionHandle e, HttpServletRequest request){
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN,HttpStatus.FORBIDDEN.value(), e.getMessage(), (List<String>) null);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());

    }


//    /*CONFLITO COM O SWAGGER POR CONTA DAS NOTA??OES, exception para tratar caminho n??o existente
//    spring.mvc.throw-exception-if-no-handler-found=true
//    spring.resources.add-mappings=false
//    */
//    @ExceptionHandler(NoHandlerFoundException.class)
//    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
//                                                                   HttpStatus status, WebRequest request) {
//        String errors = "Caminho n??o especificado: " + ex.getHttpMethod() + " " + ex.getRequestURL();
//        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, status.value(), ex.getLocalizedMessage(), errors);
//        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
//    }


}

