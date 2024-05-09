package com.example.gestordealmacenamiento.session;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestordealmacenamiento.R;
import com.example.gestordealmacenamiento.util.FinalVariables;
import com.example.gestordealmacenamiento.util.ValidateEmail;

import java.io.File;

/**
 * Clase que permite recuperar la contraseña de un usuario.
 *
 * @author <a href="mailto:jgc1031@alu.ubu.es">José Gallardo Caballero</a>
 * @version 1.0
 * @serial 17/03/2024
 */
public class RecoverPWDScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recoverpwd_screen);
    }

    /**
     * Método que permite recuperar la contraseña de un usuario.
     *
     * @param view Vista actual.
     */
    public void recover(View view) {

        EditText emailEditText = findViewById(R.id.recoverpwd_Email);
        String email = emailEditText.getText().toString();

        // Comprobar que se han aceptado los envíos al correo
        if (!((CheckBox) findViewById(R.id.recoverpwd_mailReport)).isChecked()) {
            Toast.makeText(this, getString(R.string.accept_email_messages), Toast.LENGTH_SHORT).show();
            return;
        }

        // Comprobar que el email es válido.
        if (ValidateEmail.isNotValidEmail(email)) {
            Toast.makeText(this, getString(R.string.invalid_email), Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener la carpeta "Users".
        File appDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), getString(R.string.app_name));
        File usersDirectory = new File(appDirectory, FinalVariables.getUserDirectory());
        // Crear el archivo del usuario dentro de la carpeta "Users".
        File file = new File(usersDirectory, email.split("@")[0] + ".txt");

        // Comprobar si el usuario existe (Es decir, si ya hay un archivo con el mismo nombre de usuario).
        if (!file.exists()) {
            Toast.makeText(this, getString(R.string.no_email_in_db), Toast.LENGTH_SHORT).show();
            return;
        }

        // Enviar un correo con la contraseña.
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
        Toast.makeText(this, getString(R.string.good_recoverPWD), Toast.LENGTH_LONG).show();
    }
}
