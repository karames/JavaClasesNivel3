package test;

import datos.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class MetadatosJDBC {
    public static void main(String[] args) throws Exception {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSetMetaData rsMetaData = null;

        try {
            conn = Conexion.conectarBD();
            pstmt = conn.prepareStatement("SELECT * FROM personas");
            rs = pstmt.executeQuery();
            rsMetaData = rs.getMetaData();

            if (rsMetaData == null) {
                System.out.println("Metadatos no disponibles");
            } else {
                // Obtener nombre de la tabla y número de columnas de la tabla personas
                System.out.println("");
                System.out.println("Nombre de la tabla: " + rsMetaData.getTableName(1));
                int columnCount = rsMetaData.getColumnCount();
                System.out.println("Número de columnas: " + columnCount);

                // Obtener propiedades de las columnas
                for (int i = 1; i <= columnCount; i++) {
                    // Obtener nombres de las columnas y tipos de datos
                    System.out.print("Nombre columna " + i + ": " + rsMetaData.getColumnName(i));
                    System.out.print(" - Tipo de dato: " + rsMetaData.getColumnTypeName(i));
                    System.out.print(" - Longitud: " + rsMetaData.getColumnDisplaySize(i));

                    // Indicar si la columna puede almacenar valores nulos
                    switch (rsMetaData.isNullable(i)) {
                        case ResultSetMetaData.columnNoNulls:
                            System.out.print(" - NULL: no acepta nulos");
                            break;
                        case ResultSetMetaData.columnNullable:
                            System.out.print(" - NULL: sí acepta nulos");
                            break;
                        case ResultSetMetaData.columnNullableUnknown:
                            System.out.print(" - NULL: valor nulo desconocido");
                    }

                    // Indicar si la columna tiene signo
                    if (rsMetaData.isSigned(i)) {
                        System.out.print(" - SIN SIGNO: sí");
                    } else {
                        System.out.print(" - SIN SIGNO: no");
                    }
                    System.out.println("");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(pstmt);
            Conexion.close(conn);
        }
    }
}
