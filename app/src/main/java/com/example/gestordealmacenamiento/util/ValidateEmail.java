package com.example.gestordealmacenamiento.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que valida un email.
 *
 * @author <a href="mailto:jgc1031@alu.ubu.es">José Gallardo Caballero</a>
 * @version 1.0
 * @since 1.0
 * @serial 01/05/2024
 */
public class ValidateEmail {

    /**
     * Función que comprueba si un email es válido.
     *
     * @param email Email a comprobar.
     * @return true si el email es válido, false en caso contrario.
     */
    public static boolean isNotValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }
}
