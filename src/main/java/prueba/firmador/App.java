package prueba.firmador;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.Enumeration;


/**
 * Hello world!
 *
 */
public class App {
  public static void main(String[] args) throws IOException {
    File cert = new File("./cert.p12");

    try (FileInputStream fis = new FileInputStream(cert)) {
      KeyStore ks = KeyStore.getInstance("PKCS12");
      ks.load(fis, "prueba123".toCharArray());
      Enumeration<String> enumer = ks.aliases();
      while (enumer.hasMoreElements()) {
        String s = enumer.nextElement();
        System.out.println(s);
        java.security.cert.Certificate certificate = ks.getCertificate(s);
        Key k = ks.getKey("1", "prueba123".toCharArray());
         if (k instanceof PrivateKey) {
            k = (PrivateKey) k;
        } else {
            throw new KeyStoreException("La clave privada  no fue encontrada o no es una clave privada.");
        }
        System.out.println("====================================================================================================================================================================");
        System.out.println(Base64.getEncoder().encodeToString(k.getEncoded()));
        System.out.println("====================================================================================================================================================================");
        System.out.println(certificate);
        System.out.println("====================================================================================================================================================================");
        
      }
    } catch (Exception e) {e.printStackTrace();} 
  }
}
