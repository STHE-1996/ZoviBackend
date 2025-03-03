package com.zovi.assessment.sthembisobuthelezi.exception;

import org.springframework.http.HttpStatus;

public class CustomeException extends RuntimeException {
    private int responseCode;
    private String responseMessage;
    private String developerMessage;
    private HttpStatus statusCode;

    // Getters and Setters

    public int getResponseCode() {
        return responseCode;
    }

    public CustomeException setResponseCode(int responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public CustomeException setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
        return this;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public CustomeException setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
        return this;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public CustomeException setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
        return this;
    }
}
