
package PizarraKanban;

import PizarraKanban.ConexioDB.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author minu
 */

public class TareaController {

    public boolean guardarTarea(Tarea tarea) {
        String sql = "INSERT INTO tareas (descripcion, estado, prioridad, responsable) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tarea.getDescripcion());
            ps.setString(2, tarea.getEstado().name());
            ps.setString(3, tarea.getPrioridad());
            ps.setString(4, tarea.getResponsable());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

