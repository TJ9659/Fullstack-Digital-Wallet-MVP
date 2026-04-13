package com.github.tj9659.swiftspend.exceptions;

public class EmailTakenException extends RuntimeException{
    public EmailTakenException(String message) {
        super(message);
    }
}
