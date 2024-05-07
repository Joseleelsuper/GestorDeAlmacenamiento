package com.example.gestordealmacenamiento.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestordealmacenamiento.R;
import com.example.gestordealmacenamiento.session.LoginScreen;
import com.example.gestordealmacenamiento.util.UserData;

import java.io.File;
import java.util.LinkedList;

/**
 * Clase que representa la pantalla de inicio.
 *
 * @author <a href="mailto:jgc1031@alu.ubu.es">José Gallardo Caballero</a>
 * @version 1.0
 * @serial 07/05/2024
 */
public class HomeScreen extends AppCompatActivity {

    /**
     * Lista de directorios recientes.
     */
    private final LinkedList<File> recentDirectories = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        // Obtener el nombre de usuario.
        TextView usernameTextView = findViewById(R.id.usernameTextView);
        usernameTextView.setText(UserData.currentInstance.getUsername());

        File appDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), getString(R.string.app_name));
        File filesDirectory = new File(appDirectory, "Files");
        File[] files = filesDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    recentDirectories.add(file);
                    if (recentDirectories.size() == 2) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * Método que cambia a la pantalla de Me.
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

    /**
     * Método que cambia a la pantalla de inicio.
     *
     * @param view Vista actual.
     */
    public void goHome(View view) {
        Toast.makeText(this, "Ya estás en la pantalla de inicio", Toast.LENGTH_SHORT).show();
    }

    public void logout(View view) {
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
        Toast.makeText(this, "Sesión cerrada correctamente.", Toast.LENGTH_SHORT).show();
    }
}
