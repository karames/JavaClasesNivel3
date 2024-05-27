package inventario.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * CRUD consultas con JDBC - SQLite
 *
 * @author Nono
 */
public class ProductoDAOImp implements ProductoDAO {
    // Variable que almacena una conexión como referencia
    private Connection userConn;
    private final String HOST = "jdbc:sqlite:D:/01_Archivos_Programas/DB Browser for SQLite/data/inventario.db";

    // Declaraciones SQL
    private final String SQL_CREATE_TABLE_PRODUCTOS = "CREATE TABLE IF NOT EXISTS productos (id INTEGER, nombre TEXT, categoria TEXT, precio REAL, cantidad INTEGER, PRIMARY KEY(id AUTOINCREMENT))";
    private final String SQL_INSERT_LOTE = "INSERT INTO productos (nombre, categoria, precio, cantidad) VALUES ('Laptop', 'Electrónica', 1200.99, 10), ('Smartphone', 'Electrónica', 699.49, 25), ('Tablet', 'Electrónica', 399.00, 15), ('Auriculares', 'Accesorios', 89.99, 50), ('Teclado', 'Accesorios', 45.00, 30), ('Mouse', 'Accesorios', 20.99, 40), ('Monitor', 'Electrónica', 299.99, 20), ('Impresora', 'Oficina', 149.99, 10), ('Silla de oficina',   'Mobiliario', 89.99, 12), ('Escritorio', 'Mobiliario', 159.99, 8)";
    private final String SQL_INSERT = "INSERT INTO productos (nombre, categoria, precio, cantidad) VALUES(?, ?, ?, ?)";
    private final String SQL_SELECT = "SELECT id, nombre, categoria, precio, cantidad FROM productos ORDER BY id";
    private final String SQL_UPDATE = "UPDATE productos SET nombre = ?, categoria = ?, precio = ?, cantidad = ? WHERE id = ?";
    private final String SQL_DELETE = "DELETE FROM productos WHERE id = ?";

    // Constructor vacío
    public ProductoDAOImp() {
    }

    /**
     * Crear conexión base de datos
     * Para evitar problemas de conexión de forma concurrente (synchronized)
     */
    @Override
    public synchronized void ConectarCrearBaseDatos() throws SQLException {
        System.out.println("");
        System.out.println("CREANDO/CONECTANDO CON LA BD...");
        Connection conn = null;
        try {
            if (this.userConn != null) {
                conn = this.userConn;
            } else {
                conn = DriverManager.getConnection(HOST);
                this.userConn = conn;
            }
            System.out.println("OK - Se ha establecido conexión con la BD");
        } catch (SQLException sqle) {
            System.out.println("ERROR - No se pudo conectar con la BD");
            System.out.println(sqle.getMessage());
            throw new RuntimeException(sqle);
        }
    }

