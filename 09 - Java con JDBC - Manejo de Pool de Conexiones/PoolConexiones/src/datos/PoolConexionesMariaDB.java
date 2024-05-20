package datos;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

public class PoolConexionesMariaDB {

    public static DataSource getDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.mariadb.jdbc.Driver");
        ds.setUsername("root");
        ds.setPassword("");
        ds.setUrl("jdbc:mariadb://localhost:3306/sga-nono");
        // Definir tamaño pool de conexiones
        ds.setInitialSize(5);
        return ds;
    }

    public static Connection getConexion() throws SQLException {
        return getDataSource().getConnection();
    }
}
