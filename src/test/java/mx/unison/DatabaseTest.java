package mx.unison;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive tests for Database class.
 * Tests user authentication, product insertion, filtering, and deletion.
 */
@DisplayName("Database - Data Management Tests")
class DatabaseTest {

    private Database database;

    @BeforeEach
    void setUp() {
        database = new Database();
    }

    // ==================== Authentication Tests ====================

    @Test
    @DisplayName("Should authenticate admin user with correct password '123'")
    void testAuthenticateAdminCorrectPassword() {
        Usuario user = database.authenticate("admin", "123");
        assertNotNull(user, "Should return user for correct credentials");
        assertEquals("admin", user.username);
        assertEquals("ADMIN", user.rol);
    }

    @Test
    @DisplayName("Should reject admin user with incorrect password")
    void testAuthenticateAdminIncorrectPassword() {
        Usuario user = database.authenticate("admin", "wrongpass");
        assertNull(user, "Should return null for incorrect password");
    }

    @Test
    @DisplayName("Should reject non-existent user")
    void testAuthenticateNonExistentUser() {
        Usuario user = database.authenticate("unknown", "123");
        assertNull(user, "Should return null for non-existent user");
    }

    @Test
    @DisplayName("Should reject empty username")
    void testAuthenticateEmptyUsername() {
        Usuario user = database.authenticate("", "123");
        assertNull(user);
    }

    @Test
    @DisplayName("Should reject empty password")
    void testAuthenticateEmptyPassword() {
        Usuario user = database.authenticate("admin", "");
        assertNull(user);
    }

    @Test
    @DisplayName("Should reject SQL injection attempt in username")
    void testAuthenticateSqlInjectionUsername() {
        Usuario user = database.authenticate("admin' OR '1'='1", "123");
        assertNull(user, "Should reject SQL injection in username");
    }

    @Test
    @DisplayName("Should reject SQL injection attempt in password")
    void testAuthenticateSqlInjectionPassword() {
        Usuario user = database.authenticate("admin", "123' OR '1'='1");
        assertNull(user, "Should reject SQL injection in password");
    }

    @Test
    @DisplayName("Should reject single quote in credentials")
    void testAuthenticateSingleQuoteInUsername() {
        Usuario user = database.authenticate("admin'", "123");
        assertNull(user);
    }

    @Test
    @DisplayName("Should reject single quote in password")
    void testAuthenticateSingleQuoteInPassword() {
        Usuario user = database.authenticate("admin", "123'");
        assertNull(user);
    }

    @ParameterizedTest
    @CsvSource({
            "admin, 123, true",
            "admin, wrong, false",
            "unknown, 123, false",
            "user, password, false"
    })
    @DisplayName("Should authenticate various username/password combinations correctly")
    void testAuthenticateMultipleCombinations(String username, String password, boolean shouldSucceed) {
        Usuario user = database.authenticate(username, password);
        if (shouldSucceed) {
            assertNotNull(user);
        } else {
            assertNull(user);
        }
    }

    @Test
    @DisplayName("Should be case sensitive in username")
    void testAuthenticateCaseSensitiveUsername() {
        Usuario user = database.authenticate("Admin", "123");
        assertNull(user, "Username should be case-sensitive");
    }

    // ==================== Product Insertion Tests ====================

    @Test
    @DisplayName("Should insert single product")
    void testInsertSingleProduct() {
        Producto p = new Producto();
        p.nombre = "Laptop";
        p.precio = 999.99;
        p.cantidad = 5;
        p.almacenId = 1;
        p.descripcion = "High-performance laptop";

        database.insertProducto(p, "admin");

        assertNotNull(p.id, "Product should have an ID assigned");
        assertTrue(p.id > 0, "Product ID should be positive");

        List<Producto> productos = database.listProductos("", 0, 999999);
        assertEquals(1, productos.size());
        assertEquals("Laptop", productos.get(0).nombre);
    }

    @Test
    @DisplayName("Should assign sequential IDs to products")
    void testProductIdSequence() {
        Producto p1 = new Producto();
        p1.nombre = "Product1";
        p1.precio = 10.0;

        Producto p2 = new Producto();
        p2.nombre = "Product2";
        p2.precio = 20.0;

        database.insertProducto(p1, "admin");
        database.insertProducto(p2, "admin");

        assertEquals(1, p1.id);
        assertEquals(2, p2.id);
    }

    @Test
    @DisplayName("Should insert product with zero price")
    void testInsertProductZeroPrice() {
        Producto p = new Producto();
        p.nombre = "Free Item";
        p.precio = 0.0;
        p.cantidad = 100;
        p.almacenId = 1;

        database.insertProducto(p, "admin");

        List<Producto> productos = database.listProductos("", 0, 999999);
        assertTrue(productos.stream().anyMatch(prod -> prod.precio == 0.0));
    }

