package com.projectwp.journalApp.exception;


public class BadRequestException extends RuntimeException{
    public BadRequestException(String msg){
        super(msg);
    }
}
