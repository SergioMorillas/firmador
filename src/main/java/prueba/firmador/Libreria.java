package prueba.firmador;

import java.io.FileInputStream;
import java.security.MessageDigest;

import java.io.File;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.Enumeration;
import java.util.*;

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
            java.math.BigInteger number = new java.math.BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32)
                hashtext = "0".concat(hashtext);

            return hashtext;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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

    public static ArrayList<String> comprobarAlias(KeyStore ks) {
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

    public static Key clave(String alias, String contrasena, String rutaCert) {
        File cert = new File(rutaCert);
        Key k = null;
        try (FileInputStream fis = new FileInputStream(cert)) {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(fis, null);
            System.out.println("Datos a pelo: "
                    + Base64.getEncoder().encodeToString(ks.getKey(alias, contrasena.toCharArray()).getEncoded()));
            Enumeration<String> enumer = ks.aliases();
            while (enumer.hasMoreElements()) {
                String s = enumer.nextElement();
                System.out.println(s);
                k = ks.getKey(alias, contrasena.toCharArray());
                k.getAlgorithm();
                if (k instanceof PrivateKey) {
                    k = (PrivateKey) k;
                    System.out.println("Clave privada: " + Base64.getEncoder().encodeToString(k.getEncoded()));
                } else {
                    return null;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return k;
    }

    public static void main(String[] args) {
        clave("1", "prueba123", "./cert1.p12"); // Null pointer si el alias no existe en el getEncoded();
    }
}
