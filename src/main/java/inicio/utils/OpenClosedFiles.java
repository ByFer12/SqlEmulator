/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inicio.utils;

import inicio.view.Files;
import inicio.view.Inicio;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author tuxrex
 */
public class OpenClosedFiles {
    /**
     * Este metodo crea un nuevo archivoo dentro de una carpeta ya antes creada
     * @param carpetaProyecto
     * @param nombreArchivo
     * @return 
     */
    public static File crearArchivoProyecto(File carpetaProyecto, String nombreArchivo) {
        File file = new File(carpetaProyecto, nombreArchivo + ".txt");
        try {
            if (file.createNewFile()) {
                JOptionPane.showMessageDialog(null, "Proyecto creado en: " + file.getAbsolutePath());
            } else {
                //JOptionPane.showMessageDialog(null, "El archivo ya existe.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Ruta elegida 2 dentro del metodo crearProyect0: "+file.getAbsolutePath());
        }
        return file;
    }
    
    /**
     * //Metodo para leer un archivo seleccionado
     * @param ruta 
     */
    public static void leerArchivo(String ruta) {
        String contenido;
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(ruta))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Error al cargar el archivo");
        }

        contenido = content.toString();
        Files.areaCode.setText(contenido);

    }
    
    /**
     * //Metodo para guardar cambios de un archivo
     * @param contenido
     * @param path
     * @throws IOException 
     */
    public static void guardarArchivo(String contenido,String path) throws IOException{
        System.out.println("Ruta dentro de Guardar: "+path);
         try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(contenido);
         }
    }
    
    
    public static void createFile( String path)throws IOException{
         System.out.println("Ruta dentro de Guardar: "+path);
       File file=new File(path);
            if(file.createNewFile()){
            }else{
                JOptionPane.showMessageDialog(null, "El archivo ya existe");
            }
        
    }
    
    public static void eliminarArchivo(String path)throws IOException{
        File fi=new File(path);
        if(fi.delete()){
            
        }else{
            System.out.println("No se pudo eliminar");
        }
        
    }
}
