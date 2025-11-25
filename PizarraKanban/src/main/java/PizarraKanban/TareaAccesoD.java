package PizarraKanban;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TareaAccesoD {

    // LISTAR TODAS LAS TAREAS
    public DefaultTableModel listar() {
        

        ConexionDB conexion = new ConexionDB();
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modelo.addColumn("ID");
        modelo.addColumn("Descripción");
        modelo.addColumn("Estado");
        modelo.addColumn("Prioridad");
        modelo.addColumn("Responsable");

        String datos[] = new String[5];

        try {
            Statement stmt = conexion.conectar().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tareas");

            while (rs.next()) {
                datos[0] = String.valueOf(rs.getInt("id"));
                datos[1] = rs.getString("descripcion");
                datos[2] = rs.getString("estado");
                datos[3] = rs.getString("prioridad");
                datos[4] = rs.getString("responsable");

                modelo.addRow(datos);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al consultar las tareas.");
            System.out.println("Error Consulta: " + ex.toString());
        } finally {
            conexion.desconectar();
        }

        return modelo;
    }

    // AGREGAR TAREA
    public void agregar(Tarea t) {

        ConexionDB conexion = new ConexionDB();

        String sql = "INSERT INTO tareas (descripcion, estado, prioridad, responsable) VALUES (?, ?, ?, ?)";

        try {
            CallableStatement cs = conexion.conectar().prepareCall(sql);

            cs.setString(1, t.getDescripcion());
            cs.setString(2, t.getEstado().toString());
            cs.setString(3, t.getPrioridad());
            cs.setString(4, t.getResponsable());

            cs.execute();

            JOptionPane.showMessageDialog(null, "Tarea agregada correctamente.");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al agregar la tarea.");
            System.out.println("Error SQL: " + ex.toString());
        } finally {
            conexion.desconectar();
        }
    }

    // EDITAR TAREA
    public void editar(Tarea t) {

        ConexionDB conexion = new ConexionDB();

        String sql = "UPDATE tareas SET descripcion = ?, estado = ?, prioridad = ?, responsable = ? WHERE id = ?";

        try {
            CallableStatement cs = conexion.conectar().prepareCall(sql);

            cs.setString(1, t.getDescripcion());
            cs.setString(2, t.getEstado().toString());
            cs.setString(3, t.getPrioridad());
            cs.setString(4, t.getResponsable());
            cs.setInt(5, t.getId());

            cs.execute();

            JOptionPane.showMessageDialog(null, "Tarea modificada correctamente.");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al modificar la tarea.");
            System.out.println("Error SQL: " + ex.toString());
        } finally {
            conexion.desconectar();
        }
    }

    // ELIMINAR TAREA
    public void eliminar(int id) {

        ConexionDB conexion = new ConexionDB();

        String sql = "DELETE FROM tareas WHERE id = ?";

        try {

            CallableStatement cs = conexion.conectar().prepareCall(sql);

            cs.setInt(1, id);

            cs.execute();

            JOptionPane.showMessageDialog(null, "Tarea eliminada correctamente.");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la tarea.");
            System.out.println("Error SQL: " + ex.toString());
        } finally {
            conexion.desconectar();
        }
    }
        // LISTAR TAREAS POR ESTADO (para cada columna de la pizarra)
    public DefaultTableModel listarPorEstado(Estado estado) {

        ConexionDB conexion = new ConexionDB();
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modelo.addColumn("ID");
        modelo.addColumn("Descripción");
        modelo.addColumn("Estado");
        modelo.addColumn("Prioridad");
        modelo.addColumn("Responsable");

        String datos[] = new String[5];

        String sql = "SELECT * FROM tareas WHERE estado = ?";

        try {
            CallableStatement cs = conexion.conectar().prepareCall(sql);
            cs.setString(1, estado.toString());
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                datos[0] = String.valueOf(rs.getInt("id"));
                datos[1] = rs.getString("descripcion");
                datos[2] = rs.getString("estado");
                datos[3] = rs.getString("prioridad");
                datos[4] = rs.getString("responsable");

                modelo.addRow(datos);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al consultar las tareas por estado.");
            System.out.println("Error Consulta: " + ex.toString());
        } finally {
            conexion.desconectar();
        }

        return modelo;
    }
        // ACTUALIZAR SOLO EL ESTADO DE UNA TAREA (mover entre columnas)
    public void actualizarEstado(int id, Estado nuevoEstado) {

        ConexionDB conexion = new ConexionDB();

        String sql = "UPDATE tareas SET estado = ? WHERE id = ?";

        try {
            CallableStatement cs = conexion.conectar().prepareCall(sql);
            cs.setString(1, nuevoEstado.toString());
            cs.setInt(2, id);

            cs.execute();

            JOptionPane.showMessageDialog(null, "Estado de la tarea actualizado.");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el estado de la tarea.");
            System.out.println("Error SQL: " + ex.toString());
        } finally {
            conexion.desconectar();
        }
    }


}

