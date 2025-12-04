package PizarraKanban;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class RegistrarUsuarioController implements ActionListener {

    private RegistrarUsuarioView vista;
    private UsuarioDAO dao;

    public RegistrarUsuarioController(RegistrarUsuarioView vista) {
        this.vista = vista;
        this.dao = new UsuarioDAO();

        // eventos
        this.vista.btnRegistrar.addActionListener(this);
        this.vista.btnCancelar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.btnCancelar) {
            // cerrar ventana de registro
            vista.dispose();
            return;
        }

        if (e.getSource() == vista.btnRegistrar) {
            // tomar datos y validar
            String usuario = vista.txtUsuario.getText().trim();
            String pass = new String(vista.txtPassword.getPassword()).trim();
            String rol = (String) vista.cbRol.getSelectedItem();

            if (usuario.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Complete todos los campos.");
                return;
            }

            if (pass.length() < 6) {
                JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos 6 caracteres.");
                return;
            }

            // hash de la contraseña (SHA-256)
            String hash = SeguridadUtil.hashSHA256(pass);
            boolean ok = dao.registrarConRol(usuario, hash, rol);

            if (ok) {
                JOptionPane.showMessageDialog(null, "Usuario registrado correctamente.");
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar el usuario (quizá ya existe).");
            }
        }
    }
}




