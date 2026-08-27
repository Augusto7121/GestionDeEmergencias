package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmergenciaVehiculoDAO {
    private Connection con;

    public EmergenciaVehiculoDAO() {
        this.con = Conexion.getConexion();
    }

    public void agregar(int emergenciaId, int vehiculoId) {
        String sql = "INSERT INTO emergencia_vehiculo (emergencia_id, vehiculo_id) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, emergenciaId);
            ps.setInt(2, vehiculoId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void eliminarPorEmergencia(int emergenciaId) {
        String sql = "DELETE FROM emergencia_vehiculo WHERE emergencia_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, emergenciaId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Vehiculo> obtenerPorEmergencia(int emergenciaId) {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = "SELECT v.id, v.patente, v.tipo, v.estado " +
                "FROM emergencia_vehiculo ev " +
                "JOIN vehiculo v ON ev.vehiculo_id = v.id " +
                "WHERE ev.emergencia_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, emergenciaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Vehiculo(
                            rs.getInt("id"),
                            rs.getString("patente"),
                            rs.getString("tipo"),
                            rs.getString("estado")
                    ));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }
}
