package com.bkbwongo.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author bkaaron
 * @created on 23/04/2021
 * @project ebaasa-sms
 */

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException apiRequestException){

        HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;

        ExceptionPayLoad exceptionPayLoad = new ExceptionPayLoad(
                apiRequestException.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return  new ResponseEntity<>(exceptionPayLoad, httpStatus);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(BadRequestException badRequestException){

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ExceptionPayLoad exceptionPayLoad = new ExceptionPayLoad(
                badRequestException.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return  new ResponseEntity<>(exceptionPayLoad, httpStatus);
    }
}
