package com.example.gestordealmacenamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtener el nombre de la aplicación desde los recursos de cadena
        String appName = getString(R.string.app_name);

        // Crear una carpeta en Documents con el nombre de la aplicación
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), appName);
        if (!directory.exists()) {
            directory.mkdirs(); // Crea los directorios necesarios si no existen
        }
    }

    // Método para el botón de abrir las settings
    public void openSettings(android.view.View view) {
        android.content.Intent intent = new android.content.Intent(android.provider.Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
        startActivity(intent);
    }

    // Método para subir documentos a la nube (en este caso, la carpeta local creada).
    // debe aprir la carpeta descargar, seleccionar el archivo y subirlo.
    public void uploadFile(android.view.View view) {
        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, 7);
    }
}