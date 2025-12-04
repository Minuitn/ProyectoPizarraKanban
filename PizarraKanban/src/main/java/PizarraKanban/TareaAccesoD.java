package PizarraKanban;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

// Acceso a datos para tareas (CRUD)
public class TareaAccesoD {

    // listar por estado (estado como String)
    public DefaultTableModel listarPorEstado(String estado) {

        ConexionDB conexion = new ConexionDB();
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        modelo.addColumn("ID");
        modelo.addColumn("Descripción");
        modelo.addColumn("Estado");
        modelo.addColumn("Prioridad");
        modelo.addColumn("Responsable");

        String sql = "SELECT * FROM tareas WHERE estado = ? ORDER BY id ASC";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, estado);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[5];
                    fila[0] = rs.getInt("id");
                    fila[1] = rs.getString("descripcion");
                    fila[2] = rs.getString("estado");
                    fila[3] = rs.getString("prioridad");
                    fila[4] = rs.getString("responsable");
                    modelo.addRow(fila);
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al consultar las tareas por estado.");
            System.out.println("Error Consulta: " + ex.toString());
        } finally {
            conexion.desconectar();
        }

        return modelo;
    }

    // agregar tarea
    public void agregar(Tarea t) {

        ConexionDB conexion = new ConexionDB();
        String sql = "INSERT INTO tareas (descripcion, estado, prioridad, responsable) VALUES (?, ?, ?, ?)";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getDescripcion());
            ps.setString(2, t.getEstado());
            ps.setString(3, t.getPrioridad());
            ps.setString(4, t.getResponsable());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Tarea agregada correctamente.");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al agregar la tarea.");
            System.out.println("Error SQL: " + ex.toString());
        } finally {
            conexion.desconectar();
        }
    }

    // editar tarea (descripcion, prioridad, responsable) - no cambia estado aquí
    public void editar(Tarea t) {

        ConexionDB conexion = new ConexionDB();
        String sql = "UPDATE tareas SET descripcion = ?, prioridad = ?, responsable = ? WHERE id = ?";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getDescripcion());
            ps.setString(2, t.getPrioridad());
            ps.setString(3, t.getResponsable());
            ps.setInt(4, t.getId());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Tarea modificada correctamente.");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al modificar la tarea.");
            System.out.println("Error SQL: " + ex.toString());
        } finally {
            conexion.desconectar();
        }
    }

    // obtener tarea por id
    public Tarea obtenerPorId(int id) {
        ConexionDB conexion = new ConexionDB();
        String sql = "SELECT * FROM tareas WHERE id = ?";
        Tarea t = null;

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    t = new Tarea(
                        rs.getInt("id"),
                        rs.getString("descripcion"),
                        rs.getString("estado"),
                        rs.getString("prioridad"),
                        rs.getString("responsable")
                    );
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error obtenerPorId: " + ex.toString());
        } finally {
            conexion.desconectar();
        }

        return t;
    }

    // actualizar estado (mover entre columnas)
    public void actualizarEstado(int id, String nuevoEstado) {

        ConexionDB conexion = new ConexionDB();
        String sql = "UPDATE tareas SET estado = ? WHERE id = ?";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Estado de la tarea actualizado.");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el estado de la tarea.");
            System.out.println("Error SQL: " + ex.toString());
        } finally {
            conexion.desconectar();
        }
    }

    // eliminar tarea
    public void eliminar(int id) {

        ConexionDB conexion = new ConexionDB();
        String sql = "DELETE FROM tareas WHERE id = ?";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Tarea eliminada correctamente.");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la tarea.");
            System.out.println("Error SQL: " + ex.toString());
        } finally {
            conexion.desconectar();
        }
    }
}


