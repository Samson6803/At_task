package com.Sam.demo.Exceptions;

public class ApiUserNotFoundException extends RuntimeException{
    public ApiUserNotFoundException(String message){
        super(message);
    }
}
