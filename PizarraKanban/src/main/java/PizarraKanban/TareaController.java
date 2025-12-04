package PizarraKanban;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Controlador de la vista de tareas unificado con DAO corregido
public class TareaController implements ActionListener {

    private TareaView vista;
    private TareaAccesoD dao;
    private Usuario usuario;

    public TareaController(TareaView vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;
        this.dao = new TareaAccesoD();

        // listeners
        vista.btnAgregar.addActionListener(this);
        vista.btnActualizar.addActionListener(this);
        vista.btnEditar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnMoverPorHacer.addActionListener(this);
        vista.btnMoverEnProgreso.addActionListener(this);
        vista.btnMoverCompletado.addActionListener(this);
        vista.btnCrearUsuario.addActionListener(this);

        cargarTablas();

        // permisos en UI (sólo visual, también se validan en acciones)
        if (!"ADMIN".equalsIgnoreCase(usuario.getRol())) {
            vista.btnCrearUsuario.setEnabled(false);
            vista.btnEliminar.setEnabled(false);
        }
    }

    private void cargarTablas() {
        vista.tablaPorHacer.setModel(dao.listarPorEstado("POR_HACER"));
        vista.tablaEnProgreso.setModel(dao.listarPorEstado("EN_PROGRESO"));
        vista.tablaCompletado.setModel(dao.listarPorEstado("COMPLETADO"));
    }

    private int obtenerIdSeleccionado(JTable tabla) {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return -1;
        return Integer.parseInt(tabla.getValueAt(fila, 0).toString());
    }

    private JTable detectarTablaSeleccionada() {
        if (vista.tablaPorHacer.getSelectedRow() != -1) return vista.tablaPorHacer;
        if (vista.tablaEnProgreso.getSelectedRow() != -1) return vista.tablaEnProgreso;
        if (vista.tablaCompletado.getSelectedRow() != -1) return vista.tablaCompletado;
        JOptionPane.showMessageDialog(vista, "Debe seleccionar una tarea.");
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // abrir registro usuario (solo admin)
        if (e.getSource() == vista.btnCrearUsuario) {
            if (!"ADMIN".equalsIgnoreCase(usuario.getRol())) {
                JOptionPane.showMessageDialog(vista, "No tiene permisos.");
                return;
            }
            RegistrarUsuarioView regV = new RegistrarUsuarioView();
            RegistrarUsuarioController regC = new RegistrarUsuarioController(regV);
            regV.setVisible(true);
            return;
        }

        // agregar tarea
        if (e.getSource() == vista.btnAgregar) {
            // permiso: si no es admin, no crear (según reglas del proyecto)
            if (!"ADMIN".equalsIgnoreCase(usuario.getRol())) {
                JOptionPane.showMessageDialog(vista, "Solo ADMIN puede crear tareas.");
                return;
            }

            FormularioTareaView form = new FormularioTareaView(vista);
            form.btnGuardar.addActionListener(ev -> {
                String desc = form.txtDescripcion.getText().trim();
                if (desc.isEmpty()) {
                    JOptionPane.showMessageDialog(form, "Descripción requerida.");
                    return;
                }
                String prioridad = (String) form.cbPrioridad.getSelectedItem();
                String responsable = (String) form.cbResponsable.getSelectedItem();

                Tarea t = new Tarea(0, desc, "POR_HACER", prioridad, responsable);
                dao.agregar(t);
                form.dispose();
                cargarTablas();
            });
            form.btnCancelar.addActionListener(ev -> form.dispose());
            form.setVisible(true);
            return;
        }

        // editar tarea
        if (e.getSource() == vista.btnEditar) {
            JTable tabla = detectarTablaSeleccionada();
            if (tabla == null) return;

            int id = obtenerIdSeleccionado(tabla);
            if (id == -1) return;

            Tarea t = dao.obtenerPorId(id);
            if (t == null) { JOptionPane.showMessageDialog(vista, "Tarea no encontrada."); return; }

            // permisos: admin o responsable
            if (!"ADMIN".equalsIgnoreCase(usuario.getRol()) &&
                !usuario.getUsername().equalsIgnoreCase(t.getResponsable())) {
                JOptionPane.showMessageDialog(vista, "Solo puede editar sus propias tareas.");
                return;
            }

            FormularioTareaView form = new FormularioTareaView(vista);
            form.txtDescripcion.setText(t.getDescripcion());
            form.cbPrioridad.setSelectedItem(t.getPrioridad());
            form.cbResponsable.setSelectedItem(t.getResponsable());

            form.btnGuardar.addActionListener(ev -> {
                t.setDescripcion(form.txtDescripcion.getText().trim());
                t.setPrioridad((String) form.cbPrioridad.getSelectedItem());
                // solo admin puede reasignar responsable
                if ("ADMIN".equalsIgnoreCase(usuario.getRol())) {
                    t.setResponsable((String) form.cbResponsable.getSelectedItem());
                }
                dao.editar(t);
                form.dispose();
                cargarTablas();
            });

            form.btnCancelar.addActionListener(ev -> form.dispose());
            form.setVisible(true);
            return;
        }

        // eliminar tarea (solo admin)
        if (e.getSource() == vista.btnEliminar) {
            if (!"ADMIN".equalsIgnoreCase(usuario.getRol())) {
                JOptionPane.showMessageDialog(vista, "Solo administrador puede eliminar tareas.");
                return;
            }
            JTable tabla = detectarTablaSeleccionada();
            if (tabla == null) return;
            int id = obtenerIdSeleccionado(tabla);
            if (id == -1) return;
            int conf = JOptionPane.showConfirmDialog(vista, "¿Eliminar tarea?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                dao.eliminar(id);
                cargarTablas();
            }
            return;
        }

        // actualizar vista
        if (e.getSource() == vista.btnActualizar) {
            cargarTablas();
            return;
        }

        // mover tareas entre estados (solo admin o responsable)
        if (e.getSource() == vista.btnMoverPorHacer ||
            e.getSource() == vista.btnMoverEnProgreso ||
            e.getSource() == vista.btnMoverCompletado) {

            JTable tabla = detectarTablaSeleccionada();
            if (tabla == null) return;
            int id = obtenerIdSeleccionado(tabla);
            if (id == -1) return;

            Tarea t = dao.obtenerPorId(id);
            if (t == null) { JOptionPane.showMessageDialog(vista, "Tarea no encontrada."); return; }

            // permiso
            if (!"ADMIN".equalsIgnoreCase(usuario.getRol()) &&
                !usuario.getUsername().equalsIgnoreCase(t.getResponsable())) {
                JOptionPane.showMessageDialog(vista, "No puede mover una tarea que no es suya.");
                return;
            }

            String nuevoEstado = "POR_HACER";
            if (e.getSource() == vista.btnMoverEnProgreso) nuevoEstado = "EN_PROGRESO";
            if (e.getSource() == vista.btnMoverCompletado) nuevoEstado = "COMPLETADO";

            dao.actualizarEstado(id, nuevoEstado);
            cargarTablas();
        }
    }
}

