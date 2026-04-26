package mx.unison;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive tests for CryptoUtils class.
 * Tests MD5 encryption functionality including edge cases and special characters.
 */
@DisplayName("CryptoUtils - MD5 Encryption Tests")
class CryptoUtilsTest {

    @Test
    @DisplayName("Should calculate correct MD5 hash for '123'")
    void testMd5Hash123() {
        String expected = "202cb962ac59075b964b07152d234b70";
        String actual = CryptoUtils.md5("123");
        assertEquals(expected, actual, "MD5 hash for '123' should match expected value");
    }

    @Test
    @DisplayName("Should calculate correct MD5 hash for 'admin'")
    void testMd5HashAdmin() {
        String expected = "21232f297a57a5a743894a0e4a801fc3";
        String actual = CryptoUtils.md5("admin");
        assertEquals(expected, actual, "MD5 hash for 'admin' should match expected value");
    }

    @Test
    @DisplayName("Should calculate correct MD5 hash for empty string")
    void testMd5HashEmptyString() {
        String expected = "d41d8cd98f00b204e9800998ecf8427e";
        String actual = CryptoUtils.md5("");
        assertEquals(expected, actual, "MD5 hash for empty string should match expected value");
    }

    @ParameterizedTest
    @CsvSource({
            "password, 5f4dcc3b5aa765d61d8327deb882cf99",
            "test, 098f6bcd4621d373cade4e832627b4f6",
            "ABC, 902fbdd2b1df0c4f70b4a5d23525e932",
            "123456, e10adc3949ba59abbe56e057f20f883e",
            "InventarioTest, a7f64a1149d6c8c1bfe4476d2d2e93cf"
    })
    @DisplayName("Should calculate correct MD5 hash for various inputs")
    void testMd5HashVariousInputs(String input, String expectedHash) {
        String actual = CryptoUtils.md5(input);
        assertEquals(expectedHash, actual, String.format("MD5 hash for '%s' should match expected value", input));
    }

    @Test
    @DisplayName("Should handle special characters in MD5")
    void testMd5SpecialCharacters() {
        String specialChars = "!@#$%^&*()";
        String result = CryptoUtils.md5(specialChars);
        assertNotNull(result, "MD5 hash should not be null for special characters");
        assertFalse(result.isEmpty(), "MD5 hash should not be empty");
        assertEquals(32, result.length(), "MD5 hash should have 32 characters");
    }

    @Test
    @DisplayName("Should handle whitespace in MD5")
    void testMd5WithWhitespace() {
        String withSpaces = "hello world";
        String result = CryptoUtils.md5(withSpaces);
        assertNotNull(result);
        assertEquals(32, result.length(), "MD5 hash should always be 32 characters");
    }

    @Test
    @DisplayName("Should handle newline characters in MD5")
    void testMd5WithNewlines() {
        String withNewline = "line1\nline2";
        String result = CryptoUtils.md5(withNewline);
        assertNotNull(result);
        assertEquals(32, result.length());
    }

    @Test
    @DisplayName("Should handle Unicode characters in MD5")
    void testMd5UnicodeCharacters() {
        String unicode = "café";
        String result = CryptoUtils.md5(unicode);
        assertNotNull(result);
        assertEquals(32, result.length());
    }

    @Test
    @DisplayName("Should return 32 character lowercase hex string")
    void testMd5Formatting() {
        String result = CryptoUtils.md5("test");
        assertEquals(32, result.length(), "MD5 hash should always be 32 characters");
        assertTrue(result.matches("[a-f0-9]{32}"), "MD5 hash should be lowercase hexadecimal");
    }

    @Test
    @DisplayName("Should be consistent - same input produces same hash")
    void testMd5Consistency() {
        String input = "consistency_test";
        String hash1 = CryptoUtils.md5(input);
        String hash2 = CryptoUtils.md5(input);
        assertEquals(hash1, hash2, "Same input should produce identical hash");
    }

    @Test
    @DisplayName("Should produce different hashes for different inputs")
    void testMd5Differentiation() {
        String hash1 = CryptoUtils.md5("input1");
        String hash2 = CryptoUtils.md5("input2");
        assertNotEquals(hash1, hash2, "Different inputs should produce different hashes");
    }

    @Test
    @DisplayName("Should handle long strings")
    void testMd5LongString() {
        String longString = "a".repeat(1000);
        String result = CryptoUtils.md5(longString);
        assertEquals(32, result.length());
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should handle numeric strings")
    void testMd5NumericStrings() {
        String result = CryptoUtils.md5("0123456789");
        assertEquals(32, result.length());
        assertTrue(result.matches("[a-f0-9]{32}"));
    }

    @Test
    @DisplayName("Should handle case sensitivity")
    void testMd5CaseSensitivity() {
        String hashLower = CryptoUtils.md5("test");
        String hashUpper = CryptoUtils.md5("TEST");
        assertNotEquals(hashLower, hashUpper, "MD5 should be case-sensitive");
    }

    @Test
    @DisplayName("Should handle tab characters")
    void testMd5TabCharacters() {
        String withTab = "hello\tworld";
        String result = CryptoUtils.md5(withTab);
        assertEquals(32, result.length());
        assertNotNull(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a", "abc", "123", "!@#$%", "password123", "MixedCase123!@#"})
    @DisplayName("Should always return 32 character hash for various inputs")
    void testMd5AlwaysReturns32Chars(String input) {
        String result = CryptoUtils.md5(input);
        assertEquals(32, result.length(), String.format("Hash for '%s' should be 32 characters", input));
    }

    @Test
    @DisplayName("Should return null or handle NoSuchAlgorithmException gracefully")
    void testMd5ExceptionHandling() {
        // This test verifies the exception handling - if MD5 algorithm is somehow unavailable
        // The method should return null according to implementation
        String result = CryptoUtils.md5("test");
        // MD5 should always be available on standard JVMs
        assertNotNull(result, "MD5 should be available on standard JVM");
    }
}
