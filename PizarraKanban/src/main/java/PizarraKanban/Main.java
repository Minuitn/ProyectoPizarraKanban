package PizarraKanban;



public class Main {

    public static void main(String[] args) {

        TareaView vista = new TareaView();
        TareaController controller = new TareaController(vista);

        vista.setVisible(true);
    }
}

