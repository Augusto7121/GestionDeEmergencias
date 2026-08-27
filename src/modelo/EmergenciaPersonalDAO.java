package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmergenciaPersonalDAO {
    private Connection con;

    public EmergenciaPersonalDAO() {
        this.con = Conexion.getConexion();
    }

    public void agregar(int emergenciaId, int personalId) {
        String sql = "INSERT INTO emergencia_personal (emergencia_id, personal_id) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, emergenciaId);
            ps.setInt(2, personalId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void eliminarPorEmergencia(int emergenciaId) {
        String sql = "DELETE FROM emergencia_personal WHERE emergencia_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, emergenciaId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Personal> obtenerPorEmergencia(int emergenciaId) {
        List<Personal> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.nombre, j.id AS jerarquia_id, j.nombre AS jerarquia_nombre " +
                "FROM emergencia_personal ep " +
                "JOIN personal p ON ep.personal_id = p.id " +
                "LEFT JOIN jerarquia j ON p.jerarquia_id = j.id " +
                "WHERE ep.emergencia_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, emergenciaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Jerarquia j = new Jerarquia(
                            rs.getInt("jerarquia_id"),
                            rs.getString("jerarquia_nombre")
                    );
                    lista.add(new Personal(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            j
                    ));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }
}
