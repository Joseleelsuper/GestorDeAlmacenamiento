package com.example.gestordealmacenamiento.presentation;

import com.bumptech.glide.Glide;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestordealmacenamiento.R;
import com.example.gestordealmacenamiento.user.User;

public class Presentation extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presentation);

        // Hacer que el loading.gif gire indefinidamente
        ImageView loading = findViewById(R.id.loading);
        Glide.with(this).load(R.drawable.loading).into(loading);
        loading.animate().rotationBy(360).setDuration(1000).start();

        new Handler().postDelayed(() -> {
            Intent mainIntent = new Intent(Presentation.this, User.class);
            Presentation.this.startActivity(mainIntent);
            Presentation.this.finish();
        }, SPLASH_DISPLAY_LENGTH);
    }
}