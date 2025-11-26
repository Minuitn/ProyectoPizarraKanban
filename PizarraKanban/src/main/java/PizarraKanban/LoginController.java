
package PizarraKanban;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LoginController implements ActionListener {

    private LoginView vista;
    private UsuarioDAO dao;

    public LoginController(LoginView vista) {
        this.vista = vista;
        this.dao = new UsuarioDAO();
        vista.btnLogin.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String user = vista.txtUsuario.getText().trim();
        String pass = new String(vista.txtPassword.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe completar todos los campos.");
            return;
        }

        // GENERAMOS EL HASH
        String hash = SeguridadUtil.hashSHA256(pass);

        // DEBUG: imprimirlo para ver si coincide con el de la BD
        System.out.println("HASH INTRODUCIDO = " + hash); // <--- ESTE ES EL PASO B

        Usuario u = dao.login(user, hash);

        if (u == null) {
            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
            return;
        }

        JOptionPane.showMessageDialog(null, "Bienvenido " + u.getUsername());

        // Abrimos la aplicación original
        TareaView vistaTareas = new TareaView();
        TareaController controller = new TareaController(vistaTareas);
        vistaTareas.setVisible(true);

        vista.dispose();
    }
}

