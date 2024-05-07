import datos.PersonasJDBC;
import domain.Persona;
import java.util.List;

public class ManejoPersonas {
    public static void main(String[] args) throws Exception {
        PersonasJDBC personasJDBC = new PersonasJDBC();

        // Prueba del método SELECT
        // Uso objeto persona para encapsular la información del registro de la BD
        List<Persona> personasLista = personasJDBC.select();
        for (Persona persona : personasLista) {
            System.out.print(persona);
            System.out.println("");
        }
        System.out.println("Registros seleccionados: " + personasLista.size());

        // Prueba del método INSERT
        // personasJDBC.insert("Pedro", "Sánchez");

        // Prueba del método UPDATE
        // personasJDBC.update(X, "Nombre3", "Apellido3");

        // Prueba del método DELETE
        // personasJDBC.delete(X);
    }
}
