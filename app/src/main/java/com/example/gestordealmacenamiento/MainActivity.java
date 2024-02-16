package com.example.gestordealmacenamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Método para el botón de abrir las settings
    public void openSettings(android.view.View view) {
        android.content.Intent intent = new android.content.Intent(android.provider.Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
        startActivity(intent);
    }

    // Método para subir documentos a la nube.
    // debe aprir la carpeta descargar, seleccionar el archivo y subirlo.
    public void uploadFile(android.view.View view) {
        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, 7);
    }
}