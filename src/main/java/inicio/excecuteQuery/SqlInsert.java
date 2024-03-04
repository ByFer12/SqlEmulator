package inicio.excecuteQuery;

import com.opencsv.CSVWriter;
import inicio.view.Inicio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SqlInsert {

    private List<String> path;
    private List<String> valores;
    private List<String> columnas;

    public SqlInsert(List<String> path, List<String> valores, List<String> columnas) {
        this.path = path;
        this.valores = valores;
        this.columnas = columnas;
    }

    /**
     * Constructor para insertar sin columna en especifico
     *
     * @param path
     * @param valores
     */
    public SqlInsert(List<String> path, List<String> valores) {
        this.path = path;
        this.valores = valores;
        insertAll();
        this.path.clear();
        this.valores.clear();
    }

    /**
     * Metodo para insertar una fila entera sin columnas en especifico
     */
    private void insertAll() {
        //DefaultTableModel tbModel = (DefaultTableModel) Inicio.tableResult.getModel();
        try {
            BufferedWriter writ = new BufferedWriter(new FileWriter("/home/tuxrex/Escritorio/" + path.get(0) + "/" + path.get(1) + ".csv", true));
            BufferedReader read = new BufferedReader(new FileReader("/home/tuxrex/Escritorio/" + path.get(0) + "/" + path.get(1) + ".csv"));
            String hedLine = read.readLine();
            String[] head = hedLine.split(",");
            if (valores.size() < head.length) {
                JOptionPane.showMessageDialog(null, "Debes escribir para todas las columnas");
                return;
            }else if(valores.size() > head.length){
                 JOptionPane.showMessageDialog(null, "Ha escrito elementos de mas");
                return;
            }
            StringBuilder lineas = new StringBuilder();
            for (int i = 0; i < valores.size(); i++) {
                String aux = valores.get(i);
                if (aux.startsWith("\"") && aux.endsWith("\"")) {
                    aux = aux.substring(1, aux.length() - 1);
                }
                lineas.append(aux);
                if (i < valores.size() - 1) {
                    lineas.append(",");
                }
            }
            lineas.append("\n");
            writ.write(lineas.toString());

            // Cerrar el escritor
            writ.close();
        } catch (Exception e) {
            System.out.println("No existe el archivo a escribir");

        }

    }

    private void insertColumns() {
        try {
            BufferedWriter writ = new BufferedWriter(new FileWriter("/home/tuxrex/Escritorio/" + path.get(0) + "/" + path.get(1) + ".csv", true));
            BufferedReader read = new BufferedReader(new FileReader("/home/tuxrex/Escritorio/" + path.get(0) + "/" + path.get(1) + ".csv"));
            String hedLine = read.readLine();
            String[] head = hedLine.split(",");

            int numCol = -1;
            List<Integer> cols = new ArrayList<>();
            //Aqui capturamos la posicion de las columnas
            for (String heads : head) {
                numCol++;
                if (columnas.contains(heads.trim())) {
                    cols.add(numCol);
                }
            }

            if (valores.size() < columnas.size()) {
                JOptionPane.showMessageDialog(null, "Debes escribir para todas las columnas");
                return;
            }else if(valores.size() > columnas.size()){
                JOptionPane.showMessageDialog(null, "Ha escrito elementos de mas");
                return;
            }
            StringBuilder lineas = new StringBuilder();
            for (int i = 0; i < valores.size(); i++) {
                String aux = valores.get(i);
                if (aux.startsWith("\"") && aux.endsWith("\"")) {
                    aux = aux.substring(1, aux.length() - 1);
                }
                lineas.append(aux);
                if (i < valores.size() - 1) {
                    lineas.append(",");
                }
            }
            lineas.append("\n");
            writ.write(lineas.toString());

            // Cerrar el escritor
            writ.close();
        } catch (Exception e) {
            System.out.println("No existe el archivo a escribir");

        }

    }

}