    /**
     * Crear tabla productos
     */
    @Override
    public void CrearTabla() throws SQLException {
        System.out.println("");
        System.out.println("CREANDO TABLA PRODUCTOS...");
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            if (this.userConn != null) {
                conn = this.userConn;
            } else {
                conn = DriverManager.getConnection(HOST);
                this.userConn = conn;
            }
            ps = this.userConn.prepareStatement(SQL_CREATE_TABLE_PRODUCTOS);
            ps.executeUpdate();
        } catch (SQLException sqle) {
            System.out.println("ERROR - No se pudo crear la tabla productos");
            System.out.println(sqle.getMessage());
            throw new RuntimeException(sqle);
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * Insertar varios registros en lote
     *
     * @return int número de registros insertados
     */
    @Override
    public int insertarLote() throws SQLException {
        System.out.println("");
        System.out.println("INSERTANDO LOTE REGISTROS EN LA TABLA PRODUCTOS...");
        Connection conn = null;
        PreparedStatement ps = null;
        int rows = 0; // registros afectados
        try {
            if (this.userConn != null) {
                conn = this.userConn;
            } else {
                conn = DriverManager.getConnection(HOST);
                this.userConn = conn;
            }
            System.out.println("\nEJECUTANDO QUERY: " + SQL_INSERT_LOTE);
            ps = conn.prepareStatement(SQL_INSERT_LOTE);
            rows = ps.executeUpdate(); // número de registros afectados
            System.out.println("Registros insertados: " + rows);
        } catch (SQLException sqle) {
            System.out.println("ERROR - No se puedo insertar datos en la tabla");
            System.out.println(sqle.getMessage());
            throw new RuntimeException(sqle);
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
        return rows;
    }

    /**
     * Inserta un registro en la base de datos
     *
     * @return int número de registros insertados
     */
    @Override
    public int insertar(ProductoDTO producto) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        int rows = 0; // registros afectados
        try {
            if (this.userConn != null) {
                conn = this.userConn;
            } else {
                conn = DriverManager.getConnection(HOST);
                this.userConn = conn;
            }
            System.out.println("\nEJECUTANDO QUERY: " + SQL_INSERT);
            ps = conn.prepareStatement(SQL_INSERT);
            int index = 1; // contador de parámetros (columnas)
            ps.setString(index++, producto.getNombre()); // parámetro 1 => ?
            ps.setString(index++, producto.getCategoria()); // parámetro 2 => ?
            ps.setFloat(index++, producto.getPrecio()); // parámetro 3 => ?
            ps.setInt(index, producto.getCantidad()); // parámetro 4 => ?
            rows = ps.executeUpdate(); // número de registros afectados
            System.out.println("Registros insertados: " + rows);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
        return rows;
    }

    /**
     * Método que regresa el contenido de la tabla productos (SELECT)
     *
     * @return List<ProductoDTO> Almacena resultado SELECT
     */
    @Override
    public List<ProductoDTO> select() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ProductoDTO producto = null;
        List<ProductoDTO> productosLista = new ArrayList<ProductoDTO>();
        try {
            if (this.userConn != null) {
                conn = this.userConn;
            } else {
                conn = DriverManager.getConnection(HOST);
                this.userConn = conn;
            }
            System.out.println("\nEJECUTANDO QUERY: " + SQL_SELECT);
            ps = conn.prepareStatement(SQL_SELECT);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String nombre = rs.getString(2);
                String categoria = rs.getString(3);
                float precio = rs.getFloat(4);
                int cantidad = rs.getInt(5);
                producto = new ProductoDTO();
                producto.setId(id);
                producto.setNombre(nombre);
                producto.setCategoria(categoria);
                producto.setPrecio(precio);
                producto.setCantidad(cantidad);
                productosLista.add(producto);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return productosLista;
    }

    /**
     * Actualizar registro
     *
     * @return int número de registros afectados
     */
    @Override
    public int update(ProductoDTO producto) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        int rows = 0;
        try {
            if (this.userConn != null) {
                conn = this.userConn;
            } else {
                conn = DriverManager.getConnection(HOST);
                this.userConn = conn;
            }
            System.out.println("\nEJECUTANDO QUERY: " + SQL_UPDATE);
            ps = conn.prepareStatement(SQL_UPDATE);
            int index = 1; // contador de parámetros (columnas)
            ps.setString(index++, producto.getNombre()); // parámetro 1 => ?
            ps.setString(index++, producto.getCategoria()); // parámetro 2 => ?
            ps.setFloat(index++, producto.getPrecio()); // parámetro 3 => ?
            ps.setInt(index++, producto.getCantidad()); // parámetro 4 => ?
            ps.setInt(index, producto.getId()); // parámetro 5 => ?
            rows = ps.executeUpdate();
            System.out.println("Registros actualizados: " + rows);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
        return rows;
    }

    /**
     * Método que elimina un registro (DELETE)
     *
     * @return int Número de registros afectados
     */
    @Override
    public int delete(ProductoDTO producto) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        int rows = 0;
        try {
            if (this.userConn != null) {
                conn = this.userConn;
            } else {
                conn = DriverManager.getConnection(HOST);
                this.userConn = conn;
            }
            System.out.println("\nEJECUTANDO QUERY: " + SQL_DELETE);
            ps = conn.prepareStatement(SQL_DELETE);
            ps.setInt(1, producto.getId());
            rows = ps.executeUpdate();
            System.out.println("Registros eliminados: " + rows);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
        return rows;
    }
}
