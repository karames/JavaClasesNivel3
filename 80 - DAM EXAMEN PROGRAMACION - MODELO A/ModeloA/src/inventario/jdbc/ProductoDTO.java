package inventario.jdbc;

/**
 * Clase patrón de diseño DTO (Data Transfer Object)
 *
 * @author Nono
 */
public class ProductoDTO {

    // Atributos
    private int id;
    private String nombre;
    private String categoria;
    private float precio;
    private int cantidad;

    // Constructores
    public ProductoDTO() {
    }

    public ProductoDTO(int id) {
        this.id = id;
    }

    // Métodos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Producto [id=" + id + ", nombre=" + nombre + ", categoria=" + categoria + ", precio=" + precio
                + ", cantidad=" + cantidad + "]";
    }
}
