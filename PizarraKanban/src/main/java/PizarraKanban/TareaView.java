/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PizarraKanban;
/**
 * @author shey
 */
import javax.swing.*;
import java.awt.*;

public class TareaView extends JFrame {

    public JTable tablaPorHacer;
    public JTable tablaEnProgreso;
    public JTable tablaCompletado;
    public JButton btnMoverPorHacer;
    public JButton btnMoverEnProgreso;
    public JButton btnMoverCompletado;
    public JButton btnAgregar;
    public JButton btnActualizar;
    public JButton btnEliminar;

    public TareaView() {
        setTitle("Pizarra Kanban");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel();

        btnAgregar = new JButton("Agregar tarea");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.NORTH);

        JPanel panelTablas = new JPanel(new GridLayout(1, 3));

        tablaPorHacer = new JTable();
        tablaEnProgreso = new JTable();
        tablaCompletado = new JTable();

        panelTablas.add(crearPanelColumna("Por hacer", tablaPorHacer));
        panelTablas.add(crearPanelColumna("En progreso", tablaEnProgreso));
        panelTablas.add(crearPanelColumna("Completado", tablaCompletado));

        add(panelTablas, BorderLayout.CENTER);

        btnMoverPorHacer = new JButton("Mover a POR HACER");
        btnMoverEnProgreso = new JButton("Mover a EN PROGRESO");
        btnMoverCompletado = new JButton("Mover a COMPLETADO");

        JPanel panelMover = new JPanel();
        panelMover.add(btnMoverPorHacer);
        panelMover.add(btnMoverEnProgreso);
        panelMover.add(btnMoverCompletado);

        add(panelMover, BorderLayout.SOUTH);
    }

    private JPanel crearPanelColumna(String titulo, JTable tabla) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel etiqueta = new JLabel(titulo, SwingConstants.CENTER);
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }
}
