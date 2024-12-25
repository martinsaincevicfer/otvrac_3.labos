package com.otvrac.backend.wrapper;

public class ResponseWrapper {
    private String status;
    private Object data;

    public ResponseWrapper(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }
}
