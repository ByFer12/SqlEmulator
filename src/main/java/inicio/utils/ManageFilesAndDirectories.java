/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inicio.utils;

import inicio.view.Inicio;
import static inicio.view.Inicio.arbolDirectorio;
import static inicio.view.Inicio.cargarEstructuraProyecto;
import static inicio.view.Inicio.doc;
import static inicio.view.Inicio.ideFile;
import static inicio.view.Inicio.modelo;
import static inicio.view.Inicio.raiz;
import static inicio.view.Inicio.ruta;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 *
 * @author tuxrex
 */
public class ManageFilesAndDirectories {

    public static File archivoNuevo;
    
    public static void crearArbolArchivo(String nomCarp) {
        boolean existe = false;
        String naame = OpenClosedFiles.crearArchivo();
        if (naame != null) {
            String other = ideFile.getAbsolutePath();
            int position = other.lastIndexOf("/");
            String path = other.substring(0, position);
            System.out.println("Path crearArbo: " + path + "/" + naame + ".csv");
            
            Element newArchivo = doc.createElement("ARCHIVO");
            newArchivo.setAttribute("nombre", naame);
            newArchivo.setAttribute("ubicacion", path + "/" + naame + ".csv");

            // Obtener la lista de todas las carpetas en el documento
            NodeList carpetas = doc.getElementsByTagName("CARPETA");
            for (int i = 0; i < carpetas.getLength(); i++) {
                Element carpeta = (Element) carpetas.item(i);
                
                if (naame.equals(carpeta.getAttribute("nombre"))) {
                    existe = true;
                    break;
                }
            }
            // Buscar la carpeta por su nombre (puedes ajustar según tu estructura)
            Element carpetaElegida = null;
            if (!existe) {
                for (int i = 0; i < carpetas.getLength(); i++) {
                    Element carpeta = (Element) carpetas.item(i);
                    if (nomCarp.equals(carpeta.getAttribute("nombre"))) {
                        carpetaElegida = carpeta;
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "El archivo ya existe");
            }
            
            if (carpetaElegida != null) {
                // Agregar el nuevo carpeta como hijo de la carpeta "Byron"
                carpetaElegida.appendChild(newArchivo);

                // Guardar los cambios de vuelta al carpeta XML
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = null;
                try {
                    transformer = transformerFactory.newTransformer();
                } catch (TransformerConfigurationException ex) {
                    Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                }
                DOMSource source = new DOMSource(doc);

                // Puedes ajustar la ruta del carpeta según tu caso
                StreamResult result = new StreamResult(new java.io.File("../../../../../" + ideFile.getAbsolutePath()));
                try {
                    transformer.transform(source, result);
                } catch (TransformerException ex) {
                    Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                JOptionPane.showMessageDialog(null, "Archivo creado correctaente con el nombre de: " + naame);
                
            } else {
                JOptionPane.showMessageDialog(null, "No se ha podido crear el archivo");
            }
            
            Inicio.raiz = Inicio.cargarEstructuraProyecto(ideFile);
            Inicio.modelo = new DefaultTreeModel(Inicio.raiz);
            arbolDirectorio.setModel(Inicio.modelo);
            arbolDirectorio.updateUI();
        }
    }
    
    public static void eliminarArbol(String selectArchive) {
        if (OpenClosedFiles.eliminar()) {
            //obtiene los archivos del documento
            Element padre;
            NodeList archivos = doc.getElementsByTagName("ARCHIVO");
            for (int i = 0; i < archivos.getLength(); i++) {
                Element archivo = (Element) archivos.item(i);
                padre = (Element) archivo.getParentNode();
                if (selectArchive.equals(new File(archivo.getAttribute("ubicacion")).getName())) {
                    padre.removeChild(archivo);
                    break;
                }
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = null;
            try {
                transformer = transformerFactory.newTransformer();
            } catch (TransformerConfigurationException ex) {
                Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
            }
            DOMSource source = new DOMSource(doc);

            // Puedes ajustar la ruta del carpeta según tu caso
            StreamResult result = new StreamResult(new java.io.File("../../../../../" + ideFile.getAbsolutePath()));
            try {
                transformer.transform(source, result);
            } catch (TransformerException ex) {
                Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            JOptionPane.showMessageDialog(null, "Eliminado correctamente");
            
            raiz = cargarEstructuraProyecto(ideFile);
            modelo = new DefaultTreeModel(raiz);
            arbolDirectorio.setModel(modelo);
            arbolDirectorio.updateUI();
            
        } else {
            JOptionPane.showMessageDialog(null, "No se ha podido eliminar el archivo");
            
        }
        
    }

    /**
     * Metodo para crear carpeta y actualizar
     * @param slectFile
     */
    public static void crearCarpeta(String slectFile) {
        boolean existe = false;
        Element root = doc.getDocumentElement();
        String name = JOptionPane.showInputDialog("Ingrese el nombre de la carpeta");
        if (name != null) {
            //System.out.println("Path crearArbo: " + path + "/" + name);
            Element newCarpeta = doc.createElement("CARPETA");
            newCarpeta.setAttribute("nombre", name);
            Text nodo;
            NodeList carpetas = doc.getElementsByTagName("CARPETA");
            
            for (int i = 0; i < carpetas.getLength(); i++) {
                Element carpeta = (Element) carpetas.item(i);
                
                if (name.equals(carpeta.getAttribute("nombre"))) {
                    existe = true;
                    break;
                }
            }
            
            if (!existe) {
                // Buscar la carpeta por su nombre (puedes ajustar según tu estructura)
                for (int i = 0; i < carpetas.getLength(); i++) {
                    Element carpeta = (Element) carpetas.item(i);
                    
                    if (slectFile.equals(carpeta.getAttribute("nombre"))) {
                        nodo = doc.createTextNode("");
                        newCarpeta.appendChild(nodo);
                        carpeta.appendChild(newCarpeta);
                        break;
                    } else if (slectFile.toString().equals("project")) {
                        root.appendChild(newCarpeta);
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "La carpeta ya existe");
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = null;
            try {
                transformer = transformerFactory.newTransformer();
            } catch (TransformerConfigurationException ex) {
                Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
            }
            DOMSource source = new DOMSource(doc);
            System.out.println("Crear Carpeta path: " + ideFile.getAbsolutePath());

            // Puedes ajustar la ruta del carpeta según tu caso
            StreamResult result = new StreamResult(new java.io.File("../../../../../" + ideFile.getAbsolutePath()));
            try {
                transformer.transform(source, result);
            } catch (TransformerException ex) {
                Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (!existe) {
                JOptionPane.showMessageDialog(null, "Creado correctamente");
            }
            
            raiz = cargarEstructuraProyecto(ideFile);
            modelo = new DefaultTreeModel(raiz);
            arbolDirectorio.setModel(modelo);
            arbolDirectorio.updateUI();
        }
        
    }

    /**
     * Metodo para eliminar carpeta y actualizar
     * @param selectedDirectory
     */
    public static void eliminarCarpeta(String selectedDirectory) {
        Element padre;
        NodeList archivos = doc.getElementsByTagName("CARPETA");
        for (int i = 0; i < archivos.getLength(); i++) {
            Element carpeta = (Element) archivos.item(i);
            padre = (Element) carpeta.getParentNode();
            if (selectedDirectory.equals(carpeta.getAttribute("nombre"))) {
                padre.removeChild(carpeta);
                break;
            }
        }
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Eliminar path: " + ideFile.getAbsolutePath());
        DOMSource source = new DOMSource(doc);

        // Puedes ajustar la ruta del carpeta según tu caso
        StreamResult result = new StreamResult(new java.io.File("../../../../../" + ideFile.getAbsolutePath()));
        try {
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JOptionPane.showMessageDialog(null, "Eliminado correctamente");
        
        raiz = cargarEstructuraProyecto(ideFile);
        modelo = new DefaultTreeModel(raiz);
        arbolDirectorio.setModel(modelo);
        arbolDirectorio.updateUI();
        
    }

    /**
     * Metodo para crear un nuevo proyecto o archivo.ide
     * @param path
     * @param nombre
     * @param carpeta
     * @param archivo
     * @throws ParserConfigurationException
     * @throws TransformerConfigurationException
     * @throws TransformerException
     */
    public static void crearProject(String path, String nombre, String carpeta, String archivo) throws ParserConfigurationException, TransformerConfigurationException, TransformerException {
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();
        
        Document documento = implementation.createDocument(null, "PROYECTO", null);
        Element proyecto = documento.getDocumentElement();
        proyecto.setAttribute("nombre", "project");
        
        Element nCarpeta = documento.createElement("CARPETA");
        nCarpeta.setAttribute("nombre", carpeta);
        
        Element nArchivo = documento.createElement("ARCHIVO");
        nArchivo.setAttribute("nombre", archivo);
        nArchivo.setAttribute("ubicacion", path + "/" + archivo + ".csv");
        
        nCarpeta.appendChild(nArchivo);
        
        documento.getDocumentElement().appendChild(nCarpeta);
        
        System.out.println("PATH de creacion: " + path);
        archivoNuevo = new File("../../../../../" + path + "/" + nombre + ".ide");
        
        Source source = new DOMSource(documento);        
        Result res = new StreamResult(archivoNuevo);
        Transformer ts = TransformerFactory.newInstance().newTransformer();
        ts.transform(source, res);
        
    }

//    public static void cargarDatos() {
//        DefaultTableModel tbModel = (DefaultTableModel) Inicio.tableResult.getModel();
//        try (BufferedReader read = new BufferedReader(new FileReader(ruta))) {
//            String hedLine = read.readLine();
//            String[] head = hedLine.split(",");
//            tbModel.setColumnCount(0);
//            tbModel.setNumRows(0);
//            for (String heads : head) {
//                tbModel.addColumn(heads.trim());
//            }
//            
//            String line;
//            while ((line = read.readLine()) != null) {
//                String[] rowData = line.split(",");
//                Vector<Object> rowVector = new Vector<>();
//                for (String value : rowData) {
//                    rowVector.add(value.trim());
//                }
//                tbModel.addRow(rowVector);
//            }
//        } catch (Exception e) {
//            
//        }
//    }
    
}
