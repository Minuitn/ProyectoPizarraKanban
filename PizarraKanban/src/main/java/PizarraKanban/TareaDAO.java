/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package PizarraKanban;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TareaDAO {
    
TareaDAO dao = new TareaDAO();

    // Cargar tareas por estado en una JTable
    public void cargarPorEstado(String estado, JTable tabla) {
        String sql = "SELECT * FROM tareas WHERE estado = ?";
        ConexionDB conexionDB = new ConexionDB();
        Connection conn = conexionDB.conectar();

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0); // limpia la tabla

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, estado);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[5];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getString("descripcion");
                fila[2] = rs.getString("estado");
                fila[3] = rs.getString("prioridad");
                fila[4] = rs.getString("responsable");
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            System.out.println("Error Consulta: " + e.toString());
        } finally {
            conexionDB.desconectar();
        }
    }

    // Agregar tarea nueva
    public boolean agregar(String descripcion, String prioridad, String responsable) {
        String sql = "INSERT INTO tareas (descripcion, estado, prioridad, responsable) VALUES (?, 'POR_HACER', ?, ?)";

        ConexionDB conexionDB = new ConexionDB();
        Connection conn = conexionDB.conectar();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, descripcion);
            ps.setString(2, prioridad);
            ps.setString(3, responsable);

            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.out.println("Error SQL al agregar: " + e.toString());
            return false;
        } finally {
            conexionDB.desconectar();
        }
    }

    // Cambiar estado de una tarea
    public boolean actualizarEstado(int id, String nuevoEstado) {
        String sql = "UPDATE tareas SET estado = ? WHERE id = ?";

        ConexionDB conexionDB = new ConexionDB();
        Connection conn = conexionDB.conectar();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, id);

            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.out.println("Error SQL al actualizar estado: " + e.toString());
            return false;
        } finally {
            conexionDB.desconectar();
        }
    }

    // Eliminar tarea
    public boolean eliminar(int id) {
        String sql = "DELETE FROM tareas WHERE id = ?";

        ConexionDB conexionDB = new ConexionDB();
        Connection conn = conexionDB.conectar();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.out.println("Error SQL al eliminar: " + e.toString());
            return false;
        } finally {
            conexionDB.desconectar();
        }
    }
}
