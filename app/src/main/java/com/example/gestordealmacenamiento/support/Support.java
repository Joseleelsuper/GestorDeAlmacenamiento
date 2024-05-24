package com.example.gestordealmacenamiento.support;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gestordealmacenamiento.R;
import com.example.gestordealmacenamiento.app.MeScreen;

public class Support extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.support);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void BackMore (View view) {
        Intent backmore = new Intent(this, MeScreen.class);
        startActivity(backmore);
    }
    public void ACERCADE1 (View view) {
        Intent acercade1 = new Intent(this, Acercade1.class);
        startActivity(acercade1);
    }
    public void ACERCADE2 (View view) {
        Intent acercade2 = new Intent(this, Acercade2.class);
        startActivity(acercade2);
    }
    public void AYUDA1 (View view) {
        Intent ayuda1 = new Intent(this, Ayuda1.class);
        startActivity(ayuda1);
    }
    public void AYUDA2 (View view) {
        Intent ayuda2 = new Intent(this, Ayuda2.class);
        startActivity(ayuda2);
    }
    public void CONSEJO1 (View view) {
        Intent consejo1 = new Intent(this, Consejo1.class);
        startActivity(consejo1);
    }
    public void CONSEJO2 (View view) {
        Intent consejo2 = new Intent(this, Consejo2.class);
        startActivity(consejo2);
    }
    public void PREGUNTA1 (View view) {
        Intent pregunta1 = new Intent(this, Pregunta1.class);
        startActivity(pregunta1);
    }
    public void PREGUNTA2 (View view) {
        Intent pregunta2 = new Intent(this, Pregunta2.class);
        startActivity(pregunta2);
    }
    public void SOMOS1 (View view) {
        Intent somos1 = new Intent(this, Somos1.class);
        startActivity(somos1);
    }
    public void SOMOS2 (View view) {
        Intent somos2 = new Intent(this, Somos2.class);
        startActivity(somos2);
    }

}