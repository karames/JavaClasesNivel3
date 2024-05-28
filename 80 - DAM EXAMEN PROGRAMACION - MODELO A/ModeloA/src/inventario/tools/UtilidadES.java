package inventario.tools;

import inventario.dto.ProductoDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;
import java.io.Writer;
import java.io.FileWriter;
import java.io.Reader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class UtilidadES {
    // Host de conexión
    private static final String HOST = "jdbc:sqlite:D:/01_Archivos_Programas/DB Browser for SQLite/data/inventario.db";

    // Declaraciones SQL
    private static final String SQL_SELECT = "SELECT id, nombre, categoria, precio, cantidad FROM productos ORDER BY id";
    private static final String SQL_INSERT_JSON = "INSERT INTO productos (nombre, categoria, precio, cantidad) VALUES(?, ?, ?, ?)";

    /**
     * Exportación tabla productos a json
     */
    public static void exportarJSON() throws SQLException, IOException {
        System.out.println("");
        System.out.println("EXPORTANDO TABLA PERSONAS CON FORMATO JSON'...");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ProductoDTO producto = null;
        List<ProductoDTO> productoLista = new ArrayList<ProductoDTO>();
        try {
            conn = DriverManager.getConnection(HOST);
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
                productoLista.add(producto);
            }
            Gson gson = new Gson();
            // String json = gson.toJson(productoLista);
            // System.out.println(json);
            try (Writer writer = new FileWriter("productos.json")) {
                gson.toJson(productoLista, writer);
                System.out.println("OK - Se ha realizado la exportación a productos.json");
            } catch (IOException ioe) {
                System.out.println("ERROR - NO se ha realizado la exportación a productos.json");
                System.out.println(ioe.getMessage());
                throw new RuntimeException(ioe);
            }
            System.out.println("OK - Se ha establecido conexión con la BD");
        } catch (SQLException sqle) {
            System.out.println("ERROR - NO se ha establecido conexión con la BD");
            System.out.println(sqle.getMessage());
            throw new RuntimeException(sqle);
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

    /**
     * Importación JSON
     */
    public static void importarJSON() throws IOException, SQLException {
        System.out.println("");
        System.out.println("IMPORTANDO TABLA PERSONAS CON FORMATO JSON'...");
        try (Reader reader = new FileReader("productos.json")) {
            Connection conn = null;
            PreparedStatement ps = null;
            int rows = 0; // registros afectados
            Gson gson = new Gson();
            Type productoListaTipo = new TypeToken<ArrayList<ProductoDTO>>() {
            }.getType();
            ArrayList<ProductoDTO> productoLista = gson.fromJson(reader, productoListaTipo);
            try {
                conn = DriverManager.getConnection(HOST);
                System.out.println("\nEJECUTANDO QUERY: " + SQL_INSERT_JSON);
                ps = conn.prepareStatement(SQL_INSERT_JSON);
                for (ProductoDTO producto : productoLista) {
                    System.out.println(producto);
                    int index = 1; // contador de parámetros (columnas)
                    ps.setString(index++, producto.getNombre()); // parámetro 1 => ?
                    ps.setString(index++, producto.getCategoria()); // parámetro 2 => ?
                    ps.setFloat(index++, producto.getPrecio()); // parámetro 3 => ?
                    ps.setInt(index, producto.getCantidad()); // parámetro 4 => ?
                    rows += ps.executeUpdate(); // número de registros afectados
                }
                System.out.println("OK - Se ha establecido conexión con la BD");
                System.out.println("Registros insertados: " + rows);
            } catch (SQLException sqle) {
                System.out.println("ERROR - NO se ha establecido conexión con la BD");
                System.out.println(sqle.getMessage());
                throw new RuntimeException(sqle);
            } finally {
                if (conn != null) {
                    conn.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
            System.out.println("OK - Se ha realizado la importación de productos.json");
        } catch (IOException ioe) {
            System.out.println("ERROR - NO se ha realizado la importación de productos.json");
            System.out.println(ioe.getMessage());
            throw new RuntimeException(ioe);
        }
    }
}
