package com.example.gestordealmacenamiento.exception;

/**
 * Clase que gestiona los errores de la aplicación.
 *
 * @author <a href="mailto:a@a.com">a</a>
 * @version 1.0
 * @since 1.0
 * @serial 2024/04/15
 */
public class insufficientSpaceException extends Exception {

    private static final long serialVersionUID = 1L;

    public insufficientSpaceException() {
        super("Error 001: Espacio insuficiente en la nube para almacenar el archivo.");
    }

    public insufficientSpaceException(String message) {
        super(message);
    }

    public insufficientSpaceException(Throwable cause) {
        super(cause);
    }

    public insufficientSpaceException(String message, Throwable cause) {
        super(message, cause);
    }
}
