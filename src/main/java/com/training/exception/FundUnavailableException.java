package com.training.exception;

public class FundUnavailableException extends RuntimeException{

    private String message;

    public FundUnavailableException(String message){
        super(message);
        this.message = message;
    }

    public FundUnavailableException(){

    }
}
