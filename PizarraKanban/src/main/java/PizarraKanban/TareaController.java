
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

    this.vista.btnAgregar.addActionListener(this);
    this.vista.btnActualizar.addActionListener(this);

    this.vista.btnMoverPorHacer.addActionListener(this);
    this.vista.btnMoverEnProgreso.addActionListener(this);
    this.vista.btnMoverCompletado.addActionListener(this);

    this.vista.btnEliminar.addActionListener(this); // BOTÓN ELIMINAR

    cargarTablas();
            }

    private void cargarTablas() {
        // cada tabla solo muestra su estado
        vista.tablaPorHacer.setModel(dao.listarPorEstado(Estado.POR_HACER));
        vista.tablaEnProgreso.setModel(dao.listarPorEstado(Estado.EN_PROGRESO));
        vista.tablaCompletado.setModel(dao.listarPorEstado(Estado.COMPLETADO));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // BOTÓN AGREGAR
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

                Estado estado = Estado.POR_HACER;

                // Crear objeto tarea
                Tarea t = new Tarea(0, desc, estado, prio, resp);

                // Guardar en DB
                dao.agregar(t);

                // Refrescar tablas
                cargarTablas();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Ocurrió un error al agregar la tarea.");
                System.out.println("Error: " + ex.toString());
            }
            
        } // ️ AQUÍ cierra SOLO el if de AGREGAR

        // BOTÓN MOVER A POR_HACER
        if (e.getSource() == vista.btnMoverPorHacer) {
            moverTareaAEstado(Estado.POR_HACER);
        }

        // BOTÓN MOVER A EN_PROGRESO
        if (e.getSource() == vista.btnMoverEnProgreso) {
            moverTareaAEstado(Estado.EN_PROGRESO);
        }

        // BOTÓN MOVER A COMPLETADO
        if (e.getSource() == vista.btnMoverCompletado) {
            moverTareaAEstado(Estado.COMPLETADO);
        }

        // BOTÓN ACTUALIZAR
        if (e.getSource() == vista.btnActualizar) {
            cargarTablas();
        }
        //BOTÓN ELIMINAR
        if (e.getSource() == vista.btnEliminar) {
    eliminarTareaSeleccionada();
}

    }

    private void moverTareaAEstado(Estado nuevoEstado) {

        try {
            int filaSeleccionada = -1;
            JTable tablaSeleccionada = null;

            // Revisar en cuál tabla hay una fila seleccionada
            if (vista.tablaPorHacer.getSelectedRow() != -1) {
                tablaSeleccionada = vista.tablaPorHacer;
                filaSeleccionada = vista.tablaPorHacer.getSelectedRow();
            } else if (vista.tablaEnProgreso.getSelectedRow() != -1) {
                tablaSeleccionada = vista.tablaEnProgreso;
                filaSeleccionada = vista.tablaEnProgreso.getSelectedRow();
            } else if (vista.tablaCompletado.getSelectedRow() != -1) {
                tablaSeleccionada = vista.tablaCompletado;
                filaSeleccionada = vista.tablaCompletado.getSelectedRow();
            }

            if (tablaSeleccionada == null || filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una tarea en alguna tabla.");
                return;
            }

            // La columna 0 es el ID (según el modelo que pusimos en el DAO)
            int id = Integer.parseInt(tablaSeleccionada.getValueAt(filaSeleccionada, 0).toString());

            // Actualizar en BD
            dao.actualizarEstado(id, nuevoEstado);

            // Recargar tablas
            cargarTablas();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al mover la tarea de estado.");
            System.out.println("Error mover estado: " + ex.toString());
        }
    }
    private void eliminarTareaSeleccionada() {
    try {
        int filaSeleccionada = -1;
        JTable tablaSeleccionada = null;

        // Buscar en cuál tabla está seleccionada la tarea
        if (vista.tablaPorHacer.getSelectedRow() != -1) {
            tablaSeleccionada = vista.tablaPorHacer;
            filaSeleccionada = vista.tablaPorHacer.getSelectedRow();
        } else if (vista.tablaEnProgreso.getSelectedRow() != -1) {
            tablaSeleccionada = vista.tablaEnProgreso;
            filaSeleccionada = vista.tablaEnProgreso.getSelectedRow();
        } else if (vista.tablaCompletado.getSelectedRow() != -1) {
            tablaSeleccionada = vista.tablaCompletado;
            filaSeleccionada = vista.tablaCompletado.getSelectedRow();
        }

        if (tablaSeleccionada == null || filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona una tarea para eliminar.");
            return;
        }

        // Obtener id de la tarea (columna 0)
        int id = Integer.parseInt(
                tablaSeleccionada.getValueAt(filaSeleccionada, 0).toString()
        );

        // Confirmación
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
