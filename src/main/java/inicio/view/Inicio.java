/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package inicio.view;

import inicio.Principal;
import inicio.flexycup.SqlLexer;
import inicio.flexycup.parser;
import inicio.utils.ManageFilesAndDirectories;
import inicio.utils.OpenClosedFiles;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author tuxrex
 */
public class Inicio extends javax.swing.JFrame {

    public static boolean iscreates = false;
 ArrayList<Integer> lineNumbers = new ArrayList<>();
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
    public NuevoProyecto nuevoProyect;
    public static boolean errors=false;

    public Inicio() {
        int posicion = 0;
        //String rutaSelect="";
        initComponents();
        btnErrors.setVisible(false);
        nuevoProyect = new NuevoProyecto();
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
                        ruta = ruta + "/" + tabbed1.getTitleAt(tabbed1.getSelectedIndex());
                        System.out.println("Ruta de archivo seleccionado: " + ruta);
                        //ManageFilesAndDirectories.cargarDatos();

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

                        } else if (selectedNode.toString().equals("project")) {
                            opciones = new JMenuItem("Crear Carpeta");

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
                    ManageFilesAndDirectories.crearCarpeta(selectedNode);
                } else if (accion.getActionCommand().equals("Crear Archivo")) {
                    ManageFilesAndDirectories.crearArbolArchivo(selectedNode);

                } else if (accion.getActionCommand().equals("Eliminar Archivo")) {
                    ManageFilesAndDirectories.eliminarArbol(selectedNode);

                } else if (accion.getActionCommand().equals("Eliminar Carpeta")) {
                    ManageFilesAndDirectories.eliminarCarpeta(selectedNode);
                }

            }
        });

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
        jScrollPane4 = new javax.swing.JScrollPane();
        tableResult = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        consulta = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        rutaConsole = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        errores = new javax.swing.JTextPane();
        btnErrors = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        positionCode = new javax.swing.JLabel();
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

        tableResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tableResult);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 732, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
        );

        consulta.setFont(new java.awt.Font("Liberation Sans", 0, 19)); // NOI18N
        consulta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                consultaKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(consulta);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 255));
        jLabel1.setText("Area de Consultas SQL");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(384, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );

        rutaConsole.setEditable(false);
        rutaConsole.setFont(new java.awt.Font("Liberation Sans", 0, 16)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Liberation Sans", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Resultado");

        errores.setEditable(false);
        errores.setFont(new java.awt.Font("Liberation Sans", 3, 15)); // NOI18N
        errores.setForeground(new java.awt.Color(204, 51, 0));
        jScrollPane2.setViewportView(errores);

        btnErrors.setBackground(new java.awt.Color(255, 51, 51));
        btnErrors.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        btnErrors.setForeground(new java.awt.Color(102, 0, 0));
        btnErrors.setText("Ver errores");
        btnErrors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnErrorsActionPerformed(evt);
            }
        });

        positionCode.setText("Position: 0, 0");

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
                .addComponent(tabbed1)
                .addGap(14, 14, 14))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rutaConsole, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(positionCode)
                        .addGap(81, 81, 81))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnErrors)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(pane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(tabbed1))
                        .addGap(20, 20, 20)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rutaConsole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(positionCode))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(btnErrors)))
                        .addGap(44, 44, 44))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))))
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

            rutaConsole.setText(ideFile.getAbsolutePath());
        } else {
            JOptionPane.showMessageDialog(null, "No se selecciono ninguna carpeta");
        }

    }//GEN-LAST:event_openFileActionPerformed

    private void consultaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_consultaKeyPressed
        //System.out.println("Hola acaban de hacer enter");
        //String Sho = "SELECCIONAR nombre,edad EN hoas.asdsd FILTRAR asda=34;";
        updateCursorPosition();
        String oreo = consulta.getText();
          String consult=oreo.trim();

        if (consult.endsWith(";") && evt.getKeyCode()== 10) {
            try {
                System.out.println("consulta: "+consult);
                //
//
                SqlLexer flexe = new SqlLexer(new BufferedReader(new StringReader(consult)));
                parser pa = new parser(flexe);
                if(errors){
                    errors=false;
                    btnErrors.setVisible(false);
                    errores.setText("");
                }
                pa.parse();
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_consultaKeyPressed

    private void newProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newProjectActionPerformed

        nuevoProyect.setVisible(true);
//
//        }

    }//GEN-LAST:event_newProjectActionPerformed

    private void updateCursorPosition() {
        int caretPosition = consulta.getCaretPosition();
        int newLineNumber = consulta.getDocument().getDefaultRootElement().getElementIndex(caretPosition) + 1;

        if (!lineNumbers.contains(newLineNumber)) {
            lineNumbers.add(newLineNumber);
        }

        String lineNumbersText = buildLineNumbersText();
//        lineNumberLabel.setText("<html>" + lineNumbersText.replace("\n", "<br>") + "</html>");

        positionCode.setText("Position: " + newLineNumber + ", "
                + (caretPosition - consulta.getDocument().getDefaultRootElement().getElement(newLineNumber - 1).getStartOffset() + 1));
    }

    private String buildLineNumbersText() {
        StringBuilder sb = new StringBuilder();
        for (int lineNumber : lineNumbers) {
            sb.append(lineNumber).append("<br>");
        }
        return sb.toString();
    }
    private void btnErrorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnErrorsActionPerformed
        Errores er=new Errores();
        er.setVisible(true);
        er.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnErrorsActionPerformed

    public static DefaultMutableTreeNode cargarEstructuraProyecto(File idFile) {
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
    public static DefaultMutableTreeNode raiz;
    public static DefaultTreeModel modelo;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTree arbolDirectorio;
    public static javax.swing.JButton btnErrors;
    private javax.swing.JTextPane consulta;
    public static javax.swing.JTextPane errores;
    private javax.swing.JMenu file;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JMenuItem newProject;
    private javax.swing.JMenuItem openFile;
    private javax.swing.JPanel pane1;
    private javax.swing.JLabel positionCode;
    public static javax.swing.JTextField rutaConsole;
    public static javax.swing.JTabbedPane tabbed1;
    public static javax.swing.JTable tableResult;
    // End of variables declaration//GEN-END:variables
}
