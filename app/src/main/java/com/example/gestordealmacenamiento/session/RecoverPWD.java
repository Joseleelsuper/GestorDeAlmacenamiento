package com.example.gestordealmacenamiento.session;

import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestordealmacenamiento.R;
import com.example.gestordealmacenamiento.util.validateEmail;

import java.io.File;

public class RecoverPWD extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recoverpwd);
    }

    public void recover(View view) {

        EditText emailEditText = findViewById(R.id.recoverpwd_Email);
        String email = emailEditText.getText().toString();

        // Comprobar que se han aceptado los envíos al correo
        if (!((CheckBox) findViewById(R.id.recoverpwd_mailReport)).isChecked()) {
            Toast.makeText(this, "Por favor, acepta los envíos de mensajes al correo.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Comprobar que el email es válido.
        if (!validateEmail.isValidEmail(email)) {
            Toast.makeText(this, "Por favor, introduce un Email válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener la carpeta "Users".
        File appDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), getString(R.string.app_name));
        File usersDirectory = new File(appDirectory, "Users");
        // Crear el archivo del usuario dentro de la carpeta "Users".
        File file = new File(usersDirectory, email.split("@")[0] + ".txt");

        // Comprobar si el usuario existe (Es decir, si ya hay un archivo con el mismo nombre de usuario).
        if (!file.exists()) {
            Toast.makeText(this, "Este correo electrónico no está en la base de datos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Enviar un correo con la contraseña.
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        Toast.makeText(this, "Operación realizada. Mira tu correo.", Toast.LENGTH_LONG).show();
    }
}
