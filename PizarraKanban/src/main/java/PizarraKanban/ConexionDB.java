package PizarraKanban;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private String url = "jdbc:mysql://localhost:3306/";
    private String db = "PizarraKanbanDB";
    private String user = "root";
    private String password = "TU_PASSWORD";
    private String driver = "com.mysql.cj.jdbc.Driver";

    private Connection connection;  // ESTA LÍNEA ES LA QUE FALTABA

    public Connection conectar() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url + db, user, password);
            return connection;
        } catch (ClassNotFoundException ex) {
            System.out.println("Error class: " + ex.toString());
        } catch (SQLException ex) {
            System.out.println("Error SQL: " + ex.toString());
        }
        return null;
    }

    public void desconectar() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Error SQL: " + ex.toString());
            }
        }
    }
}


