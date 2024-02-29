package inicio;

/**
 *
 * @author tuxrex
 */
import inicio.view.Inicio;

public class Principal {

    public static Inicio i;

    public static void main(String[] args) {
        i = new Inicio();
        i.setVisible(true);
        i.setLocationRelativeTo(null);
    }

}
