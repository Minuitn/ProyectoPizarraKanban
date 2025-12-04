package PizarraKanban;

import javax.swing.*;
import java.awt.*;

// Vista principal de la pizarra Kanban
public class TareaView extends JFrame {

    public JTable tablaPorHacer;
    public JTable tablaEnProgreso;
    public JTable tablaCompletado;

    public JButton btnMoverPorHacer;
    public JButton btnMoverEnProgreso;
    public JButton btnMoverCompletado;

    public JButton btnAgregar;
    public JButton btnActualizar;
    public JButton btnEditar;
    public JButton btnEliminar;
    public JButton btnCrearUsuario;

    private Usuario usuario;

    public TareaView(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Pizarra Kanban - Usuario: " + usuario.getUsername());
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));

        // PANEL SUPERIOR
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        btnAgregar = new JButton("Agregar tarea");
        btnActualizar = new JButton("Actualizar");
        btnEditar = new JButton("Editar tarea");
        btnEliminar = new JButton("Eliminar");
        btnCrearUsuario = new JButton("Crear usuario");

        panelTop.add(btnAgregar);
        panelTop.add(btnActualizar);
        panelTop.add(btnEditar);
        panelTop.add(btnEliminar);
        panelTop.add(btnCrearUsuario);

        add(panelTop, BorderLayout.NORTH);

        if (!"ADMIN".equalsIgnoreCase(usuario.getRol())) {
            btnCrearUsuario.setEnabled(false);
            btnEliminar.setEnabled(false);
        }

        // PANEL CENTRAL
        JPanel panelCenter = new JPanel(new GridLayout(1, 3, 12, 12));

        tablaPorHacer = new JTable();
        tablaEnProgreso = new JTable();
        tablaCompletado = new JTable();

        panelCenter.add(crearPanelColumna("Por hacer", tablaPorHacer, new Color(220, 240, 255)));
        panelCenter.add(crearPanelColumna("En progreso", tablaEnProgreso, new Color(255, 245, 204)));
        panelCenter.add(crearPanelColumna("Completado", tablaCompletado, new Color(220, 255, 220)));

        add(panelCenter, BorderLayout.CENTER);

        // PANEL INFERIOR
        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 14));
        btnMoverPorHacer = new JButton("Mover a POR HACER");
        btnMoverEnProgreso = new JButton("Mover a EN PROGRESO");
        btnMoverCompletado = new JButton("Mover a COMPLETADO");

        panelBottom.add(btnMoverPorHacer);
        panelBottom.add(btnMoverEnProgreso);
        panelBottom.add(btnMoverCompletado);

        add(panelBottom, BorderLayout.SOUTH);

        // Fondo general de ventana
        getContentPane().setBackground(new Color(245, 245, 245));

        // ESTILOS DE BOTONES
        estilizarBoton(btnAgregar, new Color(0, 122, 255));
        estilizarBoton(btnActualizar, new Color(0, 170, 0));
        estilizarBoton(btnEditar, new Color(255, 153, 0));
        estilizarBoton(btnEliminar, new Color(200, 0, 0));
        estilizarBoton(btnCrearUsuario, new Color(100, 100, 100));

        estilizarBoton(btnMoverPorHacer, new Color(0, 122, 255));
        estilizarBoton(btnMoverEnProgreso, new Color(255, 153, 0));
        estilizarBoton(btnMoverCompletado, new Color(0, 170, 0));

        // Render de prioridad
        aplicarRenderPorPrioridad(tablaPorHacer, 3);
        aplicarRenderPorPrioridad(tablaEnProgreso, 3);
        aplicarRenderPorPrioridad(tablaCompletado, 3);
    }

    // PANEL POR COLUMNA
    private JPanel crearPanelColumna(String titulo, JTable tabla, Color fondo) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(fondo);

        JLabel lbl = new JLabel(titulo, SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lbl.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        tabla.setRowHeight(28);

        p.add(lbl, BorderLayout.NORTH);
        p.add(new JScrollPane(tabla), BorderLayout.CENTER);
        p.setBorder(BorderFactory.createTitledBorder(titulo.toUpperCase()));

        return p;
    }

    // ESTILO DE BOTONES
    private void estilizarBoton(JButton btn, Color colorFondo) {
        btn.setBackground(colorFondo);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    // COLORES POR PRIORIDAD
    private void aplicarRenderPorPrioridad(JTable tabla, int col) {
        tabla.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                try {
                    Object val = table.getValueAt(row, col);
                    if (val != null) {
                        String prioridad = val.toString().toUpperCase();
                        switch (prioridad) {
                            case "ALTA":  c.setBackground(new Color(255, 204, 203)); break;
                            case "MEDIA": c.setBackground(new Color(255, 243, 205)); break;
                            case "BAJA":  c.setBackground(new Color(220, 255, 220)); break;
                            default:      c.setBackground(Color.WHITE);
                        }
                    }
                } catch (Exception ex) {
                    c.setBackground(Color.WHITE);
                }

                if (isSelected)
                    c.setBackground(c.getBackground().darker());

                return c;
            }
        });
    }
}
