
package PizarraKanban;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    // Verificar login (username + hash)
    public Usuario login(String username, String passwordHash) {
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password_hash = ?";
        ConexionDB conexion = new ConexionDB();
        Connection conn = conexion.conectar();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Usuario(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password_hash"),
                    rs.getString("rol")
                );
            }

        } catch (Exception e) {
            System.out.println("Error login: " + e.toString());
        } finally {
            conexion.desconectar();
        }

        return null; // login incorrecto
    }

    // Registrar usuario (solo si ocupan crear nuevos)
    public boolean registrar(String username, String passwordHash) {
        String sql = "INSERT INTO usuarios (username, password_hash) VALUES (?, ?)";

        ConexionDB conexion = new ConexionDB();
        Connection conn = conexion.conectar();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwordHash);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error registrar: " + e.toString());
            return false;

        } finally {
            conexion.desconectar();
        }
    }
}

