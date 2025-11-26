
package PizarraKanban;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class TareaController implements ActionListener {

    private TareaView vista;
    private TareaAccesoD dao;

    public TareaController(TareaView vista) {
        this.vista = vista;
        this.dao = new TareaAccesoD();

        // Eventos de botones
        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);

        this.vista.btnMoverPorHacer.addActionListener(this);
        this.vista.btnMoverEnProgreso.addActionListener(this);
        this.vista.btnMoverCompletado.addActionListener(this);

        this.vista.btnEliminar.addActionListener(this);

        cargarTablas(); // carga inicial
    }

    // Cargar cada estado de la pizarra
    private void cargarTablas() {
        vista.tablaPorHacer.setModel(dao.listarPorEstado(Estado.POR_HACER));
        vista.tablaEnProgreso.setModel(dao.listarPorEstado(Estado.EN_PROGRESO));
        vista.tablaCompletado.setModel(dao.listarPorEstado(Estado.COMPLETADO));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // AGREGAR
        if (e.getSource() == vista.btnAgregar) {

            try {
                String desc = JOptionPane.showInputDialog("Descripción:");
                String prio = JOptionPane.showInputDialog("Prioridad:");
                String resp = JOptionPane.showInputDialog("Responsable:");

                if (desc == null || desc.trim().isEmpty() ||
                    prio == null || prio.trim().isEmpty() ||
                    resp == null || resp.trim().isEmpty()) {

                    JOptionPane.showMessageDialog(null, "Debes completar todos los campos.");
                    return;
                }

                Tarea t = new Tarea(0, desc, Estado.POR_HACER, prio, resp);
                dao.agregar(t);
                cargarTablas();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Ocurrió un error al agregar la tarea.");
                System.out.println("Error: " + ex.toString());
            }
        }

        // MOVER POR HACER
        if (e.getSource() == vista.btnMoverPorHacer) {
            moverTareaAEstado(Estado.POR_HACER);
        }

        // MOVER EN PROGRESO
        if (e.getSource() == vista.btnMoverEnProgreso) {
            moverTareaAEstado(Estado.EN_PROGRESO);
        }

        // MOVER COMPLETADO
        if (e.getSource() == vista.btnMoverCompletado) {
            moverTareaAEstado(Estado.COMPLETADO);
        }

        // ACTUALIZAR
        if (e.getSource() == vista.btnActualizar) {
            cargarTablas();
        }

        // ELIMINAR
        if (e.getSource() == vista.btnEliminar) {
            eliminarTareaSeleccionada();
        }
    }

    // Cambiar estado de una tarea
    private void moverTareaAEstado(Estado nuevoEstado) {
        try {
            JTable tablaSeleccionada = null;
            int fila = -1;

            if (vista.tablaPorHacer.getSelectedRow() != -1) {
                tablaSeleccionada = vista.tablaPorHacer;
                fila = vista.tablaPorHacer.getSelectedRow();
            } else if (vista.tablaEnProgreso.getSelectedRow() != -1) {
                tablaSeleccionada = vista.tablaEnProgreso;
                fila = vista.tablaEnProgreso.getSelectedRow();
            } else if (vista.tablaCompletado.getSelectedRow() != -1) {
                tablaSeleccionada = vista.tablaCompletado;
                fila = vista.tablaCompletado.getSelectedRow();
            }

            if (tablaSeleccionada == null || fila == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una tarea.");
                return;
            }

            int id = Integer.parseInt(
                tablaSeleccionada.getValueAt(fila, 0).toString()
            );

            dao.actualizarEstado(id, nuevoEstado);
            cargarTablas();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al mover la tarea.");
            System.out.println("Error mover estado: " + ex.toString());
        }
    }

    // Eliminar tarea con confirmación
    private void eliminarTareaSeleccionada() {
        try {
            JTable tablaSeleccionada = null;
            int fila = -1;

            if (vista.tablaPorHacer.getSelectedRow() != -1) {
                tablaSeleccionada = vista.tablaPorHacer;
                fila = vista.tablaPorHacer.getSelectedRow();
            } else if (vista.tablaEnProgreso.getSelectedRow() != -1) {
                tablaSeleccionada = vista.tablaEnProgreso;
                fila = vista.tablaEnProgreso.getSelectedRow();
            } else if (vista.tablaCompletado.getSelectedRow() != -1) {
                tablaSeleccionada = vista.tablaCompletado;
                fila = vista.tablaCompletado.getSelectedRow();
            }

            if (tablaSeleccionada == null || fila == -1) {
                JOptionPane.showMessageDialog(null, "Selecciona una tarea para eliminar.");
                return;
            }

            int id = Integer.parseInt(
                tablaSeleccionada.getValueAt(fila, 0).toString()
            );

            int confirm = JOptionPane.showConfirmDialog(
                null,
                "¿Seguro que deseas eliminar esta tarea?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                dao.eliminar(id);
                cargarTablas();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la tarea.");
            System.out.println("Error: " + ex.toString());
        }
    }
}

