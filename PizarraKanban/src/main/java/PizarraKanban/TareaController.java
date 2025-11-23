
package PizarraKanban;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class TareaController implements ActionListener {

    private TareaView vista;
    private TareaAccesoD dao;   

    public TareaController(TareaView vista) {
        this.vista = vista;
        this.dao = new TareaAccesoD();  // Crear instancia correctamente

        // Escuchar botones
        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);

        cargarTablas();
    }

    private void cargarTablas() {
        // De momento las 3 tablas muestran toda la lista (MVP)
        vista.tablaPorHacer.setModel(dao.listar());
        vista.tablaEnProgreso.setModel(dao.listar());
        vista.tablaCompletado.setModel(dao.listar());
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
        }

        // BOTÓN ACTUALIZAR
        if (e.getSource() == vista.btnActualizar) {
            cargarTablas();
        }
    }
}



