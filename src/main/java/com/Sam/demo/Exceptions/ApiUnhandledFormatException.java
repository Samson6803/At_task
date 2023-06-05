package com.Sam.demo.Exceptions;

public class ApiUnhandledFormatException extends RuntimeException{
    public ApiUnhandledFormatException(String message){
        super(message);
    }
}
