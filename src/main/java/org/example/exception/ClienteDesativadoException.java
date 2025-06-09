package org.example.exception;

public class ClienteDesativadoException extends RuntimeException {
    public ClienteDesativadoException(String message) {
        super(message);
    }
}