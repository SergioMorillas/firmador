use keyring;
use std::ptr;
use winapi::um::wincrypt::{CryptAcquireCertificatePrivateKey, CRYPT_ACQUIRE_ALLOW_NCRYPT_KEY_FLAG};
use winapi::um::wincrypt::{CryptDestroyKey, CryptGetUserKey, HCRYPTPROV, HCRYPTKEY, PROV_RSA_FULL};
use winapi::um::wincrypt::{CertCloseStore, CertFindCertificateInStore, CertOpenSystemStoreW, HCERTSTORE};
use winapi::um::wincrypt::{CERT_FIND_SUBJECT_STR, CERT_SYSTEM_STORE_CURRENT_USER, CERT_STORE_PROV_SYSTEM};


fn get_private_key(alias: &str, password: &str) -> Option<Vec<u8>> {
    let store_name = "MY"; 
    let system_store = unsafe {
        CertOpenSystemStoreW(
            ptr::null_mut(),
            store_name.encode_utf16().collect::<Vec<u16>>().as_ptr(),
        )
    };
    if system_store.is_null() {
        return None;
    }

    let cert_context = unsafe {
        CertFindCertificateInStore(
            system_store,
            winapi::um::wincrypt::X509_ASN_ENCODING,
            0,
            CERT_FIND_SUBJECT_STR,
            alias.as_ptr() as *const _,
            ptr::null_mut(),
        )
    };
    if cert_context.is_null() {
        unsafe {
            CertCloseStore(system_store, 0);
        }
        return None;
    }

    let mut crypt_prov: HCRYPTPROV = ptr::null_mut();
    let mut crypt_key: HCRYPTKEY = ptr::null_mut();

    let result = unsafe {
        CryptAcquireCertificatePrivateKey(
            cert_context,
            CRYPT_ACQUIRE_ALLOW_NCRYPT_KEY_FLAG,
            ptr::null_mut(),
            &mut crypt_prov,
            ptr::null_mut(),
            ptr::null_mut(),
        )
    };
    if result == 0 {
        unsafe {
            CertCloseStore(system_store, 0);
            CertFreeCertificateContext(cert_context);
        }
        return None;
    }

    let mut key_size: u32 = 0;
    let mut key_size_len = std::mem::size_of::<u32>() as u32;
    unsafe {
        CryptGetUserKey(
            crypt_prov,
            AT_KEYEXCHANGE,
            &mut crypt_key,
        );
        CryptDestroyKey(crypt_key);
        CryptReleaseContext(crypt_prov, 0);
        CertFreeCertificateContext(cert_context);
        CertCloseStore(system_store, 0);
    }

    Some(vec![]) 
}

fn main() {
    let alias = "1";
    let password = "prueba123";

    match get_private_key(alias, password) {
        Some(private_key) => {
            println!("Private key: {:?}", private_key);
        }
        None => {
            println!("Error al recibir la private key.");
        }
    }
}