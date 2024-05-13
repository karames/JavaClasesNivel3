package personas.jdbc;

import java.sql.*;
import java.util.*;

import personas.dto.PersonaDTO;

/**
 * CRUD consultas con JDBC
 *
 * @author Nono
 */
public class PersonaDAOJDBC implements PersonaDAO {
    // Variable que almacena una conexión como referencia
    // se recibe en el constructor de esta clase
    // y permite reutilizar la misma conexión para ejecutar
    // varios queries de esta clase
    // Se puede utilizar para el uso de una transacción en SQL
    private Connection userConn;

    // Omitir 'id_persona' por ser PK con valor autoincrementable
    // 'PreparedStatement' permite utilizar parámetros (?),
    // los cuales posteriormente serán sustituidos por sus respectivos valores
    private final String SQL_SELECT = "SELECT id_persona, nombre, apellido FROM personas ORDER BY id_persona";
    private final String SQL_INSERT = "INSERT INTO personas(nombre, apellido) VALUES(?, ?)";
    private final String SQL_UPDATE = "UPDATE personas SET nombre = ?, apellido = ? WHERE id_persona = ?";
    private final String SQL_DELETE = "DELETE FROM personas WHERE id_persona = ?";

    // Constructor vacío
    public PersonaDAOJDBC() {
    }

    /**
     * Constructor que asigna una conexión existente para ser utilizada
     * en los queries de esta clase
     *
     * @param conn Conexión a la BD previamente creada
     */
    public PersonaDAOJDBC(Connection conn) {
        this.userConn = conn;
    }

    /**
     * Método que regresa el contenido de la tabla personas (SELECT)
     *
     * @return List<PersonaDTO> Almacena resultado SELECT
     * @throws SQLException Propagamos el error a la clase de prueba
     */
    public List<PersonaDTO> select() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PersonaDTO persona = null;
        List<PersonaDTO> personasLista = new ArrayList<PersonaDTO>();
        try {
            // Reutilizar conexión (se utiliza si existe, en caso contrario se crea)
            conn = (this.userConn != null) ? this.userConn : Conexion.conectarBD();
            System.out.println("\nEJECUTANDO QUERY: " + SQL_SELECT);
            pstmt = conn.prepareStatement(SQL_SELECT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String nombre = rs.getString(2);
                String apellido = rs.getString(3);
                persona = new PersonaDTO();
                persona.setId(id);
                persona.setNombre(nombre);
                persona.setApellido(apellido);
                personasLista.add(persona);
            }
        }
        // catch (SQLException e) {
        // System.out.println(e.getMessage());
        // throw new RuntimeException(e);
        // }
        finally {
            Conexion.close(rs);
            Conexion.close(pstmt);
            // Sólo cerramos la conexión si fue creada en este método
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return personasLista;
    }

    /**
     * Método que inserta un registro en la tabla personas (INSERT)
     *
     * @param nombre   Nuevo Valor
     * @param apellido Nuevo Valor
     * @return int Número de registros afectados
     * @throws SQLException Propagamos el error a la clase de prueba
     */
    public int insert(PersonaDTO persona) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int rows = 0; // registros afectados
        try {
            // Reutilizar conexión (se utiliza si existe, en caso contrario se crea)
            conn = (this.userConn != null) ? this.userConn : Conexion.conectarBD();
            System.out.println("\nEJECUTANDO QUERY: " + SQL_INSERT);
            pstmt = conn.prepareStatement(SQL_INSERT);
            int index = 1; // contador de parámetros (columnas)
            pstmt.setString(index++, persona.getNombre()); // parámetro 1 => ?
            pstmt.setString(index, persona.getApellido()); // parámetro 2 => ?
            rows = pstmt.executeUpdate(); // número de registros afectados
            System.out.println("Registros insertados: " + rows);
        }
        // catch (SQLException e) {
        // System.out.println(e.getMessage());
        // throw new RuntimeException(e);
        // }
        finally {
            Conexion.close(pstmt);
            // Sólo cerramos la conexión si fue creada en este método
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return rows;
    }

    /**
     * Método que actualiza un registro existente (UPDATE)
     *
     * @param id       Clave primaria
     * @param nombre   Nuevo Valor
     * @param apellido Nuevo Valor
     * @return int Número de registros afectados
     * @throws SQLException Propagamos el error a la clase de prueba
     */
    public int update(PersonaDTO persona) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int rows = 0;
        try {
            // Reutilizar conexión (se utiliza si existe, en caso contrario se crea)
            conn = (this.userConn != null) ? this.userConn : Conexion.conectarBD();
            System.out.println("\nEJECUTANDO QUERY: " + SQL_UPDATE);
            pstmt = conn.prepareStatement(SQL_UPDATE);
            int index = 1;
            pstmt.setString(index++, persona.getNombre()); // parámetro 1 => ?
            pstmt.setString(index++, persona.getApellido()); // parámetro 2 => ?
            pstmt.setInt(index, persona.getId()); // parámetro 3 => ?
            rows = pstmt.executeUpdate();
            System.out.println("Registros actualizados: " + rows);
        }
        // catch (SQLException e) {
        // System.out.println(e.getMessage());
        // throw new RuntimeException(e);
        // }
        finally {
            Conexion.close(pstmt);
            // Sólo cerramos la conexión si fue creada en este método
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return rows;
    }

    /**
     * Método que elimina un registro (DELETE)
     *
     * @param id Clave primaria
     * @return int Número de registros afectados
     * @throws SQLException Propagamos el error a la clase de prueba
     */
    public int delete(PersonaDTO persona) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int rows = 0;
        try {
            // Reutilizar conexión (se utiliza si existe, en caso contrario se crea)
            conn = (this.userConn != null) ? this.userConn : Conexion.conectarBD();
            System.out.println("\nEJECUTANDO QUERY: " + SQL_DELETE);
            pstmt = conn.prepareStatement(SQL_DELETE);
            pstmt.setInt(1, persona.getId());
            rows = pstmt.executeUpdate();
            System.out.println("Registros eliminados: " + rows);
        }
        // catch (SQLException e) {
        // System.out.println(e.getMessage());
        // throw new RuntimeException(e);
        // }
        finally {
            Conexion.close(pstmt);
            // Sólo cerramos la conexión si fue creada en este método
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return rows;
    }
}
