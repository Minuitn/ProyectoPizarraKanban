package PizarraKanban;

import javax.swing.*;
import java.awt.*;

public class TareaView extends JFrame {

    public JTable tablaPorHacer;
    public JTable tablaEnProgreso;
    public JTable tablaCompletado;

    public JButton btnAgregar;
    public JButton btnActualizar;

    public TareaView() {
        setTitle("Pizarra Kanban");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel();
        btnAgregar = new JButton("Agregar tarea");
        btnActualizar = new JButton("Actualizar");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);

        JPanel panelTablas = new JPanel(new GridLayout(1, 3));

        tablaPorHacer = new JTable();
        tablaEnProgreso = new JTable();
        tablaCompletado = new JTable();

        panelTablas.add(new JScrollPane(tablaPorHacer));
        panelTablas.add(new JScrollPane(tablaEnProgreso));
        panelTablas.add(new JScrollPane(tablaCompletado));

        add(panelBotones, BorderLayout.NORTH);
        add(panelTablas, BorderLayout.CENTER);
    }
}

