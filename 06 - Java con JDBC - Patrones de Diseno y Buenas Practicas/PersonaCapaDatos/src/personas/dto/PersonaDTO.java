package personas.dto;

/**
 * Clase patrón de diseño DTO (Data Transfer Object)
 *
 * @author Nono
 */
public class PersonaDTO {

    // Atributos
    private int id;
    private String nombre;
    private String apellido;

    // Constructores
    public PersonaDTO() {
    }

    public PersonaDTO(int id) {
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return "Persona [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + "]";
    }

}
