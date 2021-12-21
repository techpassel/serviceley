package com.tp.serviceley.server.exception;
import java.security.GeneralSecurityException;

public class BackendException extends RuntimeException {
    public BackendException(String message){
        super(message);
    }

    public BackendException(String message, Exception e) {
        super(message, e);
    }
}