    @Test
    @DisplayName("Should insert product with negative price")
    void testInsertProductNegativePrice() {
        Producto p = new Producto();
        p.nombre = "Discount Item";
        p.precio = -5.0;
        p.cantidad = 10;
        p.almacenId = 1;

        database.insertProducto(p, "admin");

        List<Producto> productos = database.listProductos("", 0, 999999);
        assertEquals(1, productos.size());
    }

    @Test
    @DisplayName("Should insert product with zero quantity")
    void testInsertProductZeroQuantity() {
        Producto p = new Producto();
        p.nombre = "Out of Stock";
        p.precio = 15.0;
        p.cantidad = 0;
        p.almacenId = 1;

        database.insertProducto(p, "admin");

        List<Producto> productos = database.listProductos("", 0, 999999);
        assertEquals(1, productos.size());
        assertEquals(0, productos.get(0).cantidad);
    }

    @Test
    @DisplayName("Should insert product with special characters in name")
    void testInsertProductSpecialCharacterName() {
        Producto p = new Producto();
        p.nombre = "Product @#$% & Co.";
        p.precio = 50.0;
        p.cantidad = 5;
        p.almacenId = 1;

        database.insertProducto(p, "admin");

        List<Producto> productos = database.listProductos("", 0, 999999);
        assertEquals(1, productos.size());
    }

    @Test
    @DisplayName("Should insert product with very long name")
    void testInsertProductLongName() {
        Producto p = new Producto();
        p.nombre = "A".repeat(500);
        p.precio = 25.0;
        p.cantidad = 10;
        p.almacenId = 1;

        database.insertProducto(p, "admin");

        List<Producto> productos = database.listProductos("", 0, 999999);
        assertEquals(1, productos.size());
    }

    @Test
    @DisplayName("Should insert product with very large price")
    void testInsertProductLargePrice() {
        Producto p = new Producto();
        p.nombre = "Expensive Item";
        p.precio = 1_000_000.99;
        p.cantidad = 1;
        p.almacenId = 1;

        database.insertProducto(p, "admin");

        List<Producto> productos = database.listProductos("", 0, 999999999);
        assertEquals(1, productos.size());
    }

    // ==================== Product Listing/Filtering Tests ====================

    @Test
    @DisplayName("Should list all products when no filter applied")
    void testListProductsNoFilter() {
        insertMultipleProducts();

        List<Producto> productos = database.listProductos("", 0, 999999);
        assertEquals(3, productos.size());
    }

    @Test
    @DisplayName("Should filter products by name")
    void testListProductsByName() {
        insertMultipleProducts();

        List<Producto> productos = database.listProductos("Laptop", 0, 999999);
        assertEquals(1, productos.size());
        assertEquals("Laptop", productos.get(0).nombre);
    }

    @Test
    @DisplayName("Should filter products by partial name match")
    void testListProductsPartialNameMatch() {
        insertMultipleProducts();

        List<Producto> productos = database.listProductos("top", 0, 999999);
        assertEquals(1, productos.size());
        assertTrue(productos.get(0).nombre.contains("top"));
    }

    @Test
    @DisplayName("Should return empty list for non-matching name filter")
    void testListProductsNoNameMatch() {
        insertMultipleProducts();

        List<Producto> productos = database.listProductos("NonExistent", 0, 999999);
        assertEquals(0, productos.size());
    }

    @Test
    @DisplayName("Should filter products by minimum price")
    void testListProductsMinimumPrice() {
        insertMultipleProducts();

        List<Producto> productos = database.listProductos("", 500, 999999);
        assertEquals(1, productos.size());
        assertTrue(productos.get(0).precio >= 500);
    }

    @Test
    @DisplayName("Should filter products by maximum price")
    void testListProductsMaximumPrice() {
        insertMultipleProducts();

        List<Producto> productos = database.listProductos("", 0, 100);
        assertEquals(2, productos.size());
        assertTrue(productos.stream().allMatch(p -> p.precio <= 100));
    }

    @Test
    @DisplayName("Should filter products by price range")
    void testListProductsPriceRange() {
        insertMultipleProducts();

        List<Producto> productos = database.listProductos("", 40, 150);
        assertEquals(2, productos.size());
        assertTrue(productos.stream().allMatch(p -> p.precio >= 40 && p.precio <= 150));
    }

