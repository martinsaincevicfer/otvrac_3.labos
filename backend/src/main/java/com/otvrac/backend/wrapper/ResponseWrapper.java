package com.otvrac.backend.wrapper;

import java.util.List;

public class ResponseWrapper {
    private String status;
    private String message;
    private Object response;
    private List<Link> links;

    public ResponseWrapper(String status, String message, Object response) {
        this.status = status;
        this.message = message;
        this.response = response;
    }

    // Getteri i setteri
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
