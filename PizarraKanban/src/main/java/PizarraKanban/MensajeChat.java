
package PizarraKanban;

public class MensajeChat {

    private String username;
    private String mensaje;
    private String fecha;

    public MensajeChat(String username, String mensaje, String fecha) {
        this.username = username;
        this.mensaje = mensaje;
        this.fecha = fecha;
    }

    public String getUsername() { return username; }
    public String getMensaje() { return mensaje; }
    public String getFecha() { return fecha; }
}
