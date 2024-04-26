package com.example.gestordealmacenamiento.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestordealmacenamiento.MainActivity;
import com.example.gestordealmacenamiento.R;

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
    public void register(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
