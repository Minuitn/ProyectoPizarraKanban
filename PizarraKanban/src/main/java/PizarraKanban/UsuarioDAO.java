package PizarraKanban;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Acceso a datos para usuarios
public class UsuarioDAO {

    // login
    public Usuario login(String username, String passwordHash) {
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password_hash = ?";
        ConexionDB conexion = new ConexionDB();
        Connection conn = conexion.conectar();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("rol")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Error login: " + e.toString());
        } finally {
            conexion.desconectar();
        }

        return null;
    }

    // registrar con rol
    public boolean registrarConRol(String username, String passwordHash, String rol) {
        String sql = "INSERT INTO usuarios (username, password_hash, rol) VALUES (?, ?, ?)";
        ConexionDB conexion = new ConexionDB();
        Connection conn = conexion.conectar();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            ps.setString(3, rol);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error registrar: " + e.toString());
            return false;
        } finally {
            conexion.desconectar();
        }
    }

    // eliminar usuario por id (lo dejo porque ya estaba)
    public boolean eliminarUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        ConexionDB conexion = new ConexionDB();
        Connection conn = conexion.conectar();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error eliminar usuario: " + e.toString());
            return false;
        } finally {
            conexion.desconectar();
        }
    }

    // *** NUEVO ***
    // eliminar usuario por username (para el admin)
    public boolean eliminarPorUsername(String username) {
        String sql = "DELETE FROM usuarios WHERE username = ?";
        ConexionDB conexion = new ConexionDB();
        Connection conn = conexion.conectar();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error al eliminar usuario (username): " + e.toString());
            return false;
        } finally {
            conexion.desconectar();
        }
    }

    // obtener lista de usernames (para combo)
    public List<String> obtenerUsernames() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT username FROM usuarios ORDER BY username ASC";
        ConexionDB conexion = new ConexionDB();
        Connection conn = conexion.conectar();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(rs.getString("username"));
            }
        } catch (Exception e) {
            System.out.println("Error obtenerUsernames: " + e.toString());
        } finally {
            conexion.desconectar();
        }

        if (lista.isEmpty()) lista.add("admin");
        return lista;
    }
}






