package com.example.demo.exception.handler;

import com.example.demo.dto.ResponseMessage;
import com.example.demo.exception.model.BadRequestExcep;
import com.example.demo.exception.model.RecordNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@SuppressWarnings({"unchecked", "rawtypes"})
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllException(Exception ex, WebRequest request){
        return new ResponseEntity<>(new ResponseMessage(ex.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RecordNotFound.class)
    public ResponseEntity<?> handleIndexOutOfBound(Exception ex, WebRequest request){
        return new ResponseEntity<>(new ResponseMessage("Object does not exit!"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestExcep.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request){
        return new ResponseEntity<>(new ResponseMessage("Validation Failed"), HttpStatus.BAD_REQUEST);
    }
}
