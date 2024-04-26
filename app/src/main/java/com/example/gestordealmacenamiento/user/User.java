package com.example.gestordealmacenamiento.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestordealmacenamiento.MainActivity;
import com.example.gestordealmacenamiento.R;

import java.io.File;
import java.util.List;

/**
 * Clase que representa a un usuario.
 *
 * @author <a href="mailto:a@a.com">a</a>
 * @version 1.0
 * @since 1.0
 * @serial 2024/04/15
 */
public class User extends AppCompatActivity {

    /**
     * Nombre de usuario.
     */
    private String userName;
    /**
     * Correo electrónico.
     */
    private String email;
    /**
     * Contraseña.
     */
    private String password;
    /**
     * Lista de archivos del usuario.
     */
    private List<File> files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public User() {}

    /**
     * Constructor de la clase.
     *
     * @param userName nombre de usuario
     * @param email correo electrónico
     * @param password contraseña
     */
    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    /**
     * Método que lleva al usuario a la pantalla de registro.
     */
    public void registerText(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    /**
     * Método que inicia sesión.
     */
    public void login(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Método que recupera la contraseña.
     */
    public void recoverPWD(View view) {
        Intent intent = new Intent(this, RecoverPWD.class);
        startActivity(intent);
    }

    /**
     * Método que sube un archivo.
     *
     * @param file Archivo a subir.
     */
    public void uploadFile(File file) {
        // TODO implement here
    }

    /**
     * Método que descarga un archivo.
     *
     * @param file Archivo a descargar.
     */
    public void downloadFile(File file) {
        // TODO implement here
    }

    /**
     * Método que elimina un archivo.
     *
     * @param file Archivo a eliminar.
     */
    public void deleteFile(File file) {
        // TODO implement here
    }

    /**
     * Método que comparte un archivo.
     *
     * @param file Archivo a compartir.
     * @param targetuUser Usuario al que se le comparte el archivo.
     */
    public void shareFile(File file, User targetuUser) {
        // TODO implement here
    }
}
