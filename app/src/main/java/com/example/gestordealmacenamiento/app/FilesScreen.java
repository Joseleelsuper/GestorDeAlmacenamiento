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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gestordealmacenamiento.R;
import com.example.gestordealmacenamiento.util.FileAdapter;
import com.example.gestordealmacenamiento.util.FinalVariables;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * Clase principal de la aplicación.
 *
 * @author <a href="mailto:jgc1031@alu.ubu.es">José Gallardo Caballero</a>
 * @version 1.0
 * @serial 17/03/2024
 */
public class FilesScreen extends AppCompatActivity {

    /**
     * Flecha para volver atrás.
     */
    private ImageView fileArrowBack;
    /**
     * Directorio actual.
     */
    private File currentDirectory;
    /**
     * Pila de historial de directorios.
     */
    private Stack<File> directoryHistory;

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

    /**
     * Método que cambia a la pantalla de archivos.
     *
     * @param view Vista actual.
     */
    public void goFiles(View view) {
        Toast.makeText(this, getString(R.string.alreadyinfilescreen), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_screen);

        // Inicializa el directorio actual al directorio "Files" de la aplicación
        File appDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), getString(R.string.app_name));
        currentDirectory = new File(appDirectory, FinalVariables.getFileDirectory());

        // Llama al método para mostrar la lista de archivos
        configureSpinnerSort();

        directoryHistory = new Stack<>();

        fileArrowBack = findViewById(R.id.file_arrow_back);
        fileArrowBack.setVisibility(View.INVISIBLE);
        fileArrowBack.setOnClickListener(v -> goBackToParentDirectory());

        String directoryPath = getIntent().getStringExtra(FinalVariables.getFileDirectory());
        if (directoryPath != null) {
            File directory = new File(directoryPath);
            setCurrentDirectory(directory);
            displayFiles(0);
        }
    }

    /**
     * Método que configura el Spinner de ordenación. Permitirá ordenar los archivos por nombre y tamaño.
     */
    private void configureSpinnerSort() {
        Spinner spinnerSort = findViewById(R.id.spinner_sort);
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Ordena y muestra los archivos según la opción seleccionada
                displayFiles(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });
    }

    /**
     * Método que muestra la lista de archivos.
     *
     * @param sortOption Opción de ordenación.
     */
    public void displayFiles(int sortOption) {
        // Comprueba si el directorio actual existe
        if (currentDirectory.exists()) {
            // Obtiene la lista de archivos en el directorio actual
            File[] files = currentDirectory.listFiles();

            // Ordena los archivos
            sortFiles(files, sortOption);

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
            Toast.makeText(this, getString(R.string.error_noFileDirectory), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Método que ordena los archivos.
     *
     * @param files      Lista de archivos.
     * @param sortOption Opción de ordenación.
     */
    private void sortFiles(File[] files, int sortOption) {
        if (files != null) {
            Comparator<File> comparator;
            switch (sortOption) {
                case 0: // Ordenar A-Z
                    comparator = (f1, f2) -> {
                        if (f1.isDirectory() && !f2.isDirectory()) {
                            return -1;
                        } else if (!f1.isDirectory() && f2.isDirectory()) {
                            return 1;
                        } else {
                            return f1.getName().compareToIgnoreCase(f2.getName());
                        }
                    };
                    break;
                case 1: // Ordenar Z-A
                    comparator = (f1, f2) -> {
                        if (f1.isDirectory() && !f2.isDirectory()) {
                            return 1;
                        } else if (!f1.isDirectory() && f2.isDirectory()) {
                            return -1;
                        } else {
                            return f2.getName().compareToIgnoreCase(f1.getName());
                        }
                    };
                    break;
                case 2: // Ordenar de mayor a menor tamaño
                    comparator = (f1, f2) -> Long.compare(f2.length(), f1.length());
                    break;
                case 3: // Ordenar de menor a mayor tamaño
                    comparator = Comparator.comparingLong(File::length);
                    break;
                default:
                    throw new IllegalStateException(getString(R.string.unexpectedValue) + sortOption);
            }
            Arrays.sort(files, comparator);
        }
    }

    /**
     * Método que permite añadir un archivo.
     *
     * @param view Vista actual.
     */
    public void addFile(View view) {
        // Crea un diálogo de opciones
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.chooseOption))
                .setItems(new String[]{getString(R.string.uploadFile), getString(R.string.createFolder)}, (dialog, which) -> {
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
                Toast.makeText(this, getString(R.string.error_fileExits), Toast.LENGTH_SHORT).show();
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

                Toast.makeText(this, getString(R.string.good_addedFile), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this, getString(R.string.error_addedFile), Toast.LENGTH_SHORT).show();
            } finally {
                // Actualiza la lista de archivos
                displayFiles(0);
            }
        }
    }

    // Método para obtener el nombre del archivo de un Uri
    @SuppressLint("Range")
    private String getFileName(@NonNull Uri uri) {
        String result = null;
        if (Objects.equals(uri.getScheme(), FinalVariables.getContent())) {
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
        builder.setTitle(getString(R.string.nameFolder));

        // Configura el campo de entrada
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Configura los botones
        builder.setPositiveButton(getString(R.string.ok), (dialog, which) -> {
            String folderName = input.getText().toString();

            File newFolder = new File(currentDirectory, folderName);
            boolean folderCreated = newFolder.mkdir();

            if (folderCreated) {
                Toast.makeText(this, getString(R.string.good_folderCreated), Toast.LENGTH_SHORT).show();
                // Actualiza la lista de archivos
                displayFiles(0);
            } else {
                Toast.makeText(this, getString(R.string.error_folderCreated), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.cancel());

        builder.show();
    }

    /**
     * Método que cambia al directorio seleccionado.
     *
     * @param newDirectory Directorio seleccionado.
     */
    public void setCurrentDirectory(File newDirectory) {
        if (currentDirectory != null) {
            directoryHistory.push(currentDirectory);
        }
        currentDirectory = newDirectory;

        File appDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), getString(R.string.app_name));
        File rootDirectory = new File(appDirectory, FinalVariables.getFileDirectory());

        if (currentDirectory.equals(rootDirectory)) {
            fileArrowBack.setVisibility(View.INVISIBLE);
        } else {
            fileArrowBack.setVisibility(View.VISIBLE);
        }
    }

    private void goBackToParentDirectory() {
        if (!directoryHistory.isEmpty()) {
            currentDirectory = directoryHistory.pop();

            File appDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), getString(R.string.app_name));
            File rootDirectory = new File(appDirectory, FinalVariables.getFileDirectory());

            if (currentDirectory.equals(rootDirectory)) {
                fileArrowBack.setVisibility(View.INVISIBLE);
            } else {
                fileArrowBack.setVisibility(View.VISIBLE);
            }

            displayFiles(0);
        }
    }

    @Override
    public void onBackPressed() {
        // Obtén el directorio "Files" de la aplicación
        File appDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), getString(R.string.app_name));
        File rootDirectory = new File(appDirectory, FinalVariables.getFileDirectory());

        // Comprueba si el directorio actual es el directorio raíz
        if (currentDirectory.equals(rootDirectory)) {
            super.onBackPressed();
        } else if (!directoryHistory.isEmpty()) {
            File previousDirectory = directoryHistory.pop();
            setCurrentDirectory(previousDirectory);
            displayFiles(0);
        } else {
            super.onBackPressed();
        }
    }
}