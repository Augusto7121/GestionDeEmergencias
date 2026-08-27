package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

    public List<Vehiculo> listar() {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM vehiculo";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Vehiculo(rs.getInt("id"), rs.getString("patente"),
                        rs.getString("tipo"), rs.getString("estado")));
            }
        } catch (Exception e) { System.out.println("Error listar vehiculos: " + e.getMessage()); }
        return lista;
    }

    public void agregar(Vehiculo v) {
        String sql = "INSERT INTO vehiculo (patente, tipo, estado) VALUES (?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, v.getPatente());
            ps.setString(2, v.getTipo());
            ps.setString(3, v.getEstado());
            ps.executeUpdate();
        } catch (Exception e) { System.out.println("Error agregar vehiculo: " + e.getMessage()); }
    }

    public void actualizar(Vehiculo v) {
        String sql = "UPDATE vehiculo SET patente=?, tipo=?, estado=? WHERE id=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, v.getPatente());
            ps.setString(2, v.getTipo());
            ps.setString(3, v.getEstado());
            ps.setInt(4, v.getId());
            ps.executeUpdate();
        } catch (Exception e) { System.out.println("Error actualizar vehiculo: " + e.getMessage()); }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM vehiculo WHERE id=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id); ps.executeUpdate();
        } catch (Exception e) { System.out.println("Error eliminar vehiculo: " + e.getMessage()); }
    }
}
