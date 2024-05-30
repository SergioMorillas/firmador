package prueba.firmador;

import com.apicatalog.jsonld.JsonLd;
import com.apicatalog.jsonld.JsonLdError;
import com.apicatalog.jsonld.document.Document;
import com.apicatalog.jsonld.document.JsonDocument;
import com.apicatalog.rdf.RdfNQuad;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.util.Base64URL;
import io.jsonwebtoken.Jwts;
import net.minidev.json.JSONObject;

import java.io.Reader;
import java.io.StringReader;
import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.*;

public class LibreriaAnadirJSON {
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
     * Metodo para firmar la credenciar JWS con la clave especifica
     *
     * @param clave El objeto de tipo Key con el que queremos firmar
     * @param json  Una cadena que contiene el JSON que queremos añadir en el
     *              certificado, se puede pasar una cadena vacia si no quieres
     *              añadir nada
     * @return La firma AWT
     */
    public static String firmarJWS(Key clave, String json) {
        json = cifrar(json);
        try {
            // Si no lo pones en b64 la cabecera aparece desordenada
            JWSHeader header = JWSHeader.parse(new Base64URL("eyJhbGciOiJQUzI1NiIsImI2NCI6ZmFsc2UsImNyaXQiOlsiYjY0Il19"));
            Payload p = new Payload(json);
            JWSObjectJSON jws = new JWSObjectJSON(p);
            JWSSigner signer = null;
            switch (clave.getAlgorithm()) {
                case "EC" -> signer = new ECDSASigner((ECPrivateKey) clave);
                case "RSA" -> signer = new RSASSASigner((RSAPrivateKey) clave);
                default ->
                        throw new UnsupportedOperationException("Caracteristica no desarrollada, la clave debe ser EC o RSA");
            }
            jws.sign(header, signer);
            JOSEObject jose = new JWSObject(header, p);
            System.out.println(signer.sign(header, p.toBytes()));
            System.out.println(Arrays.toString(jose.getParsedParts()));
            System.out.println((jws.toFlattenedJSONObject()));
            System.out.println((jws.toGeneralJSONObject()));
            String credencialJWS = (jws.toFlattenedJSONObject().get("protected") + ".." + jws.toFlattenedJSONObject().get("signature"));
            return obtenerProof(credencialJWS);
        } catch (ParseException | JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public static String firmarJWT(Key clave, String json) {
        String hb;
        json = cifrar(json);
        hb = Jwts.builder()
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
            Reader reader = new StringReader(json);
            Document document = JsonDocument.of(reader);
            StringBuilder sb = new StringBuilder();
            List<RdfNQuad> lista = null;
            lista = JsonLd.toRdf(document).get().toList();
            Collections.reverse(lista);
            lista.forEach(sb::append);

            str = sb.toString();

        } catch (JsonLdError e) {
            System.err.println(e.getMessage());
            System.err.println(Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
        }
        return str;
    }
    public static String obtenerProof(String credencialJWS){
        String ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        SimpleDateFormat dateFormat = new SimpleDateFormat(ISO_8601);
        StringBuilder sb = new StringBuilder("\"proof\":{\n");

        sb.append("\t\"type\":\"").append("JsonWebSignature2020").append("\",\n");
        sb.append("\t\"created\":\"").append(dateFormat.format(Calendar.getInstance().getTime())).append("\",\n");
        sb.append("\t\"proofPurpose\":\"").append("assertionMethod").append("\",\n");
        sb.append("\t\"verificationMethod\":\"").append("did:web:arlabdevelopments.com").append("\",\n");
        sb.append("\t\"jws\":\"").append(credencialJWS).append("\",\n}");
    return sb.toString();
    }
}
