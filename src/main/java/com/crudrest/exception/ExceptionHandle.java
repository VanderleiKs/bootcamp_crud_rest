package com.crudrest.exception;

import com.crudrest.dto.ResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandle extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ResponseMessage> clientExceptionHandle(RuntimeException ex){
        return ResponseEntity.badRequest().body(new ResponseMessage(ex.getMessage()));
    }
}
