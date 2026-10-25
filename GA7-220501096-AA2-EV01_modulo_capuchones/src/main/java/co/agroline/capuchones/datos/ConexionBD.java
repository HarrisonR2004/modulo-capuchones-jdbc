package co.agroline.capuchones.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Maneja la conexion JDBC a la base de datos.
 * En desarrollo se usa SQLite (archivo capuchones.db) para no tocar la base de produccion.
 * Autor: Harrison Rodriguez Galindo - Ficha 3336080
 */
public class ConexionBD {

    private static final String URL = "jdbc:sqlite:capuchones.db";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // crea la tabla si no existe, igual a amd_capuchon pero con los campos basicos
    public static void crearTabla() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS capuchon ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " valor_bd TEXT NOT NULL UNIQUE,"
                + " nombre_visible TEXT NOT NULL,"
                + " color_fondo TEXT CHECK (color_fondo IS NULL OR length(color_fondo) = 7),"
                + " activo INTEGER NOT NULL DEFAULT 1"
                + ")";
        try (Connection con = conectar(); Statement st = con.createStatement()) {
            st.execute(sql);
        }
    }
}
