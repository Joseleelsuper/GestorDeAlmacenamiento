package com.example.gestordealmacenamiento.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestordealmacenamiento.MainActivity;
import com.example.gestordealmacenamiento.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
    }

    /**
     * Método que lleva al usuario a la pantalla de inicio de sesión.
     */
    public void loginText(View view) {
        Intent intent = new Intent(this, User.class);
        startActivity(intent);
    }

    /**
     * Método que registra al usuario.
     */
    public void register(View view) throws IOException {
        EditText emailEditText = findViewById(R.id.register_email);
        String email = emailEditText.getText().toString();

        if (isValidEmail(email)) {
            String userName = ((EditText) findViewById(R.id.register_username)).getText().toString();
            String password = ((EditText) findViewById(R.id.register_password)).getText().toString();

            // Obtener la carpeta "Users"
            File appDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), getString(R.string.app_name));
            File usersDirectory = new File(appDirectory, "Users");

            // Crear el archivo del usuario dentro de la carpeta "Users"
            File file = new File(usersDirectory, email.split("@")[0] + ".txt");
            if (file.exists()) {
                Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                return;
            } else {
                // Guardar los datos del usuario en el archivo txt
                try (FileOutputStream fos = new FileOutputStream(file);
                     OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
                    osw.write(userName + "\n");
                    osw.write(email + "\n");
                    osw.write(password + "\n");
                } catch (IOException e) {
                    Toast.makeText(this, "Error al guardar el usuario", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        } else {
            // Mostrar un mensaje de error al usuario
            Toast.makeText(this, "Por favor, introduce un correo electrónico válido", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
