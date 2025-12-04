
package PizarraKanban;

import javax.swing.*;
import java.awt.*;

// Ventana para crear usuario (admin)
public class RegistrarUsuarioView extends JFrame {

    public JTextField txtUsuario;
    public JPasswordField txtPassword;
    public JComboBox<String> cbRol;
    public JButton btnRegistrar;
    public JButton btnCancelar;

    public RegistrarUsuarioView() {
        setTitle("Registrar Usuario");
        setSize(360, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        JLabel header = new JLabel("Crear nuevo usuario", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 18));
        root.add(header);
        root.add(Box.createVerticalStrut(12));

        root.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        txtUsuario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        root.add(txtUsuario);
        root.add(Box.createVerticalStrut(10));

        root.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        root.add(txtPassword);
        root.add(Box.createVerticalStrut(10));

        root.add(new JLabel("Rol:"));
        cbRol = new JComboBox<>(new String[] { "ADMIN", "USUARIO" });
        cbRol.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        root.add(cbRol);
        root.add(Box.createVerticalStrut(18));

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnRegistrar = new JButton("Registrar");
        btnCancelar = new JButton("Cancelar");
        botones.add(btnRegistrar);
        botones.add(btnCancelar);

        add(root, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }
}




