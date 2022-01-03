package com.SweetDreams.sweetDreams.Handle;

public class ErrorObject {
    String message;
    String field;
    Object parameter;

    public ErrorObject(String message, String field, Object parameter) {
        this.message = message;
        this.field = field;
        this.parameter = parameter;
    }

    public ErrorObject() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getParameter() {
        return parameter;
    }

    public void setParameter(Object parameter) {
        this.parameter = parameter;
    }
}
