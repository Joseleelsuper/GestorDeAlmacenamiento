package com.example.gestordealmacenamiento.session;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestordealmacenamiento.app.FilesScreen;
import com.example.gestordealmacenamiento.R;
import com.example.gestordealmacenamiento.app.HomeScreen;
import com.example.gestordealmacenamiento.app.MeScreen;
import com.example.gestordealmacenamiento.util.validateEmail;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Clase que permite a un usuario iniciar sesión.
 *
 * @author <a href="mailto:jgc1031@alu.ubu.es">José Gallardo Caballero</a>
 * @version 1.0
 * @since 1.0
 * @serial 15/04/2024
 */
public class LoginScreen extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
    }

    /**
     * Método que inicia sesión.
     */
    public void login(View view) {

        EditText emailEditText = findViewById(R.id.login_email);
        String email = emailEditText.getText().toString();

        // Comprobar que el email es válido.
        if (!validateEmail.isValidEmail(email)) {
            Toast.makeText(this, "Por favor, introduce un correo electrónico válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        String password = ((EditText) findViewById(R.id.login_password)).getText().toString();

        // Obtener la carpeta "Users".
        File appDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), getString(R.string.app_name));
        File usersDirectory = new File(appDirectory, "Users");
        // Crear el archivo del usuario dentro de la carpeta "Users".
        File file = new File(usersDirectory, email.split("@")[0] + ".txt");

        // Comprobar si el usuario existe (Es decir, si ya hay un archivo con el mismo nombre de usuario).
        if (!file.exists()) {
            Toast.makeText(this, "El usuario no existe. Por favor, regístrese.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Leer el archivo del usuario y comprobar si la contraseña es correcta.
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8));
            String storedEmail = null;
            String storedPassword = null;
            int lineNumber = 1;

            String line;
            while ((line = reader.readLine()) != null) {
                if (lineNumber == 2) {
                    storedEmail = line;
                } else if (lineNumber == 3) {
                    storedPassword = line;
                }
                lineNumber++;
            }
            reader.close();

            if (storedEmail == null || storedPassword == null) {
                Toast.makeText(this, "Error al leer el archivo.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!email.equals(storedEmail) || !password.equals(storedPassword)) {
                Toast.makeText(this, "Correo electrónico o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (IOException e) {
            Toast.makeText(this, "Error al intentar leer el archivo.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, MeScreen.class);
        startActivity(intent);
        Toast.makeText(this, "Inicio de sesión correcto.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Método que lleva al usuario a la pantalla de registro.
     */
    public void registerText(View view) {
        Intent intent = new Intent(this, RegisterScreen.class);
        startActivity(intent);
    }

    /**
     * Método que recupera la contraseña.
     */
    public void recoverPWD(View view) {
        Intent intent = new Intent(this, RecoverPWDScreen.class);
        startActivity(intent);
    }
}
