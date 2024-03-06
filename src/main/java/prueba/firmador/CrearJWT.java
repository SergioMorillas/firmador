package prueba.firmador;

import io.jsonwebtoken.Jwts;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;

import com.github.jsonldjava.utils.JsonUtils;
import java.io.File;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.Enumeration;


public class CrearJWT {
    public static void main(String[] args) {
        // try (FileInputStream fis = new FileInputStream(new File("./fich.json"));) {

        //     String json = new String(fis.readAllBytes());

        //     Object jsonObject = JsonUtils.fromString(json);

        //     String str = JsonUtils.toPrettyString(jsonObject);
        //     str.equals("asdf");
        // } catch (FileNotFoundException e) {
        //     e.printStackTrace();
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }


        String hb = Jwts.builder()
                .header()
                    .add("alg", clave().getAlgorithm())
                    .add("b64", false)
                    .add("crit", "b64")
                .and()
                    .signWith(clave())
                .compact();
        System.out.println(hb);

    }

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

    public static KeyStore getKeyStore() {
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

    public static Key clave() {
        Key k = null;
        File cert = new File("./cert1.p12");

        try (FileInputStream fis = new FileInputStream(cert)) {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(fis, "prueba123".toCharArray());
            Enumeration<String> enumer = ks.aliases();
            while (enumer.hasMoreElements()) {
                String s = enumer.nextElement();
                System.out.println(s);
                java.security.cert.Certificate certificate = ks.getCertificate(s);
                k = ks.getKey("1", "prueba123".toCharArray());
                k.getAlgorithm();
                if (k instanceof PrivateKey) {
                    k = (PrivateKey) k;
                    System.out.println(
                            "========================================================================================================================================");
                    System.out.println(Base64.getEncoder().encodeToString(k.getEncoded()));
                    System.out.println(
                            "========================================================================================================================================");
                } else {
                    throw new KeyStoreException("La clave privada no fue encontrada o no es una clave privada.");
                }
                System.out.println(
                        "========================================================================================================================================");
                System.out.println(certificate);
                System.out.println(
                        "========================================================================================================================================");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return k;
    }

}
