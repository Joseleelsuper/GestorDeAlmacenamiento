package com.example.gestordealmacenamiento.storage;

import android.content.Context;
import android.os.Environment;

import com.example.gestordealmacenamiento.R;
import com.example.gestordealmacenamiento.presentation.PresentationScreen;
import com.example.gestordealmacenamiento.util.FinalVariables;

import java.io.File;

/**
 * Clase que representa un almacenamiento de un dispositivo.
 *
 * @author <a href="mailto:jgc1031@alu.ubu.es">José Gallardo Caballero</a>
 * @version 1.0
 * @since 1.0
 * @serial 2024/04/15
 */
public class Storage {
    /**
     * Método que crea una carpeta llamada como el nombre de la aplicación dentro del directorio
     * documentos. Si la carpeta ya existe, no hace nada.
     *
     */
    public boolean createAppFolder(Context context) {
        String appName = context.getString(R.string.app_name);
        File documentsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File appDirectory = new File(documentsDirectory, appName);
        boolean wasDirectoryMade = false;

        // Crear la carpeta de la aplicación si no existe
        if (!appDirectory.exists()) {
            wasDirectoryMade = appDirectory.mkdirs();
        }
        return wasDirectoryMade;
    }

    /**
     * Método que crea una carpeta llamada "users" dentro de la carpeta de la aplicación.
     * Si la carpeta ya existe, no hace nada.
     *
     * @param presentation Pantalla de presentación.
     */
    public boolean createUsersFolder(PresentationScreen presentation) {
        File appDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), presentation.getString(R.string.app_name));
        File usersDirectory = new File(appDirectory, FinalVariables.getUserDirectory());
        boolean created = false;

        // Crear la carpeta de los usuarios si no existe
        if (!usersDirectory.exists()) {
            created = usersDirectory.mkdirs();
        }
        return created;
    }

    /**
     * Método que crea una carpeta llamada "files" dentro de la carpeta de la aplicación.
     *
     * @param presentation Pantalla de presentación.
     */
    public boolean createFilesFolder(PresentationScreen presentation) {
        File appDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), presentation.getString(R.string.app_name));
        File filesDirectory = new File(appDirectory, FinalVariables.getFileDirectory());
        boolean created = false;

        // Crear la carpeta de los archivos si no existe
        if (!filesDirectory.exists()) {
            created =filesDirectory.mkdirs();
        }

        return created;
    }
}