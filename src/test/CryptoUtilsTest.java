package mx.unison;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CryptoUtilsTest {
    @Test
    void testMd5Hashing() {
        String pass = "admin23";
        String hash = CryptoUtils.md5(pass);
        
        assertNotNull(hash);
        assertNotEquals(pass, hash); // La clave no debe ser igual al hash
        assertEquals(hash, CryptoUtils.md5(pass)); // El hash debe ser determinista
    }
}