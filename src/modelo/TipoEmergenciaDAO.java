package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoEmergenciaDAO {

    public List<TipoEmergencia> listar() {
        List<TipoEmergencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipo_emergencia";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new TipoEmergencia(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (SQLException e) {
            System.out.println("Error TipoEmergenciaDAO.listar: " + e.getMessage());
        }
        return lista;
    }

    public void agregar(TipoEmergencia t) {
        String sql = "INSERT INTO tipo_emergencia (nombre) VALUES (?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getNombre());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error TipoEmergenciaDAO.agregar: " + e.getMessage());
        }
    }

    public void actualizar(TipoEmergencia t) {
        String sql = "UPDATE tipo_emergencia SET nombre = ? WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getNombre());
            ps.setInt(2, t.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error TipoEmergenciaDAO.actualizar: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM tipo_emergencia WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error TipoEmergenciaDAO.eliminar: " + e.getMessage());
        }
    }
}
