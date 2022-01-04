package com.SweetDreams.sweetDreams.Handle;

import java.util.List;

public class ErrorResponse {
    String message;
    int code;
    String status;
    List<ErrorObject> error;

    public ErrorResponse(String message, int code, String status, List<ErrorObject> error) {
        this.message = message;
        this.code = code;
        this.status = status;
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ErrorResponse() {
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public List<ErrorObject> getError() {
        return error;
    }

    public void setError(List<ErrorObject> error) {
        this.error = error;
    }
}
