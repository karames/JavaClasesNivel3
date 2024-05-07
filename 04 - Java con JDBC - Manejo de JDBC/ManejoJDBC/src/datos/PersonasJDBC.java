package datos;

import domain.Persona;
import java.sql.*;
import java.util.*;

/**
 * CRUD consultas con JDBC
 *
 * @author Nono
 */
public class PersonasJDBC {
    // Omitir 'id_persona' por ser PK von valor autoincrementable
    // 'PreparedStatement' permite utilizar parámetros (?),
    // los cuales posteriormente serán sustituidos por sus respectivos valores
    private final String SQL_SELECT = "SELECT id_persona, nombre, apellido FROM persona ORDER BY id_persona";
    private final String SQL_INSERT = "INSERT INTO persona(nombre, apellido) VALUES(?, ?)";
    private final String SQL_UPDATE = "UPDATE persona SET nombre = ?, apellido = ? WHERE id_persona = ?";
    private final String SQL_DELETE = "DELETE FROM persona WHERE id_persona = ?";

    /**
     * Método que regresa el contenido de la tabla personas (SELECT)
     *
     * @return List<Persona> Almacena resultado SELECT
     */
    public List<Persona> select() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Persona persona = null;
        List<Persona> personasLista = new ArrayList<Persona>();
        try {
            conn = Conexion.conectarBD();
            System.out.println("Ejecutando query: " + SQL_SELECT);
            pstmt = conn.prepareStatement(SQL_SELECT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String nombre = rs.getString(2);
                String apellido = rs.getString(3);
                persona = new Persona();
                persona.setId(id);
                persona.setNombre(nombre);
                persona.setApellido(apellido);
                personasLista.add(persona);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            Conexion.close(rs);
            Conexion.close(pstmt);
            Conexion.close(conn);
        }
        return personasLista;
    }

    /**
     * Método que inserta un registro en la tabla personas (INSERT)
     *
     * @param nombre   Nuevo Valor
     * @param apellido Nuevo Valor
     * @return int Número de registros afectados
     */
    public int insert(String nombre, String apellido) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int rows = 0; // registros afectados
        try {
            conn = Conexion.conectarBD();
            System.out.println("Ejecutando query: " + SQL_INSERT);
            pstmt = conn.prepareStatement(SQL_INSERT);
            int index = 1; // contador de parámetros (columnas)
            pstmt.setString(index++, nombre); // parámetro 1 => ?
            pstmt.setString(index, apellido); // parámetro 2 => ?
            rows = pstmt.executeUpdate(); // número de registros afectados
            System.out.println("Registros insertados: " + rows);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            Conexion.close(pstmt);
            Conexion.close(conn);
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
     */
    public int update(int id, String nombre, String apellido) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int rows = 0;
        try {
            conn = Conexion.conectarBD();
            System.out.println("Ejecutando query: " + SQL_UPDATE);
            pstmt = conn.prepareStatement(SQL_UPDATE);
            int index = 1;
            pstmt.setString(index++, nombre); // parámetro 1 => ?
            pstmt.setString(index++, apellido); // parámetro 2 => ?
            pstmt.setInt(index, id); // parámetro 3 => ?
            rows = pstmt.executeUpdate();
            System.out.println("Registros actualizados: " + rows);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            Conexion.close(pstmt);
            Conexion.close(conn);
        }
        return rows;
    }

    /**
     * Método que elimina un registro (DELETE)
     *
     * @param id Clave primaria
     * @return int Número de registros afectados
     */
    public int delete(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int rows = 0;
        try {
            conn = Conexion.conectarBD();
            System.out.println("Ejecutando query: " + SQL_DELETE);
            pstmt = conn.prepareStatement(SQL_DELETE);
            pstmt.setInt(1, id);
            rows = pstmt.executeUpdate();
            System.out.println("Registros eliminados: " + rows);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            Conexion.close(pstmt);
            Conexion.close(conn);
        }
        return rows;
    }
}
