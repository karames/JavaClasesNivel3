package inventario.main;

import inventario.dto.ProductoDTO;
import inventario.jdbc.ProductoDAO;
import inventario.jdbc.ProductoDAOImp;
import inventario.tools.UtilidadES;

import java.util.List;

public class InventarioTest {
    public static void main(String[] args) throws Exception {
        // Utilizamos el tipo interface como referencia a una clase concreta
        ProductoDAO productoDao = new ProductoDAOImp();

        // Conecta/Crea la base de datos
        productoDao.ConectarBaseDatos();

        // Crear tabla productos
        // productoDao.CrearTabla();

        // Insertar lote de registros
        // productoDao.insertarLote();

        // Insertar 1 registro
        // ProductoDTO producto = new ProductoDTO();
        // producto.setNombre("Nombre Insertado");
        // producto.setCategoria("Categoría insertada");
        // producto.setPrecio(999.99f);
        // producto.setCantidad(99);
        // productoDao.insertar(producto);

        // Listar todos los registros
        // List<ProductoDTO> productoLista = productoDao.select();
        // for (ProductoDTO productoItem : productoLista) {
        // System.out.print(productoItem);
        // System.out.println();
        // }

        // Actualizar 1 registro
        // ProductoDTO productoUpdate = new ProductoDTO();
        // productoUpdate.setId(11); // actualizamos el registro X
        // productoUpdate.setNombre("Nombre actualizado");
        // productoUpdate.setCategoria("Categoría actualizada");
        // productoUpdate.setPrecio(999.99f);
        // productoUpdate.setCantidad(99);
        // productoDao.update(productoUpdate);

        // Eliminar 1 registro
        // productoDao.delete(new ProductoDTO(1));

        // Exportar/Importar JSON
        // UtilidadES.exportarJSON();
        // UtilidadES.importarJSON();
    }
}
