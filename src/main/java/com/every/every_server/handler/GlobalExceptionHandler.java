package com.every.every_server.handler;

import com.every.every_server.domain.vo.http.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity HttpExceptionHandler(HttpClientErrorException e) {
        Response data = new Response(e.getStatusCode(), e.getMessage());
        return new ResponseEntity(data, e.getStatusCode());
    }
}
