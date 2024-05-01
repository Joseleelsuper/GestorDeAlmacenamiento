package com.example.gestordealmacenamiento.files;

import com.example.gestordealmacenamiento.session.Login;
import java.util.List;

/**
 * Clase que representa un archivo.
 *
 * @author <a href="mailto:a@a.com">a</a>
 * @version 1.0
 * @since 1.0
 * @serial 2024/04/15
 */
public class File {

    /**
     * Nombre del archivo.
     */
    private String name;
    /**
     * Formato del archivo.
     */
    private Enum format;
    /**
     * Tamaño del archivo.
     */
    private double size;
    /**
     * Ruta del archivo.
     */
    private String path;
    /**
     * Propietario del archivo.
     */
    private Login owner;
    /**
     * Usuarios con los que se ha compartido el archivo.
     */
    private List<Login> sharedWith;

    /**
     * Constructor de la clase.
     *
     * @param name Nombre del archivo.
     * @param format Formato del archivo.
     * @param size Tamaño del archivo.
     * @param path Ruta del archivo.
     * @param owner Propietario del archivo.
     * @param sharedWith Usuarios con los que se ha compartido el archivo.
     */
    public File(String name, Enum format, double size, String path, Login owner, List<Login> sharedWith) {
        this.name = name;
        this.format = format;
        this.size = size;
        this.path = path;
        this.owner = owner;
        this.sharedWith = sharedWith;
    }

    /**
     * Método que renombra un archivo.
     *
     * @param newName Nuevo nombre del archivo.
     */
    public void renameFile(String newName) {
        // TODO implement here
    }

    /**
     * Método que mueve un archivo a una nueva ruta.
     *
     * @param newPath Nueva ruta del archivo.
     */
    public void moveFile(String newPath) {
        // TODO implement here
    }

    /**
     * Método que permite compartir un archivo con un usuario.
     *
     * @param user Usuario con el que se comparte el archivo.
     */
    public void shareWith(Login user) {
        // TODO implement here
    }

    /**
     * Método que permite dejar de compartir un archivo con un usuario.
     *
     * @param user Usuario con el que se deja de compartir el archivo.
     */
    public void unhareWith(Login user) {
        // TODO implement here
    }
}
