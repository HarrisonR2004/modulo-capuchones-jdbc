package co.agroline.capuchones.datos;

import co.agroline.capuchones.modelo.Capuchon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Operaciones CRUD de la tabla capuchon usando JDBC.
 * Se usa PreparedStatement en todas las consultas para evitar inyeccion SQL.
 * Autor: Harrison Rodriguez Galindo - Ficha 3336080
 */
public class CapuchonDAO {

    // INSERTAR
    public boolean insertar(Capuchon capuchon) throws SQLException {
        String sql = "INSERT INTO capuchon (valor_bd, nombre_visible, color_fondo, activo) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, capuchon.getValorBd());
            ps.setString(2, capuchon.getNombreVisible());
            ps.setString(3, capuchon.getColorFondo());
            ps.setInt(4, capuchon.isActivo() ? 1 : 0);
            return ps.executeUpdate() == 1;
        }
    }

    // CONSULTAR todos
    public List<Capuchon> listar() throws SQLException {
        List<Capuchon> lista = new ArrayList<>();
        String sql = "SELECT id, valor_bd, nombre_visible, color_fondo, activo FROM capuchon ORDER BY nombre_visible";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(leerFila(rs));
            }
        }
        return lista;
    }

    // CONSULTAR uno por el valor que se guarda en produccion
    public Capuchon buscarPorValor(String valorBd) throws SQLException {
        String sql = "SELECT id, valor_bd, nombre_visible, color_fondo, activo FROM capuchon WHERE valor_bd = ?";
        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, valorBd);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return leerFila(rs);
                }
            }
        }
        return null;
    }

    // ACTUALIZAR (el valor_bd no se cambia porque es el que esta guardado en los registros de ramos)
    public boolean actualizar(Capuchon capuchon) throws SQLException {
        String sql = "UPDATE capuchon SET nombre_visible = ?, color_fondo = ?, activo = ? WHERE id = ?";
        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, capuchon.getNombreVisible());
            ps.setString(2, capuchon.getColorFondo());
            ps.setInt(3, capuchon.isActivo() ? 1 : 0);
            ps.setInt(4, capuchon.getId());
            return ps.executeUpdate() == 1;
        }
    }

    // ELIMINAR
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM capuchon WHERE id = ?";
        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    private Capuchon leerFila(ResultSet rs) throws SQLException {
        return new Capuchon(
                rs.getInt("id"),
                rs.getString("valor_bd"),
                rs.getString("nombre_visible"),
                rs.getString("color_fondo"),
                rs.getInt("activo") == 1);
    }
}
