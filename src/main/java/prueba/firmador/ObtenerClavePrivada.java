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
public class ObtenerClavePrivada {
  
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
          System.out.println(
              "====================================================================================================================================================================");
          System.out.println(Base64.getEncoder().encodeToString(k.getEncoded()));
          System.out.println(
              "====================================================================================================================================================================");
        } else {
          throw new KeyStoreException("La clave privada no fue encontrada o no es una clave privada.");
        }
        System.out.println(
            "====================================================================================================================================================================");
        System.out.println(certificate);
        System.out.println(
            "====================================================================================================================================================================");

      }
    } catch (Exception e) {
      e.printStackTrace();
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
      // TODO Auto-generated catch block
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