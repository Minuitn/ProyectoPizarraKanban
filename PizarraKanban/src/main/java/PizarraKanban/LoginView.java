package PizarraKanban;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    public JTextField txtUsuario;
    public JPasswordField txtPassword;
    public JButton btnLogin;
    public JButton btnAgregarUsuario; 

    public LoginView() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}

        setTitle("Login - Pizarra Kanban");
        setSize(380, 400); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(40, 40));
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel title = new JLabel("Pizarra Kanban", JLabel.CENTER);
        title.setFont(title.getFont().deriveFont(24f));
        centerPanel.add(title);
        centerPanel.add(Box.createVerticalStrut(20));

        centerPanel.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        styleField(txtUsuario);
        centerPanel.add(txtUsuario);
        centerPanel.add(Box.createVerticalStrut(15));

        centerPanel.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        styleField(txtPassword);
        centerPanel.add(txtPassword);
        centerPanel.add(Box.createVerticalStrut(25));

        btnLogin = new JButton("Ingresar");
        styleButton(btnLogin);
        centerPanel.add(btnLogin);
        centerPanel.add(Box.createVerticalStrut(10)); // espaciado entre botones

        // Botón Agregar Usuario
        btnAgregarUsuario = new JButton("Agregar Usuario");
        styleButtonSecondary(btnAgregarUsuario);
        centerPanel.add(btnAgregarUsuario);

        add(centerPanel, BorderLayout.CENTER);
    }

    private void styleField(JTextField field) {
        field.setFont(new Font("Segoe UI", 0, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)));
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", 1, 14));
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    private void styleButtonSecondary(JButton button) {
        button.setFont(new Font("Segoe UI", 1, 14));
        button.setBackground(new Color(108, 117, 125)); // color gris azulado
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }
}
