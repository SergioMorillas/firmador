package prueba.firmador;

import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.utils.JsonUtils;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtVisitor;
import io.jsonwebtoken.Jwts;
import jdk.jshell.spi.ExecutionControl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;

public class Libreria {
    /**
     * Metodo para cifrar una string a algoritmo SHA-256
     *
     * @param cadena Cadena de texto a cifrar
     * @return Una cadena de texto con el texto de «cadena» cifrado
     */
    public static String cifrar(String cadena) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(cadena.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32)
                hashtext = "0".concat(hashtext);

            return hashtext;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo que devuelve el KeyStore de los certificados del sistema, probado
     * principalmente en Windows
     *
     * @return El KeyStore por defecto que contiene los certificados del usuario del
     * sistema
     */
    public static KeyStore certificadosSistema() {
        try {
            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.contains("win")) {
                return KeyStore.getInstance("Windows-MY");
            } else if (osName.contains("mac")) {
                return KeyStore.getInstance("KeychainStore");
            } else {
                // Para sistemas basados en Unix/Linux, aquí se usa JKS como ejemplo
                return KeyStore.getInstance("JKS");
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Metodo que recibido un KeyStore comprueba los alias que contiene y los
     * devuelve
     *
     * @param ks El KeyStore del que puedes recibir los certificados
     * @return Un ArrayList de Strings que contiene los alias de un almacen
     */
    public static ArrayList<String> comprobarAlias(KeyStore ks) {

        Enumeration<String> enumer;
        ArrayList<String> lista = new ArrayList<String>();

        try {
            ks.load(null, null);

            enumer = ks.aliases();
            while (enumer.hasMoreElements()) {
                String s = enumer.nextElement();
                lista.add(s);
            }

        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Metodo que recibe un fichero, obtiene el certificado y devuelve los alias
     * contenidos en el mismo
     *
     * @param cert Fichero que contiene un certificado PKCS12
     * @return Un array list
     */
    public static ArrayList<String> comprobarAlias(File cert) {
        KeyStore ks = obtenerKeyStore(cert);

        Enumeration<String> enumer;
        ArrayList<String> lista = new ArrayList<String>();

        try {
            enumer = ks.aliases();
            while (enumer.hasMoreElements()) {
                String s = enumer.nextElement();
                lista.add(s);
            }

        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Metodo que devuelve la clave de un fichero dadas las credenciales
     *
     * @param alias      El alias del certificado
     * @param contrasena La contraseña del certificado
     * @param cert       El fichero que referencia al certificado
     * @return Un objeto tipo Key en caso de que el fichero y las credenciales
     * referenciasen a una clave privada, en caso de que la clave no fuese
     * correcta o no referenciase a una clave privada devolvera <b>null</b>
     */
    public static Key clave(String alias, String contrasena, File cert) {
        Key k = null;
        try (FileInputStream fis = new FileInputStream(cert)) {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(fis, null);
            k = ks.getKey(alias, contrasena.toCharArray());
            if (!(k instanceof PrivateKey)) return null;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return k;
    }

    /**
     * Metodo que obtiene la clave de un certificado del almacen del sistema dadas
     * las credenciales
     *
     * @param alias      El alias del certificado
     * @param contrasena La contraseña del certificado
     * @return Un objeto tipo Key en caso de que el fichero y las credenciales
     * referenciasen a una clave privada, en caso de que la clave no fuese
     * correcta o no referenciase a una clave privada devolvera <b>null</b>
     */
    public static Key clave(String alias, String contrasena) {
        Key k = null;
        try {
            KeyStore ks = certificadosSistema();
            ks.load(null, null);
            k = ks.getKey(alias, contrasena.toCharArray());
            if (!(k instanceof PrivateKey)) return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return k;
    }

    /**
     * Metodo que recibe un fichero JSON, formatea los datos y los devuelve en una
     * string manteniendo su formato correcto
     *
     * @param json El fichero con el JSON contenido en su interior
     * @return El JSON que contenia el fichero pero formateado
     */
    public static String tratarJsonFichero(File json) {
        String str = null;
        try (FileInputStream fis = new FileInputStream(json)) {
            Object jsonObject = JsonUtils.fromInputStream(fis);
            Object jsonNormalize = JsonLdProcessor.normalize(jsonObject);
            str = JsonUtils.toPrettyString(jsonNormalize);
        } catch (Exception e) {
        }
        return str;
    }

    /**
     * Metodo que recibe una cadena de texto con un fichero JSON y lo normaliza de
     * manera correcta
     *
     * @param json La cadena de texto que contiene el JSON que vamos a formatear
     * @return La cadena de texto que contiene el JSON formateado
     */
    public static String tratarJsonTexto(String json) {
        String str = null;
        try {
            Object jsonObject = JsonUtils.fromString(json);
            Object jsonNormalize = JsonLdProcessor.normalize(jsonObject);
            str = JsonUtils.toPrettyString(jsonNormalize);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return str;
    }

    /**
     * Metodo para obtener una KeyStore recibiendo un fichero
     *
     * @param cert El fichero que contiene el certificado
     * @return La KeyStore contenida en el certificado
     */
    private static KeyStore obtenerKeyStore(File cert) {
        KeyStore ks = null;
        try (FileInputStream fis = new FileInputStream(cert)) {
            ks = KeyStore.getInstance("PKCS12");
            ks.load(fis, null);
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException ex) {
            System.err.println("Problemas con el certificado");
        }
        return ks;
    }

    /**
     * Metodo para firmar la credenciar JWT con la clave especifica
     *
     * @param clave El objeto de tipo Key con el que queremos firmar
     * @param json  Una cadena que contiene el JSON que queremos añadir en el
     *              certificado, se puede pasar una cadena vacia si no quieres
     *              añadir nada
     * @return La firma AWT
     */
    public static String firmar(Key clave, String json) {
        json = cifrar(json);
        try {
            JWSHeader header = JWSHeader.parse("{\"alg\": \"ES256\",\n\"b64\": false,\n\"crit\": [\"b64\"]}");
            Payload p = new Payload(json);
            JWSObjectJSON jws = new JWSObjectJSON(p);
            JWSSigner signer = null;

            switch (clave.getAlgorithm()) {
                case "EC" -> signer = new ECDSASigner((ECPrivateKey) clave);
                case "RSA" -> signer = new RSASSASigner((RSAPrivateKey) clave);
                default -> throw new UnsupportedOperationException("Caracteristica no desarrollada, la clave debe ser EC o RSA");
            }
            jws.sign(header, signer);
            return (jws.toFlattenedJSONObject().get("protected") + ".." + jws.toFlattenedJSONObject().get("signature"));
        } catch (ParseException | JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public static String _firmar(Key clave, String json) {
        json = cifrar(json);
        String hb = Jwts.builder()
                .header()
                .add("alg", clave.getAlgorithm())
                .add("b64", false)
                .add("crit", "b64")
                .and()
                .content(json)
                .signWith(clave)
                .compact();
        return hb;
    }
}