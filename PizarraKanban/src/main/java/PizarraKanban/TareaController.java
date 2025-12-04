
package PizarraKanban;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Controlador de la vista de tareas
public class TareaController implements ActionListener {

    private TareaView vista;
    private TareaAccesoD dao;
    private Usuario usuario;
    private UsuarioDAO daoUsuario = new UsuarioDAO();

    public TareaController(TareaView vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;
        this.dao = new TareaAccesoD();

        vista.btnAgregar.addActionListener(this);
        vista.btnActualizar.addActionListener(this);
        vista.btnEditar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnMoverPorHacer.addActionListener(this);
        vista.btnMoverEnProgreso.addActionListener(this);
        vista.btnMoverCompletado.addActionListener(this);
        vista.btnCrearUsuario.addActionListener(this);
        vista.btnEliminarUsuario.addActionListener(this); // NUEVO

        cargarTablas();
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

        // REGISTRAR USUARIO (ADMIN)
        if (e.getSource() == vista.btnCrearUsuario) {
            if (!usuario.getRol().equalsIgnoreCase("ADMIN")) {
                JOptionPane.showMessageDialog(vista, "No tiene permisos para registrar usuarios.");
                return;
            }
            RegistrarUsuarioView rv = new RegistrarUsuarioView();
            RegistrarUsuarioController rc = new RegistrarUsuarioController(rv);
            rv.setVisible(true);
            return;
        }

        // ELIMINAR USUARIO (ADMIN)
        if (e.getSource() == vista.btnEliminarUsuario) {

            if (!usuario.getRol().equalsIgnoreCase("ADMIN")) {
                JOptionPane.showMessageDialog(vista, "Solo el administrador puede eliminar usuarios.");
                return;
            }

            String idStr = JOptionPane.showInputDialog(vista, "Ingrese el ID del usuario a eliminar:");

            if (idStr == null || idStr.trim().isEmpty()) return;

            try {
                int id = Integer.parseInt(idStr);

                int conf = JOptionPane.showConfirmDialog(
                        vista,
                        "¿Eliminar usuario con ID: " + id + "?",
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION);

                if (conf == JOptionPane.YES_OPTION) {
                    boolean ok = daoUsuario.eliminarUsuario(id);

                    if (ok) JOptionPane.showMessageDialog(vista, "Usuario eliminado.");
                    else JOptionPane.showMessageDialog(vista, "No se pudo eliminar el usuario.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "ID inválido.");
            }
            return;
        }

        // AGREGAR TAREA
        if (e.getSource() == vista.btnAgregar) {
            if (!usuario.getRol().equalsIgnoreCase("ADMIN")) {
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

                String prioridad = form.cbPrioridad.getSelectedItem().toString();
                String responsable = form.cbResponsable.getSelectedItem().toString();

                Tarea t = new Tarea(0, desc, "POR_HACER", prioridad, responsable);
                dao.agregar(t);

                form.dispose();
                cargarTablas();
            });

            form.btnCancelar.addActionListener(ev -> form.dispose());
            form.setVisible(true);
            return;
        }

        // EDITAR TAREA
        if (e.getSource() == vista.btnEditar) {
            JTable tabla = detectarTablaSeleccionada();
            if (tabla == null) return;

            int id = obtenerIdSeleccionado(tabla);
            if (id == -1) return;

            Tarea t = dao.obtenerPorId(id);
            if (t == null) return;

            if (!usuario.getRol().equalsIgnoreCase("ADMIN") &&
                !usuario.getUsername().equalsIgnoreCase(t.getResponsable())) {
                JOptionPane.showMessageDialog(vista, "Solo puede editar sus propias tareas.");
                return;
            }

            FormularioTareaView form = new FormularioTareaView(vista);
            form.txtDescripcion.setText(t.getDescripcion());
            form.cbPrioridad.setSelectedItem(t.getPrioridad());
            form.cbResponsable.setSelectedItem(t.getResponsable());

            form.btnGuardar.addActionListener(ev -> {
                t.setDescripcion(form.txtDescripcion.getText());
                t.setPrioridad(form.cbPrioridad.getSelectedItem().toString());
                if (usuario.getRol().equalsIgnoreCase("ADMIN")) {
                    t.setResponsable(form.cbResponsable.getSelectedItem().toString());
                }
                dao.editar(t);
                form.dispose();
                cargarTablas();
            });

            form.btnCancelar.addActionListener(ev -> form.dispose());
            form.setVisible(true);
            return;
        }

        // ELIMINAR TAREA
        if (e.getSource() == vista.btnEliminar) {
            if (!usuario.getRol().equalsIgnoreCase("ADMIN")) {
                JOptionPane.showMessageDialog(vista, "Solo administrador puede eliminar tareas.");
                return;
            }
            JTable tabla = detectarTablaSeleccionada();
            if (tabla == null) return;
            int id = obtenerIdSeleccionado(tabla);

            int conf = JOptionPane.showConfirmDialog(vista, "¿Eliminar tarea?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                dao.eliminar(id);
                cargarTablas();
            }
            return;
        }

        // ACTUALIZAR
        if (e.getSource() == vista.btnActualizar) {
            cargarTablas();
            return;
        }

        // MOVER ENTRE ESTADOS
        if (e.getSource() == vista.btnMoverPorHacer ||
            e.getSource() == vista.btnMoverEnProgreso ||
            e.getSource() == vista.btnMoverCompletado) {

            JTable tabla = detectarTablaSeleccionada();
            if (tabla == null) return;

            int id = obtenerIdSeleccionado(tabla);
            if (id == -1) return;

            Tarea t = dao.obtenerPorId(id);
            if (t == null) return;

            if (!usuario.getRol().equalsIgnoreCase("ADMIN") &&
                !usuario.getUsername().equalsIgnoreCase(t.getResponsable())) {
                JOptionPane.showMessageDialog(vista, "No puede mover una tarea que no es suya.");
                return;
            }

            String estado = "POR_HACER";

            if (e.getSource() == vista.btnMoverEnProgreso) estado = "EN_PROGRESO";
            if (e.getSource() == vista.btnMoverCompletado) estado = "COMPLETADO";

            dao.actualizarEstado(id, estado);
            cargarTablas();
        }
    }
}


