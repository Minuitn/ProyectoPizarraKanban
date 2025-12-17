
package PizarraKanban;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

// Controlador del login
public class LoginController implements ActionListener {

    private LoginView vista;
    private UsuarioDAO dao;

    public LoginController(LoginView vista) {
        this.vista = vista;
        this.dao = new UsuarioDAO();
        this.vista.btnLogin.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user = vista.txtUsuario.getText().trim();
        String pass = new String(vista.txtPassword.getPassword()).trim();

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe completar todos los campos.");
            return;
        }

        String hash = SeguridadUtil.hashSHA256(pass);
        Usuario u = dao.login(user, hash);

        if (u == null) {
            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
            return;
        }

        JOptionPane.showMessageDialog(null, "Bienvenido " + u.getUsername());

        ProyectoView vistaProyectos = new ProyectoView(u);
        vistaProyectos.setVisible(true);

        vista.dispose(); // cierra login

    }
}



