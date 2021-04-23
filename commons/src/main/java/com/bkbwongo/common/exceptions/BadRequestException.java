package com.bkbwongo.common.exceptions;

/**
 * @author bkaaron
 * @created on 23/04/2021
 * @project ebaasa-sms
 */
public class BadRequestException extends RuntimeException{

    final String message;

    public BadRequestException(String message) {
        super(message);
        this.message = message;
    }

    public BadRequestException(String message, Object ... messageConstants){
        this.message = String.format(message, messageConstants);
    }

}
