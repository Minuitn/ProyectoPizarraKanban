
package PizarraKanban;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Conexion a la base de datos
public class ConexionDB {

    private Connection conn;

    private final String URL = "jdbc:mysql://localhost:3306/PizarraKanbanDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private final String USER = "kanban_user";
    private final String PASS = "Kanban123!";

    public Connection conectar() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("Conexión exitosa a MySQL.");
                System.out.println("Conectado a URL = " + URL);
            }
        } catch (SQLException e) {
            System.out.println("Error en la conexión: " + e.getMessage());
            conn = null;
        }
        return conn;
    }

    public void desconectar() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            System.out.println("Error al desconectar: " + e.getMessage());
        }
    }
}







