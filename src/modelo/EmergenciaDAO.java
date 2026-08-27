package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmergenciaDAO {

    public int agregarConRetorno(Emergencia e) {
        String sql = "INSERT INTO emergencia (fecha, hora, direccion, tipo_id, resultado) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, e.getFecha());
            ps.setTime(2, e.getHora());
            ps.setString(3, e.getDireccion());
            ps.setInt(4, e.getTipo().getId());
            ps.setString(5, e.getResultado());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public List<Emergencia> listar() {
        List<Emergencia> lista = new ArrayList<>();
        String sql = "SELECT e.id, e.fecha, e.hora, e.direccion, e.resultado, " +
                "t.id AS tipo_id, t.nombre AS tipo_nombre " +
                "FROM emergencia e " +
                "LEFT JOIN tipo_emergencia t ON e.tipo_id = t.id";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TipoEmergencia tipo = new TipoEmergencia(
                        rs.getInt("tipo_id"),
                        rs.getString("tipo_nombre")
                );

                Emergencia e = new Emergencia(
                        rs.getInt("id"),
                        rs.getDate("fecha"),
                        rs.getTime("hora"),
                        rs.getString("direccion"),
                        tipo,
                        rs.getString("resultado")
                );

                // 🔹 Cargar personal asociado
                e.setPersonales(obtenerPersonalesDeEmergencia(e.getId(), con));

                // 🔹 Cargar vehículos asociados
                e.setVehiculos(obtenerVehiculosDeEmergencia(e.getId(), con));

                lista.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    private List<Personal> obtenerPersonalesDeEmergencia(int emergenciaId, Connection con) throws SQLException {
        List<Personal> personales = new ArrayList<>();
        String sql = "SELECT p.id, p.nombre, j.id AS jerarquia_id, j.nombre AS jerarquia_nombre " +
                "FROM emergencia_personal ep " +
                "JOIN personal p ON ep.personal_id = p.id " +
                "LEFT JOIN jerarquia j ON p.jerarquia_id = j.id " +
                "WHERE ep.emergencia_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, emergenciaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Jerarquia jerarquia = new Jerarquia(
                            rs.getInt("jerarquia_id"),
                            rs.getString("jerarquia_nombre")
                    );
                    personales.add(new Personal(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            jerarquia
                    ));
                }
            }
        }
        return personales;
    }

    private List<Vehiculo> obtenerVehiculosDeEmergencia(int emergenciaId, Connection con) throws SQLException {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String sql = "SELECT v.id, v.patente, v.tipo, v.estado " +
                "FROM emergencia_vehiculo ev " +
                "JOIN vehiculo v ON ev.vehiculo_id = v.id " +
                "WHERE ev.emergencia_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, emergenciaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    vehiculos.add(new Vehiculo(
                            rs.getInt("id"),
                            rs.getString("patente"),
                            rs.getString("tipo"),
                            rs.getString("estado")
                    ));
                }
            }
        }
        return vehiculos;
    }

    public void actualizar(Emergencia e) {
        String sql = "UPDATE emergencia SET fecha=?, hora=?, direccion=?, tipo_id=?, resultado=? WHERE id=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, e.getFecha());
            ps.setTime(2, e.getHora());
            ps.setString(3, e.getDireccion());
            ps.setInt(4, e.getTipo().getId());
            ps.setString(5, e.getResultado());
            ps.setInt(6, e.getId());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM emergencia WHERE id=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public Emergencia obtenerPorId(int id) {
        String sql = "SELECT e.id, e.fecha, e.hora, e.direccion, e.resultado, " +
                "t.id AS tipo_id, t.nombre AS tipo_nombre " +
                "FROM emergencia e " +
                "LEFT JOIN tipo_emergencia t ON e.tipo_id = t.id " +
                "WHERE e.id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    TipoEmergencia tipo = new TipoEmergencia(
                            rs.getInt("tipo_id"),
                            rs.getString("tipo_nombre")
                    );

                    Emergencia e = new Emergencia(
                            rs.getInt("id"),
                            rs.getDate("fecha"),
                            rs.getTime("hora"),
                            rs.getString("direccion"),
                            tipo,
                            rs.getString("resultado")
                    );

                    e.setPersonales(obtenerPersonalesDeEmergencia(e.getId(), con));
                    e.setVehiculos(obtenerVehiculosDeEmergencia(e.getId(), con));
                    return e;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
