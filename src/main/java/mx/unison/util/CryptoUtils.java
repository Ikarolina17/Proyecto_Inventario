package mx.unison.util;

import java.security.MessageDigest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilidades de criptografía para hash de contraseñas
 */
public class CryptoUtils {
    private static final Logger logger = LoggerFactory.getLogger(CryptoUtils.class);

    /**
     * Genera hash MD5 de una cadena
     */
    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            logger.error("Error al generar hash MD5", e);
            return null;
        }
    }

    /**
     * Verifica que una contraseña coincida con su hash
     */
    public static boolean verificarPassword(String password, String hash) {
        String hashIngresado = md5(password);
        return hashIngresado != null && hashIngresado.equals(hash);
    }
}
