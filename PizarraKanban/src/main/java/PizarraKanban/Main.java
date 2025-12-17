package PizarraKanban;

import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            // si falla no es crítico
        }

        LoginView login = new LoginView();
        LoginController controller = new LoginController(login);
        login.setVisible(true);
    }
}
