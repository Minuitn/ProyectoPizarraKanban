package PizarraKanban;

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
    public JButton btnEliminar;   // NUEVO
    


    public TareaView() {
    setTitle("Pizarra Kanban");
    setSize(900, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    // PANEL SUPERIOR DE BOTONES
    JPanel panelBotones = new JPanel();

    btnAgregar = new JButton("Agregar tarea");
    btnActualizar = new JButton("Actualizar");
    btnEliminar = new JButton("Eliminar"); // NUEVO BOTÓN

    panelBotones.add(btnAgregar);
    panelBotones.add(btnActualizar);
    panelBotones.add(btnEliminar); // AGREGAMOS AL PANEL

    add(panelBotones, BorderLayout.NORTH);

    // PANEL DE TABLAS
    JPanel panelTablas = new JPanel(new GridLayout(1, 3));

    tablaPorHacer = new JTable();
    tablaEnProgreso = new JTable();
    tablaCompletado = new JTable();

    panelTablas.add(new JScrollPane(tablaPorHacer));
    panelTablas.add(new JScrollPane(tablaEnProgreso));
    panelTablas.add(new JScrollPane(tablaCompletado));

    add(panelTablas, BorderLayout.CENTER);

    // BOTONES PARA MOVER TAREAS
    btnMoverPorHacer = new JButton("Mover a POR HACER");
    btnMoverEnProgreso = new JButton("Mover a EN PROGRESO");
    btnMoverCompletado = new JButton("Mover a COMPLETADO");

    JPanel panelMover = new JPanel();
    panelMover.add(btnMoverPorHacer);
    panelMover.add(btnMoverEnProgreso);
    panelMover.add(btnMoverCompletado);

    add(panelMover, BorderLayout.SOUTH);
    btnEliminar = new JButton("Eliminar");
panelBotones.add(btnEliminar);

}

}

