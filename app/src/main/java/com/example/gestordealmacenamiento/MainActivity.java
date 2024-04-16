package com.example.gestordealmacenamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Clase principal de la aplicación.
 *
 * @author <a href="mailto:jgc1031@alu.ubu.es">José Gallardo Caballero</a>
 * @author <a href="mailto:jma1037@alu.ubu.es">José María Martínez Alcalde</a>
 * @author <a href="mailto:jvw1001@alu.ubu.es">José Javier Velasco Whu</a>
 * @author <a href="mailto:mrp1024@alu.ubu.es">Mario Ruiz Puente</a>
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Crear el directorio de la aplicación
        createDirectory();
        // Actualizar la lista de archivos
        updateFileList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Llamar al método onActivityResult de la superclase.
        super.onActivityResult(requestCode, resultCode, data);

        // Si el código de solicitud es seleccionar un archivo y el resultado es correcto
        // y los datos no son nulos, entonces obtener la URI (Identificador de Recursos Uniforme)
        // del archivo seleccionado.
        if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK && data != null &&
                data.getData() != null) {
            Uri selectedFileUri = data.getData();

            // Crear un nuevo archivo en la carpeta de la aplicación
            File directory = new File(Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DOCUMENTS), getString(R.string.app_name));
            // con el nombre del archivo seleccionado.
            File newFile = new File(directory, getFileName(selectedFileUri));

            // Copiar el archivo seleccionado en el nuevo archivo.
            try {
                InputStream in = getContentResolver().openInputStream(selectedFileUri);
                OutputStream out;
                // Crear un flujo de salida para el nuevo archivo.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    out = Files.newOutputStream(newFile.toPath());
                } else {
                    throw new IOException("Error al crear el flujo de salida. Versión antigua de " +
                            "android encontrada");
                }

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
                // Actualizar la lista de archivos.
                updateFileList();
            } catch (IOException e) {
                // Mostrar un mensaje de error y después cerrar la aplicación.
                Toast.makeText(this, e.getMessage(),
                        Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    /**
     * Método para abrir la aplicación de configuración.
     *
     * @param view Parámetro de vista.
     */
    public void openAppSettings(View view) {
        android.content.Intent intent = new android.content.Intent
                (android.provider.Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
        startActivity(intent);
    }

    /**
     * Método para subir un archivo a la carpeta de la aplicación.
     */
    @SuppressWarnings("deprecation")
    public void uploadFile(View view) {
        android.content.Intent intent = new android.content.Intent
                (android.content.Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, FILE_SELECT_CODE);
    }

    /**
     * Método para obtener el nombre del archivo.
     *
     * @param uri Parámetro de uri.
     * @return Devuelve el nombre del archivo.
     */
    private String getFileName(Uri uri) {
        String result = null;
        // Si el esquema de la URI es "content", que significa que el
        // archivo está en la memoria interna.
        if (Objects.equals(uri.getScheme(), "content")) {
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

    /**
     * Método para crear un directorio.
     *
     * @since 1.0
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createDirectory() {
        String appName = getString(R.string.app_name);
        File directory = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOCUMENTS), appName);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * Método para actualizar la lista de archivos.
     *
     * @since 1.0
     */
    private void updateFileList() {
        String appName = getString(R.string.app_name);
        File directory = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOCUMENTS), appName);
        File[] files = directory.listFiles();
        List<String> fileNames = new ArrayList<>();
        assert files != null;
        for (File file : files) {
            fileNames.add(file.getName());
        }
        ListView listView = findViewById(R.id.file_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, fileNames);
        listView.setAdapter(adapter);
    }
}