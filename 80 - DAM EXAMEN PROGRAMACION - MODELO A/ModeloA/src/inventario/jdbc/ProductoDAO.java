package inventario.jdbc;

import java.sql.SQLException;
import java.util.List;

import inventario.dto.ProductoDTO;

/**
 * Patrón de diseño DAO (Data Access Object)
 */
public interface ProductoDAO {

    public abstract void ConectarBaseDatos() throws SQLException;

    public abstract void CrearTabla() throws SQLException;

    public abstract int insertarLote() throws SQLException;

    public abstract int insertar(ProductoDTO producto) throws SQLException;

    public abstract List<ProductoDTO> select() throws SQLException;

    public abstract int update(ProductoDTO producto) throws SQLException;

    public abstract int delete(ProductoDTO producto) throws SQLException;

}
