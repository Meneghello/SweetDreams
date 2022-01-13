package com.SweetDreams.sweetDreams.Handle;

public class AuthorizationExceptionHandle extends RuntimeException{

    public AuthorizationExceptionHandle(String msg){
        super(msg);
    }
    public AuthorizationExceptionHandle(String msg, Throwable cause){
        super(msg,cause);
    }
}
