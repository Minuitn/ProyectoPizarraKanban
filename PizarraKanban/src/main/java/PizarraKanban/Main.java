
package PizarraKanban;

public class Main {
    public static void main(String[] args) {

        // Activa el Look and Feel usado por tu compañera
        try {
            javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            // sin problema si falla
        }

        // Abrir la pantalla de Login correcta
        LoginView login = new LoginView();
        LoginController controller = new LoginController(login);

        login.setVisible(true);
    }
}


