import datos.PersonasJDBC;
import domain.Persona;
import java.util.List;

public class ManejoJDBC {

    public static void main(String[] args) throws Exception {
        PersonasJDBC personasJDBC = new PersonasJDBC();
        // Prueba del método insert
        // personasJDBC.insert("Alberto", "Juarez");

        // Prueba del método update
        // personasJDBC.update(2, "Nombre3", "Apellido3");

        // Prueba del método delete
        // personasJDBC.delete(1);

        // Prueba del método select
        // Uso de un objeto persona para encapsular la información
        // de un registro de base de datos
        List<Persona> personas = personasJDBC.select();
        for (Persona persona : personas) {
            System.out.print(persona);
            System.out.println("");
        }
    }

}
