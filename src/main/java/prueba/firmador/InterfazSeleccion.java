package prueba.firmador;

import java.awt.*;
import java.io.File;

import javax.swing.*;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Clase con la interfaz principal de la aplicación
 */
public class InterfazSeleccion extends JFrame {
    private JButton btnAlmacen;
    private JButton btnFichero;
    private JPanel jPanel1;
    private JLabel lblTexto;
    private JLabel lblTitulo;

    /**
     * Constructor vacio, es llamado por la función Main
     */
    public InterfazSeleccion() {
        initComponents();
    }

    /**
     * Metodo utilizado para inicializar la interfaz gráfica, generar los action
     * listeners y los elementos
     */
    private void initComponents() {
        this.setResizable(false);
        jPanel1 = new JPanel();
        lblTitulo = new JLabel();
        btnFichero = new JButton();
        btnAlmacen = new JButton();
        lblTexto = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        lblTitulo.setFont(new java.awt.Font("Dialog", Font.BOLD , 36));
        lblTitulo.setText("Firmador");
        // Generamos el boton para abrir los ficheros con el texto, el tamaño y la
        // acción que llama al metodo «accionesBotonFichero»
        btnFichero.setText("En un fichero");
        btnFichero.setPreferredSize(new java.awt.Dimension(220, 25));
        btnFichero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accionesBotonFichero();
            }
        });

        btnAlmacen.setText("Almacen de certificados");
        btnAlmacen.setPreferredSize(new java.awt.Dimension(220, 25));
        btnAlmacen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                busquedaCertificadosSistema();
            }
        });

        lblTexto.setForeground(new java.awt.Color(255, 255, 255));
        lblTexto.setText(
                "¿Donde se encuentran los certificados que quieres utilizar para  firmar el documento?");

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout
                                .createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)
                                .addComponent(lblTitulo)
                                .addGap(357, 357, 357))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(126, 126, 126)
                                .addGroup(jPanel1Layout.createParallelGroup(
                                        GroupLayout.Alignment.LEADING,
                                        false)
                                        .addGroup(jPanel1Layout
                                                .createSequentialGroup()
                                                .addComponent(btnAlmacen,
                                                        GroupLayout.PREFERRED_SIZE,
                                                        GroupLayout.DEFAULT_SIZE,
                                                        GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(
                                                        LayoutStyle.ComponentPlacement.RELATED,
                                                        GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE)
                                                .addComponent(btnFichero,
                                                        GroupLayout.PREFERRED_SIZE,
                                                        GroupLayout.DEFAULT_SIZE,
                                                        GroupLayout.PREFERRED_SIZE))
                                        .addComponent(lblTexto,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE))
                                .addGap(0, 153, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(lblTitulo)
                                .addGap(72, 72, 72)
                                .addComponent(lblTexto)
                                .addGap(42, 42, 42)
                                .addGroup(jPanel1Layout.createParallelGroup(
                                        GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnFichero,
                                                GroupLayout.PREFERRED_SIZE,
                                                57,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnAlmacen,
                                                GroupLayout.PREFERRED_SIZE,
                                                57,
                                                GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(114, Short.MAX_VALUE)));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE));

        pack();
    }

    /**
     * Metodo que instancia la interfaz para seleccionar el alias de un certificado
     * especifico y su contraseña en base a los certificados del sistema
     */
    private void busquedaCertificadosSistema() {
        DatosCertificado dc = new DatosCertificado(LibreriaGeneral.certificadosSistema());
        dc.setVisible(true);
        this.dispose();
    }

    /**
     * Metodo que abre un panel de selección de ficheros del sistema, con un filtro
     * para mostrar solo los certificados con la extension .p12 y genera la interfaz
     * para introducir los datos del alias en el certificado especifico que buscamos
     */
    private void accionesBotonFichero() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Certificados", "p12"); // Creamos el filtro
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            DatosCertificado dc = new DatosCertificado(f);
            dc.setVisible(true);
            this.dispose();
        }
    }

    /**
     * Metodo main, seleccionamos el «Look and Feel» como FlatDarkLaf, o en caso de
     * que no se pueda por algun problema del sistema añadiremos el tema Nimbus por
     * defecto
     */
    public static void main(String args[]) {

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            try {
                for (UIManager.LookAndFeelInfo info : UIManager
                        .getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(InterfazSeleccion.class.getName()).log(
                        java.util.logging.Level.SEVERE,
                        null, ex);
            }
        }
        //Metodo que ejecuta la interfaz como tal, para que sea visible y podamos trabajar sobre ella
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfazSeleccion().setVisible(true);
            }
        });
    }
}
