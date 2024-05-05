package com.example.gestordealmacenamiento.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gestordealmacenamiento.R;

import java.io.File;
import java.io.FileOutputStream;
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
 * @serial 17/03/2024
 */
public class FilesScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_screen);

        // Llama al método para mostrar la lista de archivos
        displayFiles();
    }

    //TODO: Arreglar el display que no funciona.
    private void displayFiles() {
        // Obtén la referencia a la lista
        ListView filesListView = findViewById(R.id.files_listView);

        // Obtén el directorio "Files" de la aplicación
        File appDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), getString(R.string.app_name));
        File filesDirectory = new File(appDirectory, "Files");

        // Comprueba si el directorio existe
        if (filesDirectory.exists()) {
            // Obtiene la lista de archivos en el directorio
            File[] files = filesDirectory.listFiles();

            // Crea una lista de nombres de archivos
            List<String> fileNames = new ArrayList<>();
            if (files != null) {
                for (File file : files) {
                    fileNames.add(file.getName());
                }
            }

            // Crea un adaptador para la lista
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileNames);

            // Asigna el adaptador a la lista
            filesListView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No se encontró el directorio de archivos.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Método que cambia a la pantalla de inicio.
     *
     * @param view Vista actual.
     */
    public void goHome(View view) {
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }

    /**
     * Método que cambia a la pantalla de Me
     *
     * @param view Vista actual.
     */
    public void goMe(View view) {
        Intent intent = new Intent(this, MeScreen.class);
        startActivity(intent);
    }

    // Si le da click al boton de archivos, mandar un toast diciendo que ya esta en la pantalla de archivos
    public void goFiles(View view) {
        Toast.makeText(this, "Ya estás en la pantalla de archivos", Toast.LENGTH_SHORT).show();
    }

    /**
     * Método que permite añadir un archivo.
     *
     * @param view Vista actual.
     */
    public void addFile(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri uri = data.getData();

            // Obtén el nombre del archivo seleccionado
            assert uri != null;
            String fileName = getFileName(uri);

            // Crea un nuevo archivo en el directorio "Files" de la aplicación con el nombre del archivo seleccionado
            File appDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), getString(R.string.app_name));
            File filesDirectory = new File(appDirectory, "Files");
            File newFile = new File(filesDirectory, fileName);

            try {
                // Crea un InputStream del archivo seleccionado
                InputStream in = getContentResolver().openInputStream(uri);

                // Crea un OutputStream del nuevo archivo
                OutputStream out = Files.newOutputStream(newFile.toPath());

                // Copia el archivo seleccionado al nuevo archivo
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                // Cierra los streams
                in.close();
                out.close();

                Toast.makeText(this, "Archivo añadido con éxito.", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this, "Error al añadir el archivo.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Método para obtener el nombre del archivo de un Uri
    @SuppressLint("Range")
    private String getFileName(@NonNull Uri uri) {
        String result = null;
        if (Objects.equals(uri.getScheme(), "content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                assert cursor != null;
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            assert result != null;
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}