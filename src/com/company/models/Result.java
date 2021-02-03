package com.company.models;

public class Result<T> {

    boolean isSuccess;
    String message;
    T model;

    public Result(boolean isSuccess, String message, T model) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.model = model;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }


}
