package prueba.firmador;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.formdev.flatlaf.FlatDarkLaf;

public class InterfazSeleccion extends javax.swing.JFrame {

    public InterfazSeleccion() {
        initComponents();
    }

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        btnFichero = new javax.swing.JButton();
        btnAlmacen = new javax.swing.JButton();
        lblTexto = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        lblTitulo.setFont(new java.awt.Font("Dialog", 3, 36)); 
        lblTitulo.setText("Firmador");

        btnFichero.setText("En un fichero");
        btnFichero.setPreferredSize(new java.awt.Dimension(220, 25));
        btnFichero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accionesBotonFichero(evt);
            }
        });

        btnAlmacen.setText("Almacen de certificados");
        btnAlmacen.setPreferredSize(new java.awt.Dimension(220, 25));
        btnAlmacen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                busquedaCertificadosSistema(evt);
            }
        });

        lblTexto.setForeground(new java.awt.Color(255, 255, 255));
        lblTexto.setText(
                "¿Donde se encuentran los certificados que quieres utilizar para  firmar el documento?");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout
                                .createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)
                                .addComponent(lblTitulo)
                                .addGap(357, 357, 357))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(126, 126, 126)
                                .addGroup(jPanel1Layout.createParallelGroup(
                                        javax.swing.GroupLayout.Alignment.LEADING,
                                        false)
                                        .addGroup(jPanel1Layout
                                                .createSequentialGroup()
                                                .addComponent(btnAlmacen,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(
                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE)
                                                .addComponent(btnFichero,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(lblTexto,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE))
                                .addGap(0, 153, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(lblTitulo)
                                .addGap(72, 72, 72)
                                .addComponent(lblTexto)
                                .addGap(42, 42, 42)
                                .addGroup(jPanel1Layout.createParallelGroup(
                                        javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnFichero,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                57,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnAlmacen,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                57,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(114, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.PREFERRED_SIZE));

        pack();
    }

    private void busquedaCertificadosSistema(java.awt.event.ActionEvent evt) {
        DatosCertificado dc = new DatosCertificado(Libreria.certificadosSistema());
        dc.setVisible(true);
        this.dispose();
    }

    private void accionesBotonFichero(java.awt.event.ActionEvent evt) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Certificados", "p12");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            DatosCertificado dc = new DatosCertificado(f);
            dc.setVisible(true);
            this.dispose();
        }
    }

    public static void main(String args[]) {

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                        .getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(InterfazSeleccion.class.getName()).log(
                        java.util.logging.Level.SEVERE,
                        null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(InterfazSeleccion.class.getName()).log(
                        java.util.logging.Level.SEVERE,
                        null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(InterfazSeleccion.class.getName()).log(
                        java.util.logging.Level.SEVERE,
                        null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(InterfazSeleccion.class.getName()).log(
                        java.util.logging.Level.SEVERE,
                        null, ex);
            }
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfazSeleccion().setVisible(true);
            }
        });
    }

    private javax.swing.JButton btnAlmacen;
    private javax.swing.JButton btnFichero;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblTexto;
    private javax.swing.JLabel lblTitulo;
}
