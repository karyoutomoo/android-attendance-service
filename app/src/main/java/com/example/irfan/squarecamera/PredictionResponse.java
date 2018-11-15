package com.example.irfan.squarecamera;

public class PredictionResponse {
    String id;
    long probability;
    String message;
    String status;
    String validation;


    public PredictionResponse(String id, long probability, String message, String status, String validation) {
        this.id = id;
        this.probability = probability;
        this.message = message;
        this.status = status;
        this.validation = validation;
    }

    public long getProbability() {
        return probability;
    }

    public void setProbability(long probability) {
        this.probability = probability;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public PredictionResponse(String id, String message, String status, String validation) {
        this.id = id;
        this.message = message;
        this.status = status;
        this.validation = validation;
    }
}
