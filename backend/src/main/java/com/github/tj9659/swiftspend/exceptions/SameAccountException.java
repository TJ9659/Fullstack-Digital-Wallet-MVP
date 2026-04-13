package com.github.tj9659.swiftspend.exceptions;

public class SameAccountException extends RuntimeException{
    public SameAccountException(String message){
        super(message);
    }
}
