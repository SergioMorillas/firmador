package prueba.firmador;


import java.security.KeyStore;
import java.security.KeyStoreException;

public class LibreriaGeneral {


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





}