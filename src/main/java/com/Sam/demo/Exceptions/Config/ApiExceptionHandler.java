package com.Sam.demo.Exceptions.Config;

import com.Sam.demo.Exceptions.Exceptions.ApiParsingException;
import com.Sam.demo.Exceptions.Exceptions.ApiUnhandledFormatException;
import com.Sam.demo.Exceptions.Exceptions.ApiUserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiUserNotFoundException.class)
    ResponseEntity<Object> handleUserNotFoundException(ApiUserNotFoundException e){
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiException exception = new ApiException(
                notFound.value(),
                e.getMessage()
        );
        return new ResponseEntity<>(exception, notFound);
    }

    @ExceptionHandler(value = ApiUnhandledFormatException.class)
    ResponseEntity<Object> handleUnhandledFormatException(ApiUnhandledFormatException e){
        HttpStatus notAcceptable = HttpStatus.NOT_ACCEPTABLE;
        ApiException exception = new ApiException(
                notAcceptable.value(),
                e.getMessage()
        );
        return new ResponseEntity<>(exception, notAcceptable);
    }

    @ExceptionHandler(value = ApiParsingException.class)
    ResponseEntity<Object> handleParsingException(ApiParsingException e){
        HttpStatus serverError = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException exception = new ApiException(
                serverError.value(),
                e.getMessage()
        );
        return new ResponseEntity<>(exception, serverError);
    }

}
