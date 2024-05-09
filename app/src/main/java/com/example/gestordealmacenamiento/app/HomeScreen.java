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
import com.example.gestordealmacenamiento.util.FinalVariables;
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
        Toast.makeText(this, getString(R.string.alreadyinhome), Toast.LENGTH_SHORT).show();
    }

    /**
     * Método que cierra sesión.
     *
     * @param view Vista actual.
     */
    public void logout(View view) {
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
        Toast.makeText(this, getString(R.string.good_logout), Toast.LENGTH_SHORT).show();
    }

    /**
     * Método que añade un directorio a la lista de directorios recientes.
     *
     * @param directory Directorio a añadir.
     */
    /**
     * Método que añade un directorio a la lista de directorios recientes.
     *
     * @param directory Directorio a añadir.
     */
    public static void setRecentDirectory(File directory) {
        // Si el directorio ya está en la lista, lo elimina
        recentDirectories.remove(directory);

        // Añade el directorio al principio de la lista
        recentDirectories.addFirst(directory);

        // Si la lista tiene más de 2 elementos, elimina el más antiguo
        if (recentDirectories.size() > 2) {
            recentDirectories.removeLast();
        }
    }

    /**
     * Método que muestra los directorios recientes.
     */
    private void displayRecentDirectories() {
        // Crea un adaptador para la lista
        FileAdapter adapter = new FileAdapter(this, recentDirectories);

        // Asigna el adaptador a la lista
        ListView recentDirectoriesListView = findViewById(R.id.home_listView);
        recentDirectoriesListView.setAdapter(adapter);
    }

    /**
     * Método que abre la carpeta seleccionada.
     *
     * @param folder Carpeta a abrir.
     */
    public void openFolder(File folder) {
        Intent intent = new Intent(this, FilesScreen.class);
        intent.putExtra(FinalVariables.getDirectoryPath(), folder.getPath());
        startActivity(intent);
    }
}
