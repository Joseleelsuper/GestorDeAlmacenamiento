package com.example.gestordealmacenamiento.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestordealmacenamiento.R;

public class HomeScreen extends AppCompatActivity {

    public static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        // Obtener el nombre de usuario.
        TextView usernameTextView = findViewById(R.id.usernameTextView);
        usernameTextView.setText(username);
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

    // Si le da click al boton de home, mandar un toast diciendo que ya esta en la pantalla de inicio
    public void goHome(View view) {
        Toast.makeText(this, "Ya estás en la pantalla de inicio", Toast.LENGTH_SHORT).show();
    }
}
