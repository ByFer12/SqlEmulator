package inicio.excecuteQuery;

import inicio.view.Inicio;

import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

public class SqlSelect {

    private List<String> columna;
    private List<String> path;
    private List<String> cond;

    public SqlSelect(List<String> columna, List<String> path, List<String> cond) {
        this.columna = columna;
        this.path = path;
        this.cond = cond;
        System.out.println("Si hay condicion, COlumna: " + columna.get(0) + " Direccion: " + path.toString() + " condicion: " + cond.toString());
        if (columna.contains("*")) {
            selectAllWithCondition();
            this.columna.clear();
            this.path.clear();
            this.cond.clear();

        } else {
            selectColumnWithCondition();
            this.columna.clear();
            this.path.clear();
            this.cond.clear();
        }
    }

    public SqlSelect(List<String> columna, List<String> path) {
        this.columna = columna;
        this.path = path;

        System.out.println("No hay condicion " + columna.get(0) + " Direccion: " + path.toString());
        if (columna.contains("*")) {
            //No hay condicion y muestra todas las columas
            selectAll();
            this.columna.clear();
            this.path.clear();
        } else {
            selectColumnSinCondition();
            this.columna.clear();
            this.path.clear();
        }
    }

    /**
     * Seleccionar todas las columnas sin filtro
     */
    private void selectAll() {
        DefaultTableModel tbModel = (DefaultTableModel) Inicio.tableResult.getModel();
        StringBuilder pathh = metodoRuta();

        try (BufferedReader read = new BufferedReader(new FileReader("/home/tuxrex/Escritorio/" + pathh + ".csv"))) {
            String hedLine = read.readLine();
            String[] head = hedLine.split(",");
            tbModel.setColumnCount(0);
            tbModel.setNumRows(0);

            for (String heads : head) {
                tbModel.addColumn(heads.trim());
            }

            String line;
            while ((line = read.readLine()) != null) {
                String[] rowData = line.split(",");
                Vector<Object> rowVector = new Vector<>();
                for (String value : rowData) {
                    rowVector.add(value.trim());
                }
                tbModel.addRow(rowVector);
            }
        } catch (Exception e) {
            Inicio.errores.setText("El archivo al que accede no existe, verifique la ruta");
            Inicio.errors=true;

        }

    }

    /**
     * Seleccionar todas las columnas sin filtro
     */
    private void selectColumnSinCondition() {

        DefaultTableModel tbModel = (DefaultTableModel) Inicio.tableResult.getModel();
        StringBuilder pathh = metodoRuta();
        try (BufferedReader read = new BufferedReader(new FileReader("/home/tuxrex/Escritorio/" + pathh + ".csv"))) {
            String hedLine = read.readLine();
            String[] allColumns = hedLine.split(",");

            int numCol = -1;
            List<Integer> cols = new ArrayList<>();
            //Aqui seleccionamos las columnas que hemos escrito
            List<String> actualColumns = new ArrayList<>();
            for (String heads : allColumns) {
                numCol++;
                if (columna.contains(heads.trim())) {
                    actualColumns.add(heads.trim());
                    cols.add(numCol);
                }
            }
            //Aqui reseteamos las filas y las columnas para las nuevas columnas seleccionados
            tbModel.setColumnCount(0);
            tbModel.setNumRows(0);
            for (String heads : actualColumns) {
                tbModel.addColumn(heads.trim());
            }

            String line;
            while ((line = read.readLine()) != null) {

                String[] rowData = line.split(",");
                

                Vector<Object> rowVector = new Vector<>();
                for (int i = 0; i < rowData.length; i++) {
                    for (int j = 0; j < cols.size(); j++) {
                        if (i == cols.get(j)) {

                            rowVector.add(rowData[i].trim());
                        }

                    }
                }
                tbModel.addRow(rowVector);
            }
        } catch (Exception e) {
            Inicio.errores.setText("El archivo al que accede no existe, verifique la ruta");
            Inicio.errors=true;
        }

    }

