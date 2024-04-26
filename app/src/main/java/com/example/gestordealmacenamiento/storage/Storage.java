package com.example.gestordealmacenamiento.storage;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.gestordealmacenamiento.R;
import com.example.gestordealmacenamiento.exception.insufficientSpaceException;
import com.example.gestordealmacenamiento.exception.noDirectoryException;
import com.example.gestordealmacenamiento.exception.noFileException;
import com.example.gestordealmacenamiento.user.User;

import java.io.File;
import java.util.List;

/**
 * Clase que representa un almacenamiento de un dispositivo.
 *
 * @author <a href="mailto:a@a.com">a</a>
 * @version 1.0
 * @since 1.0
 * @serial 2024/04/15
 */
public class Storage {

    /**
     * Espacio total del almacenamiento (MB).
     */
    private double totalSpace;
    /**
     * Espacio disponible del almacenamiento (MB).
     */
    private double availableSpace;
    /**
     * Lista de usuarios que tienen acceso al almacenamiento (MB).
     */
    private List<User> users;

    public Storage() {
        this.totalSpace = 10000;
        this.availableSpace = totalSpace;
        this.users = null;
    }

    /**
     * Constructor de la clase.
     *
     * @param totalSpace Espacio total del almacenamiento.
     * @param users Lista de usuarios que tienen acceso al almacenamiento.
     */
    public Storage(double totalSpace, List<User> users) {
        this.totalSpace = totalSpace;
        this.availableSpace = totalSpace;
        this.users = users;
    }

    /**
     * Método que devuelve el espacio total del almacenamiento.
     *
     * @return Espacio total del almacenamiento.
     */
    public double getTotalSpace() {
        return totalSpace;
    }

    /**
     * Método que devuelve el espacio disponible del almacenamiento.
     *
     * @return Espacio disponible del almacenamiento.
     */
    public double getAvailableSpace() {
        return availableSpace;
    }

    /**
     * Método que devuelve la lista de usuarios que tienen acceso al almacenamiento.
     *
     * @return Lista de usuarios que tienen acceso al almacenamiento.
     */
    public List<User> getUsersList() {
        // TODO implement here
        return null;
    }

    /**
     * Método que crea una carpeta llamada como el nombre de la aplicación dentro del directorio
     * documentos. Si la carpeta ya existe, no hace nada.
     *
     * TODO: Arreglar cuando se pueda el comprobamiento de si la carpeta ya existe.
     */
    public void createAppFolder(Context context) throws noDirectoryException {
        String appName = context.getString(R.string.app_name);
        File documentsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File appDirectory = new File(documentsDirectory, appName);

        // Crear la carpeta de la aplicación si no existe
        if (!appDirectory.exists()) {
            boolean wasDirectoryMade = appDirectory.mkdirs();
            if (!wasDirectoryMade) {
                throw new noDirectoryException("No se pudo crear la carpeta de la aplicación.");
            }
        }
    }

    /**
     * Método que agrega un archivo a la carpeta de la aplicación.
     * Si la adición del archivo excede el límite de tamaño de la carpeta, no se agrega el archivo.
     *
     * @param context el contexto de la aplicación.
     * @param file el archivo a agregar.
     * @throws insufficientSpaceException si no hay suficiente espacio en la carpeta.
     * @throws noFileException si el archivo no existe.
     * @throws noDirectoryException si la carpeta de la aplicación no existe.
     * @return true si el archivo se agregó con éxito, false en caso contrario.
     */
    public void addFileToAppFolder(Context context, File file) throws insufficientSpaceException, noFileException, noDirectoryException {
        String appName = context.getString(R.string.app_name);
        File documentsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File appDirectory = new File(documentsDirectory, appName);

        boolean directoryExists = appDirectory.mkdirs();
        boolean fileExists = file.exists();
        boolean thereIsSpace = getAvailableSpace() - file.length() > 0;

        if (!directoryExists) {
            throw new noDirectoryException();
        }

        if (!fileExists) {
            throw new noFileException();
        }

        if (!thereIsSpace) {
            throw new insufficientSpaceException();
        }

        File newFile = new File(appDirectory, file.getName());
        availableSpace -= file.length();
    }
}