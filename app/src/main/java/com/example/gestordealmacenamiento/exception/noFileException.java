package com.example.gestordealmacenamiento.exception;

public class noFileException extends Exception {

    private static final long serialVersionUID = 1L;

    public noFileException() {
        super("Error 003: El archivo no existe.");
    }

    public noFileException(String message) {
        super(message);
    }

    public noFileException(Throwable cause) {
        super(cause);
    }

    public noFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
