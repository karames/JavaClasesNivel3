import datos.Conexion;
import datos.PersonasJDBC;
import java.sql.*;

public class ManejoPersonas {
    public static void main(String[] args) throws Exception {
        // Crear un objeto conexión para compartir con todos los queries
        Connection conn = null;

        try {
            // Conectar con la BD
            conn = Conexion.conectarBD();

            // Revisar si la conexión está en modo autocommit
            // por defecto autocommit == true
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            // Crear objeto PersonasJDBC con la conexión creada
            PersonasJDBC personasJDBC = new PersonasJDBC(conn);

            // Comenzar ejecución sentencias
            // Una transacción agrupa varias sentencias SQL
            // si algo falla no se realizan los cambios en la BD
            // Operación correcta
            personasJDBC.update(2, "UpdateNombre", "UpdateApellido");

            // Provocar error -> campo apellido supera los 45 caracteres
            personasJDBC.insert("Miguel2",
                    "Ayala12341234123412341234123412341234123412341234123412341234123412341234123412341234");
            // "Ayala2");

            // Guardar cambios
            System.out.println("\nREALIZAR COMMIT");
            conn.commit();
            System.out.println("Transacción completada");
        } catch (SQLException e) {
            try {
                // Hacer rollback en caso de error
                System.out.println("");
                e.printStackTrace();
                System.out.println("\nREALIZAR ROLLBACK");
                conn.rollback();
                System.out.println("OK - Se ha efectuado ROLLBACK");
                System.out.println("La transacción no pudo completarse");
            } catch (SQLException e1) {
                System.out.println("\nNO SE PUDO LLEVAR A CABO ROLLBACK");
                e1.printStackTrace();
            }
        }
    }
}
