package PizarraKanban;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    // URL base (con parámetros recomendados para MySQL 8)
    private String url = "jdbc:mysql://localhost:3306/pizarra_kanban?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    // Datos de conexión que creamos en MySQL
    private String user = "kanban_user";
    private String password = "Kanban123!";

    private String driver = "com.mysql.cj.jdbc.Driver";

    private Connection connection;

    public Connection conectar() {
        try {
            Class.forName(driver);
            // Ya no concatenamos db, va incluida en la URL
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a la base de datos.");
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
                System.out.println("Conexión cerrada.");
            } catch (SQLException ex) {
                System.out.println("Error SQL al cerrar: " + ex.toString());
            }
        }
    }
}