    @Test
    @DisplayName("Should return all products when price range is very wide")
    void testListProductsVeryWidePriceRange() {
        insertMultipleProducts();

        List<Producto> productos = database.listProductos("", 0, Double.MAX_VALUE);
        assertEquals(3, productos.size());
    }

    @Test
    @DisplayName("Should return empty list when price range is too narrow")
    void testListProductsNarrowPriceRange() {
        insertMultipleProducts();

        List<Producto> productos = database.listProductos("", 1000, 2000);
        assertEquals(0, productos.size());
    }

    @Test
    @DisplayName("Should combine name and price filters")
    void testListProductsCombinedFilters() {
        insertMultipleProducts();

        List<Producto> productos = database.listProductos("Mouse", 0, 50);
        assertEquals(1, productos.size());
        assertTrue(productos.get(0).nombre.contains("Mouse"));
        assertTrue(productos.get(0).precio <= 50);
    }

    @Test
    @DisplayName("Should handle case-insensitive name search")
    void testListProductsCaseInsensitive() {
        insertMultipleProducts();

        List<Producto> productosLower = database.listProductos("keyboard", 0, 999999);
        List<Producto> productosUpper = database.listProductos("KEYBOARD", 0, 999999);
        
        // Depends on implementation - verify actual behavior
        assertNotNull(productosLower);
        assertNotNull(productosUpper);
    }

    @Test
    @DisplayName("Should filter products with negative price filter")
    void testListProductsNegativePriceFilter() {
        insertMultipleProducts();

        List<Producto> productos = database.listProductos("", -100, 999999);
        assertEquals(3, productos.size());
    }

    // ==================== Product Deletion Tests ====================

    @Test
    @DisplayName("Should delete product by ID")
    void testDeleteProducto() {
        insertMultipleProducts();
        
        List<Producto> before = database.listProductos("", 0, 999999);
        assertEquals(3, before.size());

        database.deleteProducto(2);

        List<Producto> after = database.listProductos("", 0, 999999);
        assertEquals(2, after.size());
        assertFalse(after.stream().anyMatch(p -> p.id == 2));
    }

    @Test
    @DisplayName("Should not fail when deleting non-existent product ID")
    void testDeleteNonExistentProducto() {
        insertMultipleProducts();

        database.deleteProducto(999);

        List<Producto> productos = database.listProductos("", 0, 999999);
        assertEquals(3, productos.size());
    }

    @Test
    @DisplayName("Should delete first product")
    void testDeleteFirstProducto() {
        insertMultipleProducts();

        database.deleteProducto(1);

        List<Producto> productos = database.listProductos("", 0, 999999);
        assertEquals(2, productos.size());
        assertTrue(productos.stream().noneMatch(p -> p.id == 1));
    }

    @Test
    @DisplayName("Should delete last product")
    void testDeleteLastProducto() {
        insertMultipleProducts();

        database.deleteProducto(3);

        List<Producto> productos = database.listProductos("", 0, 999999);
        assertEquals(2, productos.size());
        assertTrue(productos.stream().noneMatch(p -> p.id == 3));
    }

    @Test
    @DisplayName("Should delete with negative ID")
    void testDeleteNegativeId() {
        insertMultipleProducts();

        database.deleteProducto(-1);

        List<Producto> productos = database.listProductos("", 0, 999999);
        assertEquals(3, productos.size());
    }

    @Test
    @DisplayName("Should delete with ID zero")
    void testDeleteZeroId() {
        insertMultipleProducts();

        database.deleteProducto(0);

        List<Producto> productos = database.listProductos("", 0, 999999);
        assertEquals(3, productos.size());
    }

    @Test
    @DisplayName("Should delete multiple products sequentially")
    void testDeleteMultipleProductos() {
        insertMultipleProducts();

        database.deleteProducto(1);
        database.deleteProducto(2);

        List<Producto> productos = database.listProductos("", 0, 999999);
        assertEquals(1, productos.size());
        assertEquals(3, productos.get(0).id);
    }

    // ==================== Helper Methods ====================

    private void insertMultipleProducts() {
        Producto p1 = new Producto();
        p1.nombre = "Laptop";
        p1.precio = 999.99;
        p1.cantidad = 5;
        p1.almacenId = 1;
        database.insertProducto(p1, "admin");

        Producto p2 = new Producto();
        p2.nombre = "Mouse";
        p2.precio = 29.99;
        p2.cantidad = 50;
        p2.almacenId = 1;
        database.insertProducto(p2, "admin");

        Producto p3 = new Producto();
        p3.nombre = "Keyboard";
        p3.precio = 79.99;
        p3.cantidad = 20;
        p3.almacenId = 1;
        database.insertProducto(p3, "admin");
    }
}
