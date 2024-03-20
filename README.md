# Firmador
Programa desarrollado en Java que acepta un fichero que contenga un certificado, extrae los alias disponibles para mostrarlos, y con uno de esos alias y una contraseña dada por el usuario extrae la clave privada para firmar un Token JWT.

Por ahora funciona en:
- Windows: A través de un fichero .p12
- Linux: A través de un fichero .p12 o JKS
- MacOS: A través de un fichero .p12 o el almacen de certificados del sistema

## Para pruebas
Las credenciales del certificado son:
- Alias: 1
- Contraseña: prueba123
- 