    /**
     * Seleccionar todos las columnas y con filtro condicional
     */
    private void selectAllWithCondition() {
        DefaultTableModel tbModel = (DefaultTableModel) Inicio.tableResult.getModel();
        StringBuilder pathh = metodoRuta();
        try (BufferedReader read = new BufferedReader(new FileReader("/home/tuxrex/Escritorio/" + pathh + ".csv"))) {
            String hedLine = read.readLine();
            String[] head = hedLine.split(",");
            tbModel.setColumnCount(0);
            tbModel.setNumRows(0);

            for (String heads : head) {
                tbModel.addColumn(heads.trim());
            }

            int indexColumn = -1;

            for (int i = 0; i < head.length; i++) {
                if (head[i].trim().equals(cond.get(0))) {
                    indexColumn = i;
                    break;
                }

            }

            if (indexColumn == -1) {
                JOptionPane.showMessageDialog(null, "La columna no existe");
                return;
            }

            String line;
            while ((line = read.readLine()) != null) {
                String dato = "";
                String[] rowData = line.split(",");
                String texto = cond.get(2);
                if (texto.startsWith("\"") && texto.endsWith("\"")) {
                    // Eliminar las comillas dobles escapadas
                    dato = texto.substring(1, texto.length() - 1);

                } else {
                    dato = texto;
                }
                if (rowData.length > indexColumn && rowData[indexColumn].trim().equals(dato)) {
                    Vector<Object> rowVector = new Vector<>();
                    for (String value : rowData) {
                        rowVector.add(value.trim());
                    }
                    tbModel.addRow(rowVector);
                }
            }
        } catch (Exception e) {
           Inicio.errores.setText("El archivo al que accede no existe, verifique la ruta");
           Inicio.errors=true;
        }

    }

    /**
     * Select con algunas columnas seleccionadas y con condicional
     */
    private void selectColumnWithCondition() {
        DefaultTableModel tbModel = (DefaultTableModel) Inicio.tableResult.getModel();
        StringBuilder pathh = metodoRuta();

        try (BufferedReader read = new BufferedReader(new FileReader("/home/tuxrex/Escritorio/" + pathh + ".csv"))) {
            String hedLine = read.readLine();
            String[] head = hedLine.split(",");

            //Aqui recorremos la columna al cual le designamos la condicion
            int indexColumn = -1;
            for (int i = 0; i < head.length; i++) {
                if (head[i].trim().equals(cond.get(0))) {
                    indexColumn = i;
                    break;
                }

            }

            int numCol = -1;
            List<Integer> cols = new ArrayList<>();
            //Aqui seleccionamos las columnas que hemos escrito
            List<String> actualColumns = new ArrayList<>();
            for (String heads : head) {
                numCol++;
                if (columna.contains(heads.trim())) {
                    actualColumns.add(heads.trim());
                    cols.add(numCol);
                }
            }

            tbModel.setColumnCount(0);
            tbModel.setNumRows(0);
            for (String heads : actualColumns) {
                tbModel.addColumn(heads.trim());
            }

            if (indexColumn == -1) {
                JOptionPane.showMessageDialog(null, "La columna no existe");
                return;
            }

            String line;
            while ((line = read.readLine()) != null) {
                String dato = "";
                String[] rowData = line.split(",");
                String texto = cond.get(2);
                if (texto.startsWith("\"") && texto.endsWith("\"")) {
                    // Eliminar las comillas dobles escapadas
                    dato = texto.substring(1, texto.length() - 1);

                } else {
                    dato = texto;
                }
                if (rowData.length > indexColumn && rowData[indexColumn].trim().equals(dato)) {
                    Vector<Object> rowVector = new Vector<>();
                    for (int i = 0; i < rowData.length; i++) {
                        for (int j = 0; j < cols.size(); j++) {
                            if (i == cols.get(j)) {

                                rowVector.add(rowData[i].trim());
                            }

                        }
                    }
                    tbModel.addRow(rowVector);
                }
            }
        } catch (Exception e) {
            Inicio.errores.setText("El archivo al que accede no existe, verifique la ruta");
            Inicio.errors=true;
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
