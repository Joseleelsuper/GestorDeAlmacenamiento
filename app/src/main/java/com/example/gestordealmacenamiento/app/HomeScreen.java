package com.example.gestordealmacenamiento.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestordealmacenamiento.R;
import com.example.gestordealmacenamiento.session.LoginScreen;
import com.example.gestordealmacenamiento.util.FileAdapter;
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
    private static final LinkedList<File> recentDirectories = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        // Obtener el nombre de usuario.
        TextView usernameTextView = findViewById(R.id.usernameTextView);
        usernameTextView.setText(UserData.currentInstance.getUsername());

        displayRecentDirectories();
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

    public static void setRecentDirectory(File directory) {
        // Añade el directorio a la lista
        recentDirectories.addFirst(directory);

        // Si la lista tiene más de 2 elementos, elimina el más antiguo
        if (recentDirectories.size() > 2) {
            recentDirectories.removeLast();
        }
    }

    private void displayRecentDirectories() {
        // Crea un adaptador para la lista
        FileAdapter adapter = new FileAdapter(this, recentDirectories);

        // Asigna el adaptador a la lista
        ListView recentDirectoriesListView = findViewById(R.id.home_listView);
        recentDirectoriesListView.setAdapter(adapter);
    }

    public void openFolder(File folder) {
        Intent intent = new Intent(this, FilesScreen.class);
        intent.putExtra("directoryPath", folder.getPath());
        startActivity(intent);
    }
}
