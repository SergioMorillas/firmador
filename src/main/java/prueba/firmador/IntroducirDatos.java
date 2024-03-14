package prueba.firmador;

import java.io.File;
import java.security.Key;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

/**
 *
 * @author sergio
 */
public class IntroducirDatos extends javax.swing.JFrame {
    private String JSON = "";
    private Key clave;

    private javax.swing.JButton btnFicheroExterno;
    private javax.swing.JButton btnIntroducirTexto;
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

        jPanel1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        btnIntroducirTexto = new javax.swing.JButton();
        btnFicheroExterno = new javax.swing.JButton();
        lblTexto = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPanelJWT = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        lblTitulo.setFont(new java.awt.Font("Dialog", 3, 36));
        lblTitulo.setText("Introduce tus datos");

        btnIntroducirTexto.setText("Firmar");
        btnIntroducirTexto.setPreferredSize(new java.awt.Dimension(220, 25));
        btnIntroducirTexto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIntroducirTextoActionPerformed();
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
        lblTexto.setText(
                "Introduzca los datos que deseas añadir al payload en el siguiente cuadro o introduce un fichero JSON.");

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
                                                .addComponent(lblTitulo)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(78, 78, 78)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                jPanel1Layout.createSequentialGroup()
                                                                        .addComponent(btnFicheroExterno,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                        .addComponent(btnIntroducirTexto,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(lblTexto)
                                                                        .addComponent(jScrollPane1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                748,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(0, 0, Short.MAX_VALUE)))))
                                .addGap(85, 85, 85)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 64,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(lblTexto)
                                .addGap(12, 12, 12)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 177,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnFicheroExterno, javax.swing.GroupLayout.PREFERRED_SIZE, 57,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnIntroducirTexto, javax.swing.GroupLayout.PREFERRED_SIZE, 57,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)));

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
    private void btnIntroducirTextoActionPerformed() {
        if (txtPanelJWT.getText().isEmpty() || txtPanelJWT.getText().isBlank()) {
            int opcion = JOptionPane.showConfirmDialog(null,
                    "Estas intentando firmar el documento vacio, si es correcto dele a «Ok»",
                    "Texto vacio",
                    JOptionPane.OK_CANCEL_OPTION);
            if (opcion == JOptionPane.OK_OPTION) {
                btnFirmarActionPerformed();
            }
        } else {
            JSON = Libreria.tratarJsonTexto(txtPanelJWT.getText());
            btnFirmarActionPerformed();
        }
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
            JSON = Libreria.tratarJsonFichero(f);
            txtPanelJWT.setText(JSON);
        }
    }

    /**
     * Metodo que crea el token JWT firmandolo con la clave y añadiendo el JSON (El
     * cual puede ser una cadena vacia) y lo añade a tu portapapeles, para que
     * puedas pegarlo donde necesites
     */
    private void btnFirmarActionPerformed() {
        String JWT = Libreria.firmar(this.clave, JSON);

        StringSelection stringSelection = new StringSelection(JWT);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

        JOptionPane.showMessageDialog(null, "El contenido del JWT se ha añadido a tu portapapeles", "Correcto",
                JOptionPane.INFORMATION_MESSAGE);
    }
}



