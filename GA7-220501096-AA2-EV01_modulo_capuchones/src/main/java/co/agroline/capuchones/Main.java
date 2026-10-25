package co.agroline.capuchones;

import co.agroline.capuchones.datos.ConexionBD;
import co.agroline.capuchones.vista.MenuConsola;

import java.sql.SQLException;

/**
 * Modulo de capuchones - Agro Line
 * GA7-220501096-AA2-EV01 Codificacion de modulos del software
 * Autor: Harrison Rodriguez Galindo - Ficha 3336080
 */
public class Main {

    public static void main(String[] args) {
        try {
            ConexionBD.crearTabla();
        } catch (SQLException e) {
            System.out.println("No se pudo preparar la base de datos: " + e.getMessage());
            return;
        }
        new MenuConsola().iniciar();
    }
}
