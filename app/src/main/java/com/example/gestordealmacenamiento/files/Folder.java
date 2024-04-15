package com.example.gestordealmacenamiento.files;

import java.util.List;

/**
 * Clase que representa una carpeta en el sistema de archivos.
 *
 * @author <a href="mailto:a@a.com">a</a>
 * @version 1.0
 * @since 1.0
 * @serial 2024/04/15
 */
public class Folder {

    /**
     * Nombre de la carpeta.
     */
    private String name;
    /**
     * Ruta de la carpeta.
     */
    private String path;
    /**
     * Lista de archivos de la carpeta.
     */
    private List<File> files;
    /**
     * Lista de subcarpetas de la carpeta.
     */
    private List<Folder> folders;
    /**
     * Carpeta padre de la carpeta.
     */
    private Folder parentFolder;

    /**
     * Constructor de la clase.
     *
     * @param name Nombre de la carpeta.
     * @param path Ruta de la carpeta.
     */
    public Folder(String name, String path) {
        this.name = name;
        this.path = path;
    }

    /**
     * Método que crea una carpeta con el nombre especificado.
     *
     * @param name Nombre de la carpeta.
     */
    public void createFolder(String name) {
        // TODO implement here
    }

    /**
     * Método que crea un archivo con el nombre especificado.
     *
     * @param folder Carpeta a eliminar.
     */
    public void deleteFolder(Folder folder) {
        // TODO implement here
    }

    /**
     * Método que renombra una carpeta.
     *
     * @param folder Carpeta a renombrar.
     * @param newPath Nueva ruta de la carpeta.
     */
    public  void moveFolder(Folder folder, String newPath) {
        // TODO implement here
    }
}
