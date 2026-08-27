package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonalDAO {

    public List<Personal> listar() {
        List<Personal> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.nombre, p.jerarquia_id, j.nombre AS jer_nombre " +
                "FROM personal p LEFT JOIN jerarquia j ON p.jerarquia_id = j.id";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Jerarquia jer = null;
                int jerId = rs.getInt("jerarquia_id");
                if (!rs.wasNull()) jer = new Jerarquia(jerId, rs.getString("jer_nombre"));
                lista.add(new Personal(rs.getInt("id"), rs.getString("nombre"), jer));
            }
        } catch (Exception e) { System.out.println("Error listar personal: " + e.getMessage()); }
        return lista;
    }

    public void agregar(Personal p) {
        String sql = "INSERT INTO personal (nombre, jerarquia_id) VALUES (?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setObject(2, p.getJerarquia() != null ? p.getJerarquia().getId() : null);
            ps.executeUpdate();
        } catch (Exception e) { System.out.println("Error agregar personal: " + e.getMessage()); }
    }

    public void actualizar(Personal p) {
        String sql = "UPDATE personal SET nombre=?, jerarquia_id=? WHERE id=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setObject(2, p.getJerarquia() != null ? p.getJerarquia().getId() : null);
            ps.setInt(3, p.getId());
            ps.executeUpdate();
        } catch (Exception e) { System.out.println("Error actualizar personal: " + e.getMessage()); }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM personal WHERE id=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id); ps.executeUpdate();
        } catch (Exception e) { System.out.println("Error eliminar personal: " + e.getMessage()); }
    }
    public Personal obtenerPorId(int id) {
        String sql = "SELECT p.id, p.nombre, p.jerarquia_id, j.nombre AS jer_nombre " +
                "FROM personal p LEFT JOIN jerarquia j ON p.jerarquia_id = j.id " +
                "WHERE p.id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Jerarquia jer = null;
                    int jerId = rs.getInt("jerarquia_id");
                    if (!rs.wasNull()) jer = new Jerarquia(jerId, rs.getString("jer_nombre"));
                    return new Personal(rs.getInt("id"), rs.getString("nombre"), jer);
                }
            }
        } catch (Exception e) {
            System.out.println("Error obtener personal por id: " + e.getMessage());
        }
        return null;
    }

}
