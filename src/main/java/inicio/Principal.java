package inicio;

/**
 *
 * @author tuxrex
 */
import inicio.flexycup.SqlLexer;
import inicio.flexycup.parser;
import inicio.view.Inicio;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Principal {

    public static Inicio i;

    public static void main(String[] args) {
//    pruebas();
        gui();

    }

    public static void pruebas() {
        String Sho = "SELECCIONAR nombre,edad EN hoas.asdsd FILTRAR asda=34;";
        try {
            SqlLexer flexe = new SqlLexer(new BufferedReader(new StringReader(Sho)));
            parser pa = new parser(flexe);
            pa.parse();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void gui() {
        i = new Inicio();
        i.setVisible(true);
        i.setLocationRelativeTo(null);
    }

}
