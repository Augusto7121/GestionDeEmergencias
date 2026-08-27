package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JerarquiaDAO {

    public List<Jerarquia> listar() {
        List<Jerarquia> lista = new ArrayList<>();
        String sql = "SELECT * FROM jerarquia";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Jerarquia(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (SQLException e) {
            System.out.println("Error JerarquiaDAO.listar: " + e.getMessage());
        }
        return lista;
    }

    public void agregar(Jerarquia j) {
        String sql = "INSERT INTO jerarquia (nombre) VALUES (?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, j.getNombre());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error JerarquiaDAO.agregar: " + e.getMessage());
        }
    }

    public void actualizar(Jerarquia j) {
        String sql = "UPDATE jerarquia SET nombre = ? WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, j.getNombre());
            ps.setInt(2, j.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error JerarquiaDAO.actualizar: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM jerarquia WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error JerarquiaDAO.eliminar: " + e.getMessage());
        }
    }
}
