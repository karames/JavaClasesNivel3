package personas.test;

import java.sql.SQLException;
import java.util.List;
import personas.dto.PersonaDTO;
import personas.jdbc.PersonaDAO;
import personas.jdbc.PersonaDAOJDBC;

public class PersonaCapaDatosTest {
    public static void main(String[] args) throws Exception {
        // Utilizamos el tipo interface como referencia a una clase concreta
        PersonaDAO personaDao = new PersonaDAOJDBC();

        // Utilizamos la capa DAO para persistir el objeto DTO
        try {
            // Seleccionar todos los registros
            List<PersonaDTO> personasLista = personaDao.select();
            for (PersonaDTO persona : personasLista) {
                System.out.print(persona);
                System.out.println();
            }

            // Crear un nuevo registro (insertar registro)
            // Hacemos uso de la clase PersonaDTO la cual se usa
            // para transferir la información entre las capas
            // No es necesario especificar la PK (tipo numérico autoincrementable),
            // la BD se encarga de asignarle un nuevo valor
            // PersonaDTO persona = new PersonaDTO();
            // persona.setNombre("Pedro");
            // persona.setApellido("Sánchez");
            // personaDao.insert(persona);

            // Actualizar registro
            // PersonaDTO personaTmp = new PersonaDTO();
            // personaTmp.setId(X); // actualizamos el registro X
            // personaTmp.setNombre("Nombre update");
            // personaTmp.setApellido("Apellido update");
            // personaDao.update(personaTmp);

            // Eliminar registro
            // personaDao.delete(new PersonaDTO(21));

        } catch (SQLException e) {
            System.out.println("Excepción en la capa de prueba");
            e.printStackTrace();
        }
    }
}
