package PizarraKanban;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ventana de selección de proyecto.
 * Representa el proyecto activo del sistema.
 */
public class ProyectoView extends JFrame {

    private JList<String> listaProyectos;
    private DefaultListModel<String> modeloLista;
    private JButton btnEntrar;
    private Usuario usuario;

    public ProyectoView(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Selección de Proyecto");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inicializarComponentes();
    }

    private void inicializarComponentes() {

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Proyectos Disponibles", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));

        modeloLista = new DefaultListModel<>();
        modeloLista.addElement("Proyecto Kanban - Equipo de Desarrollo");

        listaProyectos = new JList<>(modeloLista);
        listaProyectos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        listaProyectos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaProyectos.setSelectedIndex(0);

        JScrollPane scroll = new JScrollPane(listaProyectos);

        btnEntrar = new JButton("Entrar al Proyecto");
        btnEntrar.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirProyecto();
            }
        });

        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        panelPrincipal.add(scroll, BorderLayout.CENTER);
        panelPrincipal.add(btnEntrar, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private void abrirProyecto() {

        if (listaProyectos.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un proyecto.");
            return;
        }

        TareaView vistaTareas = new TareaView(usuario);
        TareaController controller = new TareaController(vistaTareas, usuario);
        vistaTareas.setVisible(true);

        dispose();
    }
}


