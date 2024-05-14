package personas.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase Conexión JDBC
 */
public class Conexion {
    // Valores de conexión a MariaDB
    // El puerto es opcional (omisión SSL da un warning)
    // "jdbc:mariadb://localhost:3306/sga-nono?useSSL=false";
    private static String HOST = "jdbc:mariadb://localhost:3306/";
    // private static String HOST =
    // "jdbc:sqlite:D:/01_Archivos_Programas/SQLiteStudio/data/sga-nono.db";
    private static String BD = "sga-nono";
    private static String USER = "root";
    private static String PASSWORD = "";

    // Para evitar problemas de conexión de forma concurrente (synchronized)
    public static synchronized Connection conectarBD() {
        System.out.println("");
        System.out.println("CONECTANDO CON LA BD...");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(HOST + BD, USER, PASSWORD);
            // conn = DriverManager.getConnection(HOST);
            System.out.println("OK - Se ha establecido conexión con la BD");
        } catch (SQLException e) {
            System.out.println("ERROR - No se pudo conectar con la BD");
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return conn;
    }

    // Cerrar ResultSet
    public static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // Cerrar PreparedStatement
    public static void close(PreparedStatement pstmt) {
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // Cerrar Connection
    public static void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
