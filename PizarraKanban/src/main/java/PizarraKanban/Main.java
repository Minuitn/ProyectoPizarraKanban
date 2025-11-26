
package PizarraKanban;

public class Main {

    public static void main(String[] args) {

        LoginView login = new LoginView();
        LoginController controller = new LoginController(login);

        login.setVisible(true);
       // Probar hash admin123 contra lo que hay en BD


    }
    
}

