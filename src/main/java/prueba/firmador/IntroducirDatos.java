package prueba.firmador;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;

/**
 * @author sergio
 */
public class IntroducirDatos extends javax.swing.JFrame {
    private String JSON = "";
    private Key clave;

    private javax.swing.JButton btnFicheroExterno;
    private javax.swing.JButton btnFirmaGenerica;
    private javax.swing.JButton btnFirmaCompacta;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTexto;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextPane txtPanelJWT;

    /**
     * Constructor con un parametro de tipo Key0
     *
     * @param clave La clave que utilizaremos para firmar el JWT
     */
    public IntroducirDatos(Key clave) {
        this.clave = clave;
        initComponents();
    }

    /**
     * Metodo utilizado para inicializar la interfaz gráfica, generar los action
     * listeners y los elementos
     */
    private void initComponents() {
        this.setResizable(false);
        jPanel1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        btnFirmaGenerica = new javax.swing.JButton();
        btnFirmaCompacta = new JButton();
        btnFicheroExterno = new javax.swing.JButton();
        lblTexto = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPanelJWT = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        lblTitulo.setFont(new java.awt.Font("Dialog", Font.BOLD, 36));
        lblTitulo.setText("Introduce tus datos");

        btnFirmaGenerica.setText("Firmar un VC-JWT");
        btnFirmaGenerica.setPreferredSize(new java.awt.Dimension(220, 25));
        btnFirmaGenerica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirmaGenericaActionPerformed();
            }
        });
        btnFirmaCompacta.setText("Firmar un JWS");
        btnFirmaCompacta.setPreferredSize(new java.awt.Dimension(220, 25));
        btnFirmaCompacta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirmaCompactaActionPerformed();
            }
        });

        btnFicheroExterno.setText("Cargar un fichero externo");
        btnFicheroExterno.setPreferredSize(new java.awt.Dimension(220, 25));
        btnFicheroExterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFicheroExternoActionPerformed();
            }
        });

        lblTexto.setForeground(new java.awt.Color(255, 255, 255));
        lblTexto.setText("Introduzca los datos que deseas añadir al payload en el siguiente cuadro o introduce un fichero JSON.");

        txtPanelJWT.setFocusable(true);
        jScrollPane1.setViewportView(txtPanelJWT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(212, 212, 212)
                                                .addComponent(lblTitulo))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(78, 78, 78)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblTexto)
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 739, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addComponent(btnFicheroExterno, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(105, 105, 105)
                                                                        .addComponent(btnFirmaGenerica)
                                                                        .addGap(105, 105, 105)
                                                                        .addComponent(btnFirmaCompacta))))))
                                .addContainerGap(96, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(lblTexto)
                                .addGap(12, 12, 12)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnFicheroExterno, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnFirmaGenerica, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnFirmaCompacta, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(59, 59, 59))
        );
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    /**
     * Metodo que comprueba si has añadido texto como payload:
     * <ol>
     * <li>Si no hay texto te pregunta si quieres firmar sin texto</li>
     * <li>Si hay texto lo utiliza como payload</li>
     * </ol>
     * Y una vez hecho ese paso llama al metodo de firmar con el json tratado para
     * que tenga formato
     */
    private void btnFirmaGenericaActionPerformed() {
        if (txtPanelJWT.getText().isEmpty() || txtPanelJWT.getText().isBlank()) {
            if (getOpcion() == JOptionPane.OK_OPTION) {
                btnFirmarGenericoActionPerformed();
            }
        } else {
            JSON = Libreria.tratarJsonTexto(txtPanelJWT.getText());
            if (comprobarNullJson("Texto vacio")) btnFirmarGenericoActionPerformed();
        }
    }

    private void btnFirmaCompactaActionPerformed() {
        if (txtPanelJWT.getText().isEmpty() || txtPanelJWT.getText().isBlank()) {
            if (getOpcion() == JOptionPane.OK_OPTION) {
                btnFirmarCompactoActionPerformed();
            }
        } else {
            JSON = Libreria.tratarJsonTexto(txtPanelJWT.getText());
            if (comprobarNullJson("Json mal formateado")) btnFirmarCompactoActionPerformed();
        }
    }

    private boolean comprobarNullJson(String jsonErroneo) {
        if (JSON == null) {
            JOptionPane.showConfirmDialog(null, "El JSON no es valido", jsonErroneo, JOptionPane.OK_CANCEL_OPTION);
            return false;
        }
        return true;
    }

    private static int getOpcion() {
        int opcion = JOptionPane.showConfirmDialog(null, "Estas intentando firmar el documento vacio, si es correcto dele a «Ok»", "Texto vacio", JOptionPane.OK_CANCEL_OPTION);
        return opcion;
    }


    /**
     * Metodo que abre un explorador de archivos para buscar en el sistema el
     * fichero con el contenido que necesitas, utiliza un filtro para mostrar solo
     * los ficheros .json, y una vez lo recibe guarda ese texto tratado en la
     * variable JSON
     */
    private void btnFicheroExternoActionPerformed() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccion del JSON");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Datos", "json");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            try(FileInputStream fis = new FileInputStream(f)){
                txtPanelJWT.setText(new String(fis.readAllBytes()));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Metodo que crea el token JWT firmandolo con la clave y añadiendo el JSON (El
     * cual puede ser una cadena vacia) y lo añade a tu portapapeles, para que
     * puedas pegarlo donde necesites
     */
    private void btnFirmarGenericoActionPerformed() {
        String JWT = Libreria.firmarJWT(this.clave, JSON);

        anadirPortapapeles(JWT);
    }

    private void btnFirmarCompactoActionPerformed() {
        String JWT = Libreria.firmarJWS(this.clave, JSON);

        anadirPortapapeles(JWT);
    }

    private static void anadirPortapapeles(String JWT) {
        StringSelection stringSelection = new StringSelection(JWT);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        
        JOptionPane.showMessageDialog(null, "El contenido del JWT se ha añadido a tu portapapeles", "Correcto", JOptionPane.INFORMATION_MESSAGE);
    }
}



