package com.example.gestordealmacenamiento.exception;

public class noDirectoryException extends Exception {

    private static final long serialVersionUID = 1L;

    public noDirectoryException() {
        super("Error 002: El directorio no existe.");
    }

    public noDirectoryException(String message) {
        super(message);
    }

    public noDirectoryException(Throwable cause) {
        super(cause);
    }

    public noDirectoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
