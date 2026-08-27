package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/gestion_emergencias?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CLAVE = ""; // ⚠️ si tenés contraseña, ponela acá

    public static Connection getConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
            //System.out.println("✅ Nueva conexión establecida");
            return conexion;
        } catch (ClassNotFoundException e) {
            System.err.println("❌ No se encontró el driver de MySQL");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar con la base de datos");
            e.printStackTrace();
        }
        return null;
    }
}
