
package PizarraKanban;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChatView extends JPanel {

    private JTextArea areaChat;
    private JTextField txtMensaje;
    private JButton btnEnviar;
    private JButton btnActualizar;

    private ChatDAO dao;
    private Usuario usuario;

    public ChatView(Usuario usuario) {
        this.usuario = usuario;
        this.dao = new ChatDAO();

        setLayout(new BorderLayout(5,5));
        setBorder(BorderFactory.createTitledBorder("Chat del Proyecto"));

        areaChat = new JTextArea();
        areaChat.setEditable(false);
        areaChat.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(areaChat);

        txtMensaje = new JTextField();
        btnEnviar = new JButton("Enviar");
        btnActualizar = new JButton("Actualizar");

        JPanel panelInferior = new JPanel(new BorderLayout(5,5));
        panelInferior.add(txtMensaje, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botones.add(btnActualizar);
        botones.add(btnEnviar);

        panelInferior.add(botones, BorderLayout.EAST);

        add(scroll, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        btnEnviar.addActionListener(e -> enviar());
        btnActualizar.addActionListener(e -> cargarMensajes());

        cargarMensajes();
    }

    private void enviar() {
        String texto = txtMensaje.getText().trim();
        if (texto.isEmpty()) return;

        dao.enviarMensaje(usuario.getUsername(), texto);
        txtMensaje.setText("");
        cargarMensajes();
    }

    private void cargarMensajes() {
        areaChat.setText("");
        List<MensajeChat> mensajes = dao.listarMensajes();

        for (MensajeChat m : mensajes) {
            areaChat.append(
                "[" + m.getUsername() + "] " + m.getMensaje() + "\n"
            );
        }
    }
}
