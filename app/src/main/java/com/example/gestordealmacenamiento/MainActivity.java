package com.example.gestordealmacenamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Clase principal de la aplicación.
 *
 * @author <a href="mailto:jgc1031@alu.ubu.es">José Gallardo Caballero</a>
 * @version 1.0
 * @serial 2024/02/17
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Código de solicitud para seleccionar un archivo.
     *
     * @since 1.0
     */
    private final int FILE_SELECT_CODE = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Llamar al método onCreate de la superclase.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtener el nombre de la aplicación desde los recursos de cadena.
        String appName = getString(R.string.app_name);

        // Crear una carpeta en Documents con el nombre de la aplicación.
        File directory = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOCUMENTS), appName);
        if (!directory.exists()) {
            directory.mkdirs(); // Crea el directorio si no existe.
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Llamar al método onActivityResult de la superclase.
        super.onActivityResult(requestCode, resultCode, data);

        // Si el código de solicitud es seleccionar un archivo y el resultado es correcto
        // y los datos no son nulos, entonces obtener la URI (Identificador de Recursos Uniforme) del archivo seleccionado.
        if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK && data != null &&
                data.getData() != null) {
            Uri selectedFileUri = data.getData();

            // Obtener el nombre de la aplicación desde los recursos de cadena.
            String appName = getString(R.string.app_name);
            File directory = new File(Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DOCUMENTS), appName);
            if (!directory.exists()) {
                // Crear el directorio si no existe
                directory.mkdirs();
            }

            // Crear un nuevo archivo en la carpeta de la aplicación
            // con el nombre del archivo seleccionado.
            File newFile = new File(directory, getFileName(selectedFileUri));

            try {
                InputStream in = getContentResolver().openInputStream(selectedFileUri);
                OutputStream out = new FileOutputStream(newFile);

                // Copiar el archivo seleccionado en el nuevo archivo.
                byte[] buf = new byte[1024];
                int len;
                while (true) {
                    assert in != null;
                    if (!((len = in.read(buf)) > 0)) {
                        break;
                    }
                    // Escribir el archivo en la carpeta de la aplicación.
                    out.write(buf, 0, len);
                }
                // Cerrar los flujos de entrada y salida.
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método para abrir la aplicación de configuración.
     *
     * @author <a href="mailto:jgc1031@alu.ubu.es">José Gallardo Caballero</a>
     * @param view Parámetro de vista.
     */
    public void openSettings(android.view.View view) {
        android.content.Intent intent = new android.content.Intent
                (android.provider.Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
        startActivity(intent);
    }

    /**
     * Método para subir un archivo a la carpeta de la aplicación.
     *
     * @author <a href="mailto:jgc1031@alu.ubu.es">José Gallardo Caballero</a>
     * @param view Parámetro de vista.
     */
    public void uploadFile(android.view.View view) {
        android.content.Intent intent = new android.content.Intent
                (android.content.Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, FILE_SELECT_CODE);
    }

    /**
     * Método para obtener el nombre del archivo.
     *
     * @author <a href="mailto:jgc1031@alu.ubu.es">José Gallardo Caballero</a>
     * @param uri Parámetro de uri.
     * @return Devuelve el nombre del archivo.
     */
    private String getFileName(Uri uri) {
        String result = null;
        // Si el esquema de la URI es "content", que significa que el
        // archivo está en la memoria interna.
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query
                    (uri, null, null, null, null)) {
                // Si el cursor no es nulo y se mueve a la primera fila.
                if (cursor != null && cursor.moveToFirst()) {
                    // Obtener el índice de la columna DISPLAY_NAME.
                    int columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (columnIndex != -1) {
                        // Obtener el nombre del archivo.
                        result = cursor.getString(columnIndex);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            assert result != null;
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                // Obtener el nombre del archivo
                result = result.substring(cut + 1);
            }
        }
        // Devolver el nombre del archivo
        return result;
    }
}