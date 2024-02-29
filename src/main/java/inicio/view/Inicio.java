/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package inicio.view;

import inicio.utils.OpenClosedFiles;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 *
 * @author tuxrex
 */
public class Inicio extends javax.swing.JFrame {

    public static boolean iscreates = false;

    /**
     * Creates new form Inicio
     */
    public static File ideFile;
    public static Files frame;
    public static String ruta = "";
    public static String nombre = "";
    public static boolean isnew = false;
    public static int position;
    public static File nCarpeta;
    public static DocumentBuilderFactory dbFactory;
    private static DocumentBuilder dbBuilder;
    public static Document doc;

    public Inicio() {
        //String rutaSelect="";
        int posicion = 0;
        initComponents();

        arbolDirectorio.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent tse) {
                try {
                    //arbolDirectorio.getLastSelectedPathComponent().toString();
                    String archivo = arbolDirectorio.getLastSelectedPathComponent().toString();
                    frame = new Files();
                    //frame.setVisible(true);

                    if (archivo.contains(".")) {

                        tabbed1.addTab(archivo, frame);
                        tabbed1.setSelectedComponent(frame);
                        position = ideFile.getAbsolutePath().lastIndexOf("/");
                        ruta = ideFile.getAbsolutePath().substring(0, position);
                        OpenClosedFiles.leerArchivo(ruta + "/" + tabbed1.getTitleAt(tabbed1.getSelectedIndex()));
                        nombre = archivo;

                        System.out.println(tabbed1.getTitleAt(tabbed1.getSelectedIndex()));
                        System.out.println("Nombre: " + tabbed1.getTabCount());
                        System.out.println("Ruta de archivo seleccionado: " + ruta + "/" + tabbed1.getTitleAt(tabbed1.getSelectedIndex()));

                    } else {

                    }
                } catch (Exception e) {
                    //JOptionPane.showMessageDialog(null, "No se ha seleccionado la carpeta, vuelve a intentarlo");

                }

            }

        });

        arbolDirectorio.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    // Obtener el nodo seleccionado en el JTree
                    TreePath path = arbolDirectorio.getPathForLocation(e.getX(), e.getY());
                    if (path != null) {
                        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                        // Crear un menú contextual con las opciones de crear carpeta y carpeta
                        JPopupMenu menu = new JPopupMenu();
                        JMenuItem opciones;
                        System.out.println("Selected node: " + selectedNode.toString());
                        if (selectedNode.toString().contains(".")) {
                            opciones = new JMenuItem("Eliminar");

                            menu.add(opciones);
                            administrarCarpetasArchivos(opciones, selectedNode.toString());

                        } else {
                            
                            opciones = new JMenuItem("Crear Carpeta");

                            menu.add(opciones);

                            administrarCarpetasArchivos(opciones, selectedNode.toString());
                            opciones = new JMenuItem("Crear Archivo");

                            menu.add(opciones);
                            administrarCarpetasArchivos(opciones, selectedNode.toString());
                            opciones = new JMenuItem("Eliminar Carpeta");

                            menu.add(opciones);
                            administrarCarpetasArchivos(opciones, selectedNode.toString());

                        }

                        // Mostrar el menú contextual en la posición del clic derecho
                        menu.show(arbolDirectorio, e.getX(), e.getY());
                    }
                }
            }
        });
    }

    public void administrarCarpetasArchivos(JMenuItem accion, String selectedNode) {
        accion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (accion.getActionCommand().equals("Crear Carpeta")) {
                    crearCarpeta(selectedNode);
                } else if (accion.getActionCommand().equals("Crear Archivo")) {
                    crearArbolArchivo(selectedNode);

                } else if (accion.getActionCommand().equals("Eliminar")) {
                    eliminarArbol(selectedNode);

                } else if (accion.getActionCommand().equals("Eliminar Carpeta")) {
                    eliminarCarpeta(selectedNode);
                }

            }
        });

    }

    public void eliminarCarpeta(String selectedDirectory) {
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
        DOMSource source = new DOMSource(doc);

        // Puedes ajustar la ruta del carpeta según tu caso
        StreamResult result = new StreamResult(new java.io.File("../../../SQLemulator/proyecto.ide"));
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

    public void eliminarArbol(String selectArchive) {
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
            StreamResult result = new StreamResult(new java.io.File("../../../SQLemulator/proyecto.ide"));
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

    public void crearCarpeta(String slectFile) {
        boolean existe = false;

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

            // Puedes ajustar la ruta del carpeta según tu caso
            StreamResult result = new StreamResult(new java.io.File("../../../SQLemulator/proyecto.ide"));
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

    public void crearArbolArchivo(String nomCarp) {
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
                StreamResult result = new StreamResult(new java.io.File("../../../SQLemulator/proyecto.ide"));
                try {
                    transformer.transform(source, result);
                } catch (TransformerException ex) {
                    Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                }

                JOptionPane.showMessageDialog(null, "Archivo creado correctaente con el nombre de: " + naame);

            } else {
                JOptionPane.showMessageDialog(null, "No se ha podido crear el archivo");
            }

            raiz = cargarEstructuraProyecto(ideFile);
            modelo = new DefaultTreeModel(raiz);
            arbolDirectorio.setModel(modelo);
            arbolDirectorio.updateUI();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        pane1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        arbolDirectorio = new javax.swing.JTree();
        tabbed1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        file = new javax.swing.JMenu();
        openFile = new javax.swing.JMenuItem();
        newProject = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SQLemulator");

        arbolDirectorio.setFont(new java.awt.Font("Liberation Sans", 0, 17)); // NOI18N
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("-");
        arbolDirectorio.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane1.setViewportView(arbolDirectorio);

        javax.swing.GroupLayout pane1Layout = new javax.swing.GroupLayout(pane1);
        pane1.setLayout(pane1Layout);
        pane1Layout.setHorizontalGroup(
            pane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
        );
        pane1Layout.setVerticalGroup(
            pane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jTextPane1.setFont(new java.awt.Font("Liberation Sans", 0, 19)); // NOI18N
        jScrollPane2.setViewportView(jTextPane1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78))
        );

        jTextPane2.setFont(new java.awt.Font("Liberation Sans", 0, 19)); // NOI18N
        jTextPane2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextPane2KeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(jTextPane2);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel1.setText("Area de Consultas SQL");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );

        file.setText("Archivo");
        file.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fileMouseClicked(evt);
            }
        });
        file.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileActionPerformed(evt);
            }
        });

        openFile.setIcon(new javax.swing.ImageIcon("/home/tuxrex/NetBeansProjects/Web/SQLemulator/src/main/java/inicio/icons/database.png")); // NOI18N
        openFile.setText("Abrir archivo");
        openFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFileActionPerformed(evt);
            }
        });
        file.add(openFile);

        newProject.setIcon(new javax.swing.ImageIcon("/home/tuxrex/NetBeansProjects/Web/SQLemulator/src/main/java/inicio/icons/add_9068662.png")); // NOI18N
        newProject.setText("CrearProyecto");
        newProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newProjectActionPerformed(evt);
            }
        });
        file.add(newProject);

        jMenuBar1.add(file);
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbed1, javax.swing.GroupLayout.DEFAULT_SIZE, 1259, Short.MAX_VALUE)
                .addGap(14, 14, 14))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(tabbed1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileActionPerformed

    }//GEN-LAST:event_fileActionPerformed

    private void fileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fileMouseClicked

    }//GEN-LAST:event_fileMouseClicked

    private void openFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFileActionPerformed
        // TODO add your handling code here:
        //int seleccion=0;

        JFileChooser abrirDirectorio = new JFileChooser();
        FileNameExtensionFilter filtrar = new FileNameExtensionFilter("Solo archivos ide", "ide");
        abrirDirectorio.setFileFilter(filtrar);
        abrirDirectorio.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int seleccion = abrirDirectorio.showOpenDialog(this);

        if (seleccion == abrirDirectorio.APPROVE_OPTION) {
            ideFile = abrirDirectorio.getSelectedFile();
            raiz = cargarEstructuraProyecto(ideFile);
            modelo = new DefaultTreeModel(raiz);
            arbolDirectorio.setModel(modelo);

        } else {
            JOptionPane.showMessageDialog(null, "No se selecciono ninguna carpeta");
        }
    }//GEN-LAST:event_openFileActionPerformed

    private void jTextPane2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPane2KeyPressed
        System.out.println("Hola acaban de hacer enter");
    }//GEN-LAST:event_jTextPane2KeyPressed

    private void newProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newProjectActionPerformed
        String nombreCarpeta = JOptionPane.showInputDialog("Ingrese el nombre de la carpeta para el proyecto:");
        String nombreArchivo = JOptionPane.showInputDialog("Ingrese el nombre del archivo del proyecto:");

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona la carpeta donde se creará el proyecto");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = fileChooser.showOpenDialog(Inicio.this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = fileChooser.getSelectedFile();

        }

    }//GEN-LAST:event_newProjectActionPerformed

    public DefaultMutableTreeNode cargarEstructuraProyecto(File idFile) {
        DefaultMutableTreeNode raiz = null;
        try {
            dbFactory = DocumentBuilderFactory.newInstance();
            dbBuilder = dbFactory.newDocumentBuilder();
            doc = dbBuilder.parse(idFile);
            doc.getDocumentElement().normalize();

            //Obtener el nodo de la raiz del proyecto
            Element proyecto = (Element) doc.getElementsByTagName("PROYECTO").item(0);
            raiz = new DefaultMutableTreeNode(proyecto.getAttribute("nombre"));

            //Procesar carpetas y carpetas
            processNode(proyecto, raiz);
        } catch (Exception e) {

        }
        return raiz;
    }

    private static void processNode(Element element, DefaultMutableTreeNode parent) {
        // Procesar todas las carpetas en el nodo actual primero
        NodeList carpetas = element.getElementsByTagName("CARPETA");
        for (int i = 0; i < carpetas.getLength(); i++) {
            Element carpeta = (Element) carpetas.item(i);
            // Verificar que la carpeta sea un hijo directo del elemento actual
            System.out.println("processNode: " + carpeta.getNodeName());
            if (carpeta.getParentNode().equals(element)) {
                String nombreCarpeta = carpeta.getAttribute("nombre");
                DefaultMutableTreeNode folderNode = new DefaultMutableTreeNode(nombreCarpeta);
                parent.add(folderNode);

                // Recursividad para subcarpetas y carpetas dentro de esta carpeta
                processNode(carpeta, folderNode);
            }
        }

        // Procesar todos los carpetas en el nodo actual después de las carpetas
        NodeList archivos = element.getElementsByTagName("ARCHIVO");
        for (int i = 0; i < archivos.getLength(); i++) {
            Element archivo = (Element) archivos.item(i);
            // Verificar que el carpeta sea un hijo directo del elemento actual
            if (archivo.getParentNode().equals(element)) {
                String nombreArchivo = new File(archivo.getAttribute("ubicacion")).getName();
                DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode(nombreArchivo);
                System.out.println("Archivos: " + nombreArchivo);
                parent.add(fileNode);
            }
        }
    }

    /**
     * z
     *
     * @param args the command line arguments
     */
//    private List<File> carpetasAbiertas = new ArrayList<>();
    private static DefaultMutableTreeNode raiz;
    private static DefaultTreeModel modelo;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTree arbolDirectorio;
    private javax.swing.JMenu file;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JMenuItem newProject;
    private javax.swing.JMenuItem openFile;
    private javax.swing.JPanel pane1;
    public static javax.swing.JTabbedPane tabbed1;
    // End of variables declaration//GEN-END:variables
}
