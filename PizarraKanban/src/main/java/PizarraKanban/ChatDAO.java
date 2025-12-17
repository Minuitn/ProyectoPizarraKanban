
package PizarraKanban;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatDAO {

    public void enviarMensaje(String username, String mensaje) {

        String sql = "INSERT INTO chat_mensajes (username, mensaje) VALUES (?, ?)";
        ConexionDB conexion = new ConexionDB();

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, mensaje);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error enviarMensaje: " + e);
        } finally {
            conexion.desconectar();
        }
    }

    public List<MensajeChat> listarMensajes() {

        List<MensajeChat> lista = new ArrayList<>();
        String sql = "SELECT username, mensaje, fecha FROM chat_mensajes ORDER BY id ASC";
        ConexionDB conexion = new ConexionDB();

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new MensajeChat(
                        rs.getString("username"),
                        rs.getString("mensaje"),
                        rs.getString("fecha").toString()
                ));
            }

        } catch (Exception e) {
            System.out.println("Error listarMensajes: " + e);
        } finally {
            conexion.desconectar();
        }

        return lista;
    }
}

