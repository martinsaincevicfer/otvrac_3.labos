package com.otvrac.backend.wrapper;

public class ResponseWrapper {
    private String status;
    private String message;
    private Object response;

    public ResponseWrapper(String status, String message, Object response) {
        this.status = status;
        this.message = message;
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getResponse() {
        return response;
    }
}
