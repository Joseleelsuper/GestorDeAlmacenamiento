package com.example.gestordealmacenamiento.session;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestordealmacenamiento.R;
import com.example.gestordealmacenamiento.app.HomeScreen;
import com.example.gestordealmacenamiento.util.FinalVariables;
import com.example.gestordealmacenamiento.util.ValidateEmail;
import com.example.gestordealmacenamiento.util.UserData;

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
        if (ValidateEmail.isNotValidEmail(email)) {
            Toast.makeText(this, getString(R.string.invalid_email), Toast.LENGTH_SHORT).show();
            return;
        }

        String password = ((EditText) findViewById(R.id.login_password)).getText().toString();

        // Obtener la carpeta "Users".
        File appDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), getString(R.string.app_name));
        File usersDirectory = new File(appDirectory, FinalVariables.getUserDirectory());
        // Crear el archivo del usuario dentro de la carpeta "Users".
        File file = new File(usersDirectory, email.split("@")[0] + ".txt");

        // Comprobar si el usuario existe (Es decir, si ya hay un archivo con el mismo nombre de usuario).
        if (!file.exists()) {
            Toast.makeText(this, getString(R.string.invalid_user), Toast.LENGTH_SHORT).show();
            return;
        }

        // Leer el archivo del usuario y comprobar si la contraseña es correcta.
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8));
            String storedUsername = null;
            String storedEmail = null;
            String storedPassword = null;
            String storedAccountType = null;
            String storedExpirationDate = null;

            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                switch (lineNumber) {
                    case 1:
                        storedUsername = line;
                        break;
                    case 2:
                        storedEmail = line;
                        break;
                    case 3:
                        storedPassword = line;
                        break;
                    case 4:
                        storedAccountType = line;
                        break;
                    case 5:
                        storedExpirationDate = line;
                        break;
                    default:
                        break;
                }
                lineNumber++;
            }
            reader.close();

            if (storedEmail == null || storedPassword == null) {
                Toast.makeText(this, getString(R.string.error_readFile), Toast.LENGTH_SHORT).show();
                return;
            }

            if (!email.equals(storedEmail) || !password.equals(storedPassword)) {
                Toast.makeText(this, getString(R.string.error_emailOrPasswordIncorrect), Toast.LENGTH_SHORT).show();
                return;
            }

            // Create a new instance of UserData and set its data
            // Store the instance in the static variable
            UserData.currentInstance = new UserData(storedUsername, storedEmail, storedPassword, storedAccountType, storedExpirationDate);

        } catch (IOException e) {
            Toast.makeText(this, getString(R.string.error_readFile), Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
        Toast.makeText(this, getString(R.string.good_login), Toast.LENGTH_SHORT).show();
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
