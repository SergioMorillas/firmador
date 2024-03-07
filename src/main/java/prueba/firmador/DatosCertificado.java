package prueba.firmador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.security.Key;
import java.security.KeyStore;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * Clase que recibe un certificado, añade todos los alias en un comboBox, pide
 * laa contraseña y pasa la Key del certificado a otra interfaz (Introducir
 * datos).
 * Esta clase al no tener main se podría añadir el Look and Feel en el
 * constructor o en el initComponents, pero no es necesario ya que por defecto
 * utilizan el que posee la clase que la ha llamado
 */
public class DatosCertificado extends javax.swing.JFrame {
    /**
     * Creamos un objeto certificado, que solo puede ser o File o KeyStore, y luego
     * trabajaremos sobre ello
     */
    private Object certificado = null;

    private javax.swing.JButton btnAceptar;
    private javax.swing.JComboBox<String> comboAlias;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblErrores;
    private javax.swing.JLabel lblNombreCertificado;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPasswordField txtContraseña;

    /**
     * Constructor con un parametro de tipo file, se encarga de setear el
     * certificado de la clase, añadir el nombre al titulo de la interfaz y a un
     * label, ademas de rellenar un combobox con todos los aliases del certificado
     * 
     * @param certificado Objeto de tipo File que contiene el certificado sobre el
     *                    que trabajaremos
     */
    public DatosCertificado(File certificado) {
        this.certificado = certificado;
        initComponents();
        lblNombreCertificado.setText(certificado.getName());
        ArrayList<String> lista = Libreria.comprobarAlias(certificado);

        comboAlias.setModel(llenarCombo(lista));
    }

    /**
     * Constructor con un parametro de tipo KeyStore, se encarga de setear el
     * certificado de la clase, añadir el nombre al titulo de la interfaz y a un
     * label, ademas de rellenar un combobox con todos los aliases del certificado
     * 
     * @param certificado Objeto de tipo KeyStore que contiene el certificado sobre
     *                    el que trabajaremos
     */

    public DatosCertificado(KeyStore certificado) {
        this.certificado = certificado;
        initComponents();
        lblNombreCertificado.setText("Certificados del sistema");
        ArrayList<String> lista = Libreria.comprobarAlias(certificado);

        comboAlias.setModel(llenarCombo(lista));
    }

    /**
     * Metodo utilizado para inicializar la interfaz gráfica, generar los action
     * listeners y los elementos
     */
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblNombreCertificado = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        comboAlias = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtContraseña = new javax.swing.JPasswordField();
        btnAceptar = new javax.swing.JButton();
        lblErrores = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        lblTitulo.setFont(new java.awt.Font("Dialog", 3, 36));
        lblTitulo.setText("Certificado: ");

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 24));
        jLabel1.setText("Contraseña:");

        comboAlias.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 24));
        jLabel2.setText("Alias: ");

        txtContraseña.setText("");
        txtContraseña.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt){
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnAceptarActionPerformed(); //En caso de que pulses ener en la contraseña tendrá el mismo efecto que pulsar el boton de «Aceptar»
                }
            }
        });
        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                btnAceptarActionPerformed();
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(83, 83, 83)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(lblTitulo)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(lblNombreCertificado,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 359,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(6, 6, 6)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                                        false)
                                                                        .addComponent(lblErrores,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                .addGroup(jPanel1Layout
                                                                                        .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(jLabel2)
                                                                                        .addComponent(jLabel1))
                                                                                .addGap(44, 44, 44)
                                                                                .addGroup(jPanel1Layout
                                                                                        .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                false)
                                                                                        .addComponent(comboAlias, 0,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                        .addComponent(txtContraseña,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                347,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(274, 274, 274)
                                                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 144,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 14, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(jPanel1Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblNombreCertificado, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(comboAlias, javax.swing.GroupLayout.PREFERRED_SIZE, 29,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(36, 36, 36)
                                .addGroup(jPanel1Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtContraseña))
                                .addGap(29, 29, 29)
                                .addComponent(lblErrores, javax.swing.GroupLayout.PREFERRED_SIZE, 46,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 44,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
    }

    /**
     * Metodo que intenta obtener la clave de los datos introducidos (alias y
     * contraseña), y en caso de que la clave se haya podido recuperar instancia la
     * ultima interfaz que necesitamos, la interfaz para introducir los datos al
     * firmador
     */
    private void btnAceptarActionPerformed() {
        Key clave = null;
        String contr = new String(txtContraseña.getPassword());
        String alias = (String) comboAlias.getSelectedItem();

        if (certificado instanceof File) {
            clave = Libreria.clave(alias, contr, (File) certificado);
        } else {
            clave = Libreria.clave(alias, contr);
        }
        if (clave == null) {
            JOptionPane.showMessageDialog(null,
                    "La contraseña que has introducido no es correcta o no hay una clave privada valida",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            IntroducirDatos id = new IntroducirDatos(clave);
            id.setVisible(true);
            this.dispose();
        }

    }

    /**
     * Metodo que pasado un ArrayList de Strings rellena un
     * DefaultComboBoxModel<String>, que sirve para rellenar un combo box con los
     * datos de ese arraylist
     * 
     * @param lista El arraylist que contiene los datos
     * @return El DefaultComboBoxModel con los mismos datos que el arraylist
     */
    private DefaultComboBoxModel<String> llenarCombo(ArrayList<String> lista) {
        DefaultComboBoxModel<String> dml = new DefaultComboBoxModel<String>();
        for (String str : lista) {
            dml.addElement(str);
        }
        return dml;
    }
}
