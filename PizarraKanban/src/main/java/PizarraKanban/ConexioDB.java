
package PizarraKanban;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author minu
 */
public class ConexioDB {

public class ConexionDB {
    private static final String URL = "jdbc:mysql:// ######### ";
    private static final String USER = "usuario";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

}
