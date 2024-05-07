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
import com.example.gestordealmacenamiento.util.ValidateEmail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Clase que permite a un usuario registrarse.
 *
 * @author <a href="mailto:jgc1031@alu.ubu.es">José Gallardo Caballero</a>
 * @version 1.0
 * @since 1.0
 * @serial 15/04/2024
 */
public class RegisterScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);
    }

    /**
     * Método que registra al usuario.
     *
     * @param view Vista actual.
     */
    public void register(View view){

        // Comprobat que se ha marcado la casilla de aceptar términos y condiciones.
        if (!((CheckBox) findViewById(R.id.register_termsOfUse)).isChecked()) {
            Toast.makeText(this, "Por favor, acepta los términos de uso.", Toast.LENGTH_SHORT).show();
            return;
        }

        EditText emailEditText = findViewById(R.id.register_email);
        String email = emailEditText.getText().toString();

        // Comprobar que el email es válido.
        if (!ValidateEmail.isValidEmail(email)) {
            Toast.makeText(this, "Por favor, introduce un correo electrónico válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        String userName = ((EditText) findViewById(R.id.register_username)).getText().toString();
        String password = ((EditText) findViewById(R.id.register_password)).getText().toString();

        // Dependiendo del mail con el que se registre, se le asignará un tipo de cuenta.
        Set<String> personalEmailDomains = new HashSet<>(Arrays.asList("gmail.com", "hotmail.com", "yahoo.com"));

        String emailDomain = email.split("@")[1];
        String accountType;

        if (personalEmailDomains.contains(emailDomain)) {
            accountType = "Personal";
        } else {
            accountType = "Business";
        }

        // Obtener la fecha y hora actual
        Calendar calendar = Calendar.getInstance();
        // Agregar 5 años a la fecha actual
        calendar.add(Calendar.YEAR, 5);
        // Obtener el formateador de fecha predeterminado para el idioma del dispositivo
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        // Formatear la fecha
        String expirationDate = dateFormat.format(calendar.getTime());

        // Obtener la carpeta "Users".
        File appDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), getString(R.string.app_name));
        File usersDirectory = new File(appDirectory, "Users");
        // Crear el archivo del usuario dentro de la carpeta "Users".
        File file = new File(usersDirectory, email.split("@")[0] + ".txt");

        // Comprobar si el usuario ya existe (Es decir, si ya hay un archivo con el mismo nombre de usuario).
        if (file.exists()) {
            Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
            return;
        }

        // Guardar los datos del usuario en el archivo txt.
        try (FileOutputStream fos = new FileOutputStream(file);
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
            osw.write(userName + "\n");
            osw.write(email + "\n");
            osw.write(password + "\n");
            osw.write(accountType + "\n");
            osw.write(expirationDate);
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Error al encontrar el archivo. No pudo ser creado.", Toast.LENGTH_SHORT).show();
            return;
        } catch (IOException e) {
            Toast.makeText(this, "Error al intentar escribir en el archivo.", Toast.LENGTH_SHORT).show();
            return;
        }

        loginText(view);
        Toast.makeText(this, "Usuario registrado correctamente.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Método que cambia a la pantalla de inicio de sesión.
     *
     * @param view Vista actual.
     */
    public void loginText(View view) {
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
    }
}
