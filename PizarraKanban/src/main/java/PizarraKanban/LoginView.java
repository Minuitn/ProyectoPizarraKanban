
package PizarraKanban;

import javax.swing.*;
import java.awt.*;

// Vista Login con inputs y labels centrados correctamente
public class LoginView extends JFrame {

    public JTextField txtUsuario;
    public JPasswordField txtPassword;
    public JButton btnLogin;

    public LoginView() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}

        setTitle("Login - Pizarra Kanban");
        setSize(380, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // GridBag para centrar todo exacto
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel title = new JLabel("Pizarra Kanban", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(title, gbc);

        // Label usuario
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Usuario:"), gbc);

        // Input usuario
        gbc.gridx = 1;
        txtUsuario = new JTextField();
        txtUsuario.setPreferredSize(new Dimension(160, 28));
        panel.add(txtUsuario, gbc);

        // Label contraseña
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Contraseña:"), gbc);

        // Input contraseña
        gbc.gridx = 1;
        txtPassword = new JPasswordField();
        txtPassword.setPreferredSize(new Dimension(160, 28));
        panel.add(txtPassword, gbc);

        // Botón login
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        btnLogin = new JButton("Ingresar");
        btnLogin.setBackground(new Color(0, 122, 204));
        btnLogin.setForeground(Color.WHITE);
        panel.add(btnLogin, gbc);

        add(panel);
    }
}
