package com.example.gestordealmacenamiento.app;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestordealmacenamiento.R;
import com.example.gestordealmacenamiento.session.LoginScreen;
import com.example.gestordealmacenamiento.util.FinalVariables;
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
        Toast.makeText(this, getString(R.string.alreadyinme), Toast.LENGTH_SHORT).show();
    }

    public void goPermissions(View view) {
        Intent intent = new Intent(this, PermissionScreen.class);
        startActivity(intent);
    }

    /**
     * Método que elimina la cuenta.
     *
     * @param view Vista actual.
     */
    public void deleteAccount(View view) {
        // Crea un diálogo de entrada
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.confirmDeleteAccount));

        // Configura los campos de entrada
        final EditText emailInput = new EditText(this);
        emailInput.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailInput.setHint(getString(R.string.email));

        final EditText passwordInput = new EditText(this);
        passwordInput.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordInput.setHint(getString(R.string.password));

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(emailInput);
        layout.addView(passwordInput);
        builder.setView(layout);

        // Configura los botones
        builder.setPositiveButton(getString(R.string.delete), (dialog, which) -> {
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();

            // Verifica los datos introducidos
            if (email.equals(UserData.currentInstance.getEmail()) && password.equals(UserData.currentInstance.getPassword())) {
                // Los datos son correctos, procede a eliminar la cuenta
                File appDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), getString(R.string.app_name));
                File usersDirectory = new File(appDirectory, FinalVariables.getUserDirectory());
                File fileName = new File(usersDirectory, email.split("@")[0] + ".txt");
                if (fileName.exists()) {
                    boolean deleted = fileName.delete();
                    if (!deleted) {
                        Toast.makeText(this, getString(R.string.error_DeleteAccount), Toast.LENGTH_LONG).show();
                        return;
                    }
                } else {
                    Toast.makeText(this, getString(R.string.error_emailOrPasswordIncorrect), Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(this, LoginScreen.class);
                startActivity(intent);
                Toast.makeText(this, getString(R.string.good_accountDeleted), Toast.LENGTH_SHORT).show();
            } else {
                // Los datos son incorrectos, muestra un mensaje de error
                Toast.makeText(this, getString(R.string.error_emailOrPasswordIncorrect), Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
