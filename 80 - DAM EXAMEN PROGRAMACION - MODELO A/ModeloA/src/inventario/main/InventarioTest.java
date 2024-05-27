package inventario.main;

import inventario.jdbc.ProductoDAO;
import inventario.jdbc.ProductoDAOImp;
import inventario.jdbc.ProductoDTO;
import java.util.List;

public class InventarioTest {
    public static void main(String[] args) throws Exception {
        // Utilizamos el tipo interface como referencia a una clase concreta
        ProductoDAO productoDao = new ProductoDAOImp();

        productoDao.ConectarCrearBaseDatos();

        productoDao.CrearTabla();

        productoDao.insertarLote();

        // Insertar un nuevo registro
        ProductoDTO producto = new ProductoDTO();
        producto.setNombre("Nombre Insertado");
        producto.setCategoria("Categoría insertada");
        producto.setPrecio(999.99f);
        producto.setCantidad(99);
        productoDao.insertar(producto);

        // Seleccionar todos los registros
        List<ProductoDTO> productosLista = productoDao.select();
        for (ProductoDTO productoSelect : productosLista) {
            System.out.print(productoSelect);
            System.out.println();
        }

        // Actualizar registro
        // ProductoDTO productoUpdate = new ProductoDTO();
        // productoUpdate.setId(1); // actualizamos el registro X
        // productoUpdate.setNombre("Nombre actualizado");
        // productoUpdate.setCategoria("Categoría actualizada");
        // productoUpdate.setPrecio(999.99f);
        // productoUpdate.setCantidad(99);
        // productoDao.update(productoUpdate);

        // Eliminar registro
        // productoDao.delete(new ProductoDTO(1));
    }
}
