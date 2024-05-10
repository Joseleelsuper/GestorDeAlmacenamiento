package com.example.gestordealmacenamiento.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestordealmacenamiento.R;

public class PermissionScreen extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission_screen);
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

    /**
     * Método que cambia a la pantalla de archivos.
     *
     * @param view Vista actual.
     */
    public void goFiles(View view) {
        Intent intent = new Intent(this, FilesScreen.class);
        startActivity(intent);
    }

    public void goPermissions(View view) {
        Toast.makeText(this, getString(R.string.alreadyinpermissionscreen), Toast.LENGTH_SHORT).show();
    }

    public void updatePermissions(View view) {
        Toast.makeText(this, getString(R.string.good_permUpdated), Toast.LENGTH_SHORT).show();
    }
}
