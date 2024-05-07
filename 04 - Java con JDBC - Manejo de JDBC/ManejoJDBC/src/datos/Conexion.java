package datos;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase Conexión JDBC
 */
public class Conexion {

    // Valores de conexión a MySql
    // private static String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    // El puerto es opcional (omisión SSL da un warning)
    private static String JDBC_URL = "jdbc:mariadb://localhost:3306/sga-nono?useSSL=false";
    private static String JDBC_USER = "root";
    private static String JDBC_PASS = "";
    private static Driver driver = null;

    // Para que no haya problemas al obtener la conexión de
    // manera concurrente, se usa la palabra synchronized
    public static synchronized Connection getConnection() throws SQLException {
        if (driver == null) {
            try {
                // Registrar driver
                driver = new org.mariadb.jdbc.Driver();
                DriverManager.registerDriver(driver);
                System.out.println("Registrado el driver JDBC");
            } catch (Exception e) {
                System.out.println("Error al cargar el driver JDBC");
                e.printStackTrace();
            }
        }
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
    }

    // Cerrar ResultSet
    public static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    // Cerrar PreparedStatement
    public static void close(PreparedStatement pstmt) {
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    // Cerrar Connection
    public static void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
}
