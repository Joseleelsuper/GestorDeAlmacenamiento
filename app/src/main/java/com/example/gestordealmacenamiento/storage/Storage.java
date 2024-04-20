package com.example.gestordealmacenamiento.storage;

import android.content.Context;
import android.os.Environment;

import com.example.gestordealmacenamiento.R;
import com.example.gestordealmacenamiento.user.User;

import java.io.File;
import java.util.List;
import java.util.Objects;

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
        // TODO implement here
        return  0;
    }

    /**
     * Método que devuelve el espacio disponible del almacenamiento.
     *
     * @return Espacio disponible del almacenamiento.
     */
    public double getAvailableSpace() {
        // TODO implement here
        return 0;
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
     */
    public void createAppFolder(Context context) {
        String appName = context.getString(R.string.app_name);
        File documentsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File appDirectory = new File(documentsDirectory, appName);

        if (!appDirectory.exists()) {
            boolean wasDirectoryMade = appDirectory.mkdirs();
        }
    }

    /**
     * Método que agrega un archivo a la carpeta de la aplicación.
     * Si la adición del archivo excede el límite de tamaño de la carpeta, no se agrega el archivo.
     *
     * @param context el contexto de la aplicación
     * @param file el archivo a agregar
     * @return true si el archivo se agregó con éxito, false en caso contrario
     */
    public boolean addFileToAppFolder(Context context, File file) {
        String appName = context.getString(R.string.app_name);
        File documentsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File appDirectory = new File(documentsDirectory, appName);

        if (!appDirectory.exists() || !file.exists() || getFolderSize() + file.length() > availableSpace) {
            return false;
        }

        File newFile = new File(appDirectory, file.getName());
        availableSpace -= file.length();
        return file.renameTo(newFile);
    }

    /**
     * Método que devuelve el tamaño de una carpeta en MB.
     *
     * @return el tamaño de la carpeta en bytes
     */
    private double getFolderSize() {
        return availableSpace;
    }

}