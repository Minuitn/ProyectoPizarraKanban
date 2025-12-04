package PizarraKanban;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// Ventana para crear o editar una tarea
public class FormularioTareaView extends JDialog {

    public JTextField txtDescripcion;
    public JComboBox<String> cbPrioridad;
    public JComboBox<String> cbResponsable;
    public JButton btnGuardar;
    public JButton btnCancelar;

    public FormularioTareaView(JFrame parent) {
        super(parent, true);
        setTitle("Formulario de Tarea");
        setSize(360, 290);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));

        // descripcion
        root.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextField();
        txtDescripcion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        root.add(txtDescripcion);
        root.add(Box.createVerticalStrut(10));

        // prioridad
        root.add(new JLabel("Prioridad:"));
        cbPrioridad = new JComboBox<>(new String[]{"ALTA", "MEDIA", "BAJA"});
        cbPrioridad.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        root.add(cbPrioridad);
        root.add(Box.createVerticalStrut(10));

        // responsable (usuarios del sistema)
        root.add(new JLabel("Responsable:"));
        UsuarioDAO daoUser = new UsuarioDAO();
        List<String> lista = daoUser.obtenerUsernames();

        cbResponsable = new JComboBox<>(lista.toArray(new String[0]));
        cbResponsable.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        root.add(cbResponsable);
        root.add(Box.createVerticalStrut(12));

        // botones
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        botones.add(btnGuardar);
        botones.add(btnCancelar);

        add(root, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }
}




