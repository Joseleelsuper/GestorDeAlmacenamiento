package com.example.gestordealmacenamiento.storage;

import com.example.gestordealmacenamiento.user.User;
import java.util.List;

/**
 * Clase que representa un almacenamiento de un dispositivo.
 *
 * @author <a href="mailto:a@a.com">a</a>
 * @version 1.0
 * @since 1.0
 * @serial 2024/04/15
 */
public class Storage {

    /**
     * Espacio total del almacenamiento.
     */
    private double totalSpace;
    /**
     * Espacio disponible del almacenamiento.
     */
    private double availableSpace;
    /**
     * Lista de usuarios que tienen acceso al almacenamiento.
     */
    private List<User> users;

    /**
     * Constructor de la clase.
     *
     * @param totalSpace Espacio total del almacenamiento.
     * @param availableSpace Espacio disponible del almacenamiento.
     * @param users Lista de usuarios que tienen acceso al almacenamiento.
     */
    public Storage(double totalSpace, double availableSpace, List<User> users) {
        this.totalSpace = totalSpace;
        this.availableSpace = availableSpace;
        this.users = users;
    }

    /**
     * Método que devuelve el espacio total del almacenamiento.
     *
     * @return Espacio total del almacenamiento.
     */
    public double getTotalSpace() {
        // TODO implement here
        return  0;
    }

    /**
     * Método que devuelve el espacio disponible del almacenamiento.
     *
     * @return Espacio disponible del almacenamiento.
     */
    public double getAvailableSpace() {
        // TODO implement here
        return 0;
    }

    /**
     * Método que devuelve la lista de usuarios que tienen acceso al almacenamiento.
     *
     * @return Lista de usuarios que tienen acceso al almacenamiento.
     */
    public List<User> getUsersList() {
        // TODO implement here
        return null;
    }

}
