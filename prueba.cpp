#include <iostream>
#include <windows.h>
#include <Wincrypt.h>

#pragma comment (lib, "crypt32.lib") // Comentario para incluir la libreria crypt32.lib

std::vector<unsigned char> getPrivateKey(const std::wstring& alias, const std::wstring& password) {
    std::vector<unsigned char> clavePrivada; //Vector de bytes que almacena la clave privada 

    HCERTSTORE hCertStore = CertOpenSystemStoreW(NULL, L"MY"); //Abrimos el almacen de certificados del usuario, como KeyStore.getInstance("Windows-MY") 
    if (!hCertStore) {
        std::cerr << "Error al abrir el almacen de certificados." << std::endl; // Mostramos el error y devolvemos el vector vacio
        return clavePrivada;
    }

    PCCERT_CONTEXT contextoCertificado = CertFindCertificateInStore( // Buscamos los certificados que hay en el sistema, con sus alias
        hCertStore, // El almacen de certificados del sistema
        X509_ASN_ENCODING | PKCS_7_ASN_ENCODING, // Los formatos de encode, certificado x509 y pkcs
        0, // Flags, 0 es que no tenemos
        CERT_FIND_SUBJECT_STR, // Buscar los certificados
        (LPVOID)alias.c_str(), // Alias del certificado 
        NULL // Contexto
    );
    if (!contextoCertificado) { // Si no tiene contexto, si no devuelve el certificado lo cierra
        std::cerr << "Certificado no encontrado." << std::endl;
        CertCloseStore(hCertStore, 0); 
        return clavePrivada;
    }

    HCRYPTPROV proveedor = NULL; // Creamos el provider
    if (!CryptAcquireContext(&proveedor, NULL, NULL, PROV_RSA_FULL, CRYPT_VERIFYCONTEXT)) { // Accedemos al contexto de la clave, en caso de que no se pueda cerramos los almacenes 
        std::cerr << "Error con el contexto del certificado." << std::endl;
        CertFreeCertificateContext(contextoCertificado);
        CertCloseStore(hCertStore, 0);
        return clavePrivada;
    }

    DWORD dwKeySpec;
    BOOL resultado = CryptAcquireCertificatePrivateKey(  // Obtenemos una variable booleana que nos dice si se ha podido acceder a la clave privada con el contexto, el provider y las especificaciones
        contextoCertificado,
        0,
        NULL,
        &hCryptProv,
        &dwKeySpec,
        NULL
    );
    if (!resultado) { // Si no ha funcionado salimos del programa
        std::cerr << "Error al recibir la clave privada del certificado." << std::endl;
        CertFreeCertificateContext(contextoCertificado);
        CryptReleaseContext(hCryptProv, 0);
        CertCloseStore(hCertStore, 0);
        return clavePrivada;
    }
    BOOL existeClave = CryptExportKey(
        (LPVOID)password.c_str(),
        NULL,
        PRIVATEKEYBLOB,
        0x00000001,
        clavePrivada, //Pasamos el vector con la clave privada como parametro in/out

    );
    if (!existeClave) {
        std::cout << "Se ha almacenado la clave privada del certificado" << std::endl;
    }
    CryptReleaseContext(hCryptProv, 0); // Cerramos todo lo que hemos abierto
    CertFreeCertificateContext(contextoCertificado);
    CertCloseStore(hCertStore, 0);

    return clavePrivada;
}

int main() {
    std::wstring alias = L"1";
    std::wstring password = L"password123";

    std::vector<unsigned char> clavePrivada = getPrivateKey(alias, password);
    if (!clavePrivada.empty()) {
        std::cout << "Clave privada almacenada correctamente" << std::endl;
    } else {
        std::cerr << "Error al almacenar la clave privada." << std::endl;
    }
    return 0;
}
int main(int argc, char *argv[]) {
    std::wstring alias = argv[1];
    std::wstring password = argv[2];

    std::vector<unsigned char> clavePrivada = getPrivateKey(alias, password);
    if (!clavePrivada.empty()) {
        std::cout << "Clave privada almacenada correctamente" << std::endl;
    } else {
        std::cerr << "Error al almacenar la clave privada." << std::endl;
    }
    return 0;
}
