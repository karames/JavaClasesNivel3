import java.sql.*;

public class IntroJDBC {
    public static void main(String[] args) throws Exception {
        // Cadena de conexión de MySql/MariaDB, el parámetro useSSL es opcional
        // String url = "jdbc:mysql://localhost:3306/sga?useSSL=false";
        // String url = "jdbc:mariadb://localhost:3306/sga-nono?user=root&password=";
        String url = "jdbc:mariadb://localhost:3306/sga-nono";

        try {
            // Cargar driver mysql/mariadb
            // Class.forName("com.mysql.jdbc.Driver");
            Class.forName("org.mariadb.jdbc.Driver");

            // Crear objeto Connection
            Connection connection = DriverManager.getConnection(url, "root", "");

            // Creamos un objeto Statement
            Statement sentencia = connection.createStatement();

            // Crear query
            String sql = "select id_persona, nombre, apellido from persona";
            ResultSet resultado = sentencia.executeQuery(sql);
            while (resultado.next()) {
                System.out.println("");
                System.out.print("Id: " + resultado.getInt(1));
                System.out.print("\tNombre: " + resultado.getString(2));
                System.out.print("\tApellido: " + resultado.getString(3));
            }

            // Cerrar objetos utilizados
            connection.close();
            sentencia.close();
            resultado.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
