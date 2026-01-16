package com.projectwp.journalApp.exception;

public class ConflictException extends RuntimeException{
    public ConflictException(String msg){
        super(msg);
    }
}
