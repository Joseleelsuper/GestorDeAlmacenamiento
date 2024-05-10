package com.example.gestordealmacenamiento.app;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestordealmacenamiento.R;

public class PermissionScreen extends AppCompatActivity {

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
}
