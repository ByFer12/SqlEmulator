package inicio.excecuteQuery;

import inicio.view.Inicio;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

public class SqlDelete {
    private List<String> cond;
    private List<String> path;

    public SqlDelete(List<String> cond, List<String> path) {
        this.cond = cond;
        this.path = path;
    }

    public SqlDelete(List<String> path) {
        this.path = path;
        eliminarSinCondicion();
        this.path.clear();
    }


    private  void eliminarSinCondicion(){
        StringBuilder pathh=metodoRuta();
        try {
            BufferedWriter writ = new BufferedWriter(new FileWriter("/home/tuxrex/Escritorio/" +pathh+ ".csv"));
            writ.write("");
            writ.close();
            JOptionPane.showMessageDialog(null,"Has borrado todos los datos de tu archivo CSV");
        } catch (Exception e) {
            Inicio.errores.setText("El archivo al que accede no existe, verifique la ruta");

        }

    }

    private StringBuilder metodoRuta() {
        StringBuilder pathh = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            pathh.append(path.get(i));
            if (i < path.size() - 1) {
                pathh.append("/");
            }

        }
        return pathh;
    }


}
