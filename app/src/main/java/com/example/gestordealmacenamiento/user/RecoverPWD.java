package com.example.gestordealmacenamiento.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestordealmacenamiento.MainActivity;
import com.example.gestordealmacenamiento.R;

public class RecoverPWD extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recoverpwd);
    }

    public void recover(View view) {
        Intent intent = new Intent(this, User.class);
        startActivity(intent);
    }
}
