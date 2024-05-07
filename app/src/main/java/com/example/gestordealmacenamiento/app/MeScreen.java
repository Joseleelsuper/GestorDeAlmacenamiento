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

/**
 * Clase que representa la pantalla de Me.
 *
 * @author <a href="mailto:jgc1031@alu.ubu.es">José Gallardo Caballero</a>
 * @version 1.0
 * @serial 17/03/2024
 */
public class MeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_screen);

        TextView usernameTextView = findViewById(R.id.me_text_accountType);
        usernameTextView.setText(UserData.currentInstance.getAccountType());

        TextView emailTextView = findViewById(R.id.me_text_expireDate);
        emailTextView.setText(UserData.currentInstance.getExpirationDate());
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
     * Método que cambia a la pantalla de archivos.
     *
     * @param view Vista actual.
     */
    public void goFiles(View view) {
        Intent intent = new Intent(this, FilesScreen.class);
        startActivity(intent);
    }

    /**
     * Método que cambia a la pantalla de Me.
     *
     * @param view Vista actual.
     */
    public void goMe(View view) {
        Toast.makeText(this, "Ya estás en la pantalla de Me", Toast.LENGTH_SHORT).show();
    }

    public void deleteAccount(View view) {
        File appDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), getString(R.string.app_name));
        File usersDirectory = new File(appDirectory, "Users");
        // Conseguir lo que hay antes del @ del email
        String email = UserData.currentInstance.getEmail();
        File fileName = new File(usersDirectory, email.split("@")[0] + ".txt");
        // Borrar el archivo
        if (fileName.exists()) {
            boolean deleted = fileName.delete();
            if (!deleted) {
                Toast.makeText(this, "Error al eliminar la cuenta", Toast.LENGTH_LONG).show();
                return;
            }
        } else {
            Toast.makeText(this, "El archivo no existe", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
        Toast.makeText(this, "Cuenta eliminada correctamente", Toast.LENGTH_SHORT).show();
    }
}
