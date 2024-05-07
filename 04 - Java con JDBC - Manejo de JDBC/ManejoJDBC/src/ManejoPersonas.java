import datos.PersonasJDBC;
import domain.Persona;
import java.util.List;

public class ManejoPersonas {
    public static void main(String[] args) throws Exception {
        PersonasJDBC personasJDBC = new PersonasJDBC();

        // Prueba del método SELECT
        // Uso de un objeto persona para encapsular la información
        // de un registro de base de datos
        System.out.println("");
        List<Persona> personasLista = personasJDBC.select();
        for (Persona persona : personasLista) {
            System.out.print(persona);
            System.out.println("");
        }

        // Prueba del método insert
        // personasJDBC.insert("Pedro", "Sánchez");

        // Prueba del método update
        // personasJDBC.update(8, "Nombre3", "Apellido3");

        // Prueba del método delete
        // personasJDBC.delete(8);
    }
}
