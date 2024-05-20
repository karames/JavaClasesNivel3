import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import datos.PoolConexionesMariaDB;

public class TestPoolConexiones {
    public static void main(String[] args) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // Probamos el pool con MariaDB
            conn = PoolConexionesMariaDB.getConexion();
            System.out.println("\nUtilizamos el pool de conexiones con MariaDB");
            pstmt = conn.prepareStatement("SELECT * FROM personas");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.print(" " + rs.getInt(1)); // id_persona
                System.out.print(" " + rs.getString(2)); // nombre
                System.out.println(" " + rs.getString(3)); // apellido
            }
            // Cerrar la conexión para que regrese al pool
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
