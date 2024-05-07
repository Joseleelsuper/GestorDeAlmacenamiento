package com.example.gestordealmacenamiento.util;

/**
 * Clase que almacena los datos del usuario.
 *
 * @author <a href="mailto:jgc1031@alu.ubu.es">José Gallardo Caballero</a>
 * @version 1.0
 * @serial 17/03/2024
 */
public class UserData {
    /**
     * Instancia actual de los datos del usuario.
     */
    public static UserData currentInstance;
    /**
     * Nombre de usuario.
     */
    private final String username;
    /**
     * Correo electrónico.
     */
    private final String email;
    /**
     * Contraseña.
     */
    private final String password;
    /**
     * Tipo de cuenta.
     */
    private final String accountType;
    /**
     * Fecha de expiración.
     */
    private final String expirationDate;

    /**
     * Constructor de la clase UserData.
     *
     * @param username Nombre de usuario.
     * @param email Correo electrónico.
     * @param password Contraseña.
     * @param accountType Tipo de cuenta.
     * @param expirationDate Fecha de expiración.
     */
    public UserData(String username, String email, String password, String accountType, String expirationDate){
        this.username = username;
        this.email = email;
        this.password = password;
        this.accountType = accountType;
        this.expirationDate = expirationDate;
    }

    /**
     * Método que devuelve la instancia actual de los datos del usuario.
     *
     * @return Instancia actual de los datos del usuario.
     */
    public String getUsername(){
        return username;
    }

    /**
     * Método que devuelve el nombre de usuario.
     *
     * @return Nombre de usuario.
     */
    public String getEmail(){
        return email;
    }

    /**
     * Método que devuelve la contraseña.
     *
     * @return Contraseña.
     */
    public String getPassword(){
        return password;
    }

    /**
     * Método que devuelve el tipo de cuenta.
     *
     * @return Tipo de cuenta.
     */
    public String getAccountType(){
        return accountType;
    }

    /**
     * Método que devuelve la fecha de expiración.
     *
     * @return Fecha de expiración.
     */
    public String getExpirationDate(){
        return expirationDate;
    }
}
