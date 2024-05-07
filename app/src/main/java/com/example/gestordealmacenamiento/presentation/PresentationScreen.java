package com.example.gestordealmacenamiento.presentation;

import com.bumptech.glide.Glide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestordealmacenamiento.R;
import com.example.gestordealmacenamiento.session.LoginScreen;
import com.example.gestordealmacenamiento.storage.Storage;

/**
 * Clase que representa la pantalla de presentación de la aplicación.
 *
 * @author <a href="mailto:jgc1031@alu.ubu.es">José Gallardo Caballero</a>
 * @version 1.0
 * @serial 17/03/2024
 */
public class PresentationScreen extends AppCompatActivity {

    /**
     * Duración de la presentación.
     */
    private static final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presentation_screen);

        // Hacer que el loading.gif gire indefinidamente
        ImageView loading = findViewById(R.id.imageView);
        Glide.with(this).load(R.drawable.loading).into(loading);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent mainIntent = new Intent(PresentationScreen.this, LoginScreen.class);
            PresentationScreen.this.startActivity(mainIntent);
            PresentationScreen.this.finish();
        }, SPLASH_DISPLAY_LENGTH);

        Storage storage = new Storage();
        try {
            storage.createAppFolder(this);
            storage.createUsersFolder(this);
            storage.createFilesFolder(this);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al crear los directorios necesarios", e);
        }
    }
}