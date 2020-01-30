package com.every.every_server.handler;

import com.every.every_server.domain.vo.http.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity handleHttpClientErrorException(HttpClientErrorException e) {
        Response data = new Response(e.getStatusCode(), e.getMessage());
        return new ResponseEntity(data, e.getStatusCode());
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Response handleWebExchangeBindException(WebExchangeBindException e) {
        return new Response(HttpStatus.BAD_REQUEST, "검증 오류.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Response handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new Response(HttpStatus.BAD_REQUEST, "검증 오류.");
    }
}
