package personas.jdbc;

import java.sql.SQLException;
import java.util.List;
import personas.dto.PersonaDTO;

/**
 * Patrón de diseño DAO (Data Access Object)
 * Esta interfaz contiene los métodos abstractos con las
 * operaciones básicas sobre la tabla 'personas'
 * CRUD (Create, Read, Update y Delete)
 * Se debe crear una clase concreta para implementar el
 * código asociado a cada método
 *
 * @author Nono
 */
public interface PersonaDAO {

    public abstract int insert(PersonaDTO persona) throws SQLException;

    public abstract int update(PersonaDTO persona) throws SQLException;

    public abstract int delete(PersonaDTO persona) throws SQLException;

    public abstract List<PersonaDTO> select() throws SQLException;

}
