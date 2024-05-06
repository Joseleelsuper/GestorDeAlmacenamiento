package com.example.gestordealmacenamiento.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gestordealmacenamiento.R;
import com.example.gestordealmacenamiento.util.FileAdapter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
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

    private File currentDirectory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_screen);

        // Inicializa el directorio actual al directorio "Files" de la aplicación
        File appDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), getString(R.string.app_name));
        currentDirectory = new File(appDirectory, "Files");

        // Llama al método para mostrar la lista de archivos
        displayFiles();
    }

    public void displayFiles() {
        // Comprueba si el directorio actual existe
        if (currentDirectory.exists()) {
            // Obtiene la lista de archivos en el directorio actual
            File[] files = currentDirectory.listFiles();

            // Crea una lista de archivos
            List<File> fileList = new ArrayList<>();
            if (files != null) {
                fileList.addAll(Arrays.asList(files));
            }

            // Crea un adaptador para la lista
            FileAdapter adapter = new FileAdapter(this, this, fileList);

            // Asigna el adaptador a la lista
            ListView filesListView = findViewById(R.id.files_listView);
            filesListView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No se encontró el directorio de archivos.", Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    private ArrayAdapter<String> getStringArrayAdapter(File filesDirectory) {
        File[] files = filesDirectory.listFiles();

        // Crea una lista de nombres de archivos
        List<String> fileNames = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                fileNames.add(file.getName());
            }
        }

        // Crea un adaptador para la lista
        return new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileNames);
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
        // Crea un diálogo de opciones
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Elige una opción")
                .setItems(new String[]{"Subir archivo", "Crear carpeta"}, (dialog, which) -> {
                    switch (which) {
                        case 0: // Subir archivo
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("*/*");
                            startActivityForResult(intent, 1);
                            break;
                        case 1: // Crear carpeta
                            createFolder();
                            break;
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri uri = data.getData();

            // Obtén el nombre del archivo seleccionado
            assert uri != null;
            String fileName = getFileName(uri);

            // Crea un nuevo archivo en el directorio actual con el nombre del archivo seleccionado
            File newFile = new File(currentDirectory, fileName);

            // Comprueba si el archivo ya existe
            if (newFile.exists()) {
                Toast.makeText(this, "El archivo ya existe.", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                // Crea un InputStream del archivo seleccionado
                InputStream in = getContentResolver().openInputStream(uri);

                // Crea un OutputStream del nuevo archivo
                OutputStream out = Files.newOutputStream(newFile.toPath());

                // Copia el archivo seleccionado al nuevo archivo
                byte[] buf = new byte[1024];
                int len;
                while (true) {
                    assert in != null;
                    if (!((len = in.read(buf)) > 0)) break;
                    out.write(buf, 0, len);
                }

                // Cierra los streams
                in.close();
                out.close();

                Toast.makeText(this, "Archivo añadido con éxito.", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this, "Error al añadir el archivo.", Toast.LENGTH_SHORT).show();
            } finally {
                // Actualiza la lista de archivos
                displayFiles();
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

    /**
     * Método que permite crear una carpeta.
     */
    private void createFolder() {
        // Crea un diálogo de entrada
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nombre de la carpeta");

        // Configura el campo de entrada
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Configura los botones
        builder.setPositiveButton("OK", (dialog, which) -> {
            String folderName = input.getText().toString();

            // Crea un nuevo directorio en el directorio "Files" de la aplicación con el nombre de la carpeta
            File appDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), getString(R.string.app_name));
            File filesDirectory = new File(appDirectory, "Files");
            File newFolder = new File(filesDirectory, folderName);
            boolean folderCreated = newFolder.mkdir();

            if (folderCreated) {
                Toast.makeText(this, "Carpeta creada con éxito.", Toast.LENGTH_SHORT).show();
                // Actualiza la lista de archivos
                displayFiles();
            } else {
                Toast.makeText(this, "Error al crear la carpeta.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    public void setCurrentDirectory(File newDirectory) {
        currentDirectory = newDirectory;
    }
}