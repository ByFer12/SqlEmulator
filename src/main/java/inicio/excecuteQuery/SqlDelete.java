package inicio.excecuteQuery;

import inicio.view.Inicio;

import javax.swing.*;
import java.io.*;
import java.util.List;

public class SqlDelete {
    private List<String> cond;
    private List<String> path;

    public SqlDelete(List<String> cond, List<String> path) {
        this.cond = cond;
        this.path = path;
        eliminarConCondicion();
        this.cond.clear();
        this.path.clear();
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
            Inicio.errors=true;

        }

    }

    private  void eliminarConCondicion(){
        StringBuilder pathh=metodoRuta();
        String dato=cond.get(2);
        try {
            eliminarLinea(pathh, dato);

            JOptionPane.showMessageDialog(null,"Has borrado todos los datos de tu archivo CSV");
        } catch (Exception e) {
            Inicio.errores.setText("Error al eliminar el elemento");
            Inicio.errors=true;

        }

    }

    private void eliminarLinea(StringBuilder pathh, String dato) throws IOException {

        File inputFile = new File("/home/tuxrex/Escritorio/" +pathh+ ".csv");
        File tempFile = new File( "/home/tuxrex/Escritorio/" +pathh+ ".csv");
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String currentLine;

        while ((currentLine = reader.readLine()) != null) {
            // Dividir la línea en campos utilizando coma como delimitador
            String[] fields = currentLine.split(",");

            // Verificar si la línea contiene la edad a eliminar
            boolean contieneEdad = false;
            for (String field : fields) {
                if (field.trim().equals(String.valueOf(dato))) {
                    contieneEdad = true;
                    break;
                }
            }

            // Escribir la línea en el archivo temporal solo si no contiene la edad a eliminar
            if (!contieneEdad) {
                writer.write(currentLine + System.getProperty("line.separator"));
            }
        }
        writer.close();
        reader.close();

        writer.close();
        reader.close();
        if (!inputFile.delete()) {
            Inicio.errores.setText("No se pudo eliminar el archivo anterior");
        }
        if (!tempFile.renameTo(inputFile)) {
            Inicio.errores.setText("No se pudo renombrar el archivo temporal.");
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
