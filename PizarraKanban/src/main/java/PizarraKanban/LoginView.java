
package PizarraKanban;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    public JTextField txtUsuario;
    public JPasswordField txtPassword;
    public JButton btnLogin;

    public LoginView() {
        setTitle("Login - Pizarra Kanban");
        setSize(300, 180);
        setLayout(new GridLayout(3, 2));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        add(txtUsuario);

        add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        btnLogin = new JButton("Ingresar");
        add(new JLabel()); // hueco
        add(btnLogin);
    }
}
