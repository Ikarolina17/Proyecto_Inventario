package mx.unison;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive tests for Producto (Product) class.
 * Tests product creation and data integrity.
 */
@DisplayName("Producto - Product Entity Tests")
class ProductoTest {

    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = new Producto();
    }

    @Nested
    @DisplayName("ID Property Tests")
    class IdTests {
        @Test
        @DisplayName("Should initialize with default ID")
        void testDefaultId() {
            assertEquals(0, producto.id, "Default ID should be 0");
        }

        @Test
        @DisplayName("Should allow setting positive ID")
        void testSetPositiveId() {
            producto.id = 123;
            assertEquals(123, producto.id);
        }

        @Test
        @DisplayName("Should allow setting negative ID")
        void testSetNegativeId() {
            producto.id = -1;
            assertEquals(-1, producto.id);
        }

        @Test
        @DisplayName("Should allow setting zero ID")
        void testSetZeroId() {
            producto.id = 0;
            assertEquals(0, producto.id);
        }

        @Test
        @DisplayName("Should allow setting large ID")
        void testSetLargeId() {
            producto.id = Integer.MAX_VALUE;
            assertEquals(Integer.MAX_VALUE, producto.id);
        }
    }

    @Nested
    @DisplayName("Nombre (Name) Property Tests")
    class NombreTests {
        @Test
        @DisplayName("Should initialize with null nombre")
        void testDefaultNombre() {
            assertNull(producto.nombre);
        }

        @Test
        @DisplayName("Should allow setting simple name")
        void testSetSimpleName() {
            producto.nombre = "Laptop";
            assertEquals("Laptop", producto.nombre);
        }

        @Test
        @DisplayName("Should allow setting empty string as name")
        void testSetEmptyName() {
            producto.nombre = "";
            assertEquals("", producto.nombre);
        }

        @Test
        @DisplayName("Should allow setting name with special characters")
        void testSetSpecialCharacterName() {
            producto.nombre = "Product @#$% & Co.!";
            assertEquals("Product @#$% & Co.!", producto.nombre);
        }

        @Test
        @DisplayName("Should allow setting name with spaces")
        void testSetNameWithSpaces() {
            producto.nombre = "  Product With Spaces  ";
            assertEquals("  Product With Spaces  ", producto.nombre);
        }

        @Test
        @DisplayName("Should allow setting very long name")
        void testSetVeryLongName() {
            String longName = "A".repeat(10000);
            producto.nombre = longName;
            assertEquals(longName, producto.nombre);
        }

        @Test
        @DisplayName("Should allow setting null name")
        void testSetNullName() {
            producto.nombre = "Initial";
            producto.nombre = null;
            assertNull(producto.nombre);
        }

        @Test
        @DisplayName("Should allow setting name with numbers")
        void testSetNameWithNumbers() {
            producto.nombre = "Product123";
            assertEquals("Product123", producto.nombre);
        }

        @Test
        @DisplayName("Should allow setting name with Unicode characters")
        void testSetNameWithUnicode() {
            producto.nombre = "Café ☕ 中文";
            assertEquals("Café ☕ 中文", producto.nombre);
        }
    }

    @Nested
    @DisplayName("Precio (Price) Property Tests")
    class PrecioTests {
        @Test
        @DisplayName("Should initialize with default price 0.0")
        void testDefaultPrecio() {
            assertEquals(0.0, producto.precio);
        }

        @Test
        @DisplayName("Should allow setting positive price")
        void testSetPositivePrice() {
            producto.precio = 99.99;
            assertEquals(99.99, producto.precio);
        }

        @Test
        @DisplayName("Should allow setting negative price")
        void testSetNegativePrice() {
            producto.precio = -10.50;
            assertEquals(-10.50, producto.precio);
        }

        @Test
        @DisplayName("Should allow setting zero price")
        void testSetZeroPrice() {
            producto.precio = 0.0;
            assertEquals(0.0, producto.precio);
        }

        @Test
        @DisplayName("Should allow setting very large price")
        void testSetLargePrice() {
            producto.precio = Double.MAX_VALUE;
            assertEquals(Double.MAX_VALUE, producto.precio);
        }

        @Test
        @DisplayName("Should allow setting very small price")
        void testSetSmallPrice() {
            producto.precio = 0.01;
            assertEquals(0.01, producto.precio);
        }

        @Test
        @DisplayName("Should handle precision with multiple decimal places")
        void testPricePrecision() {
            producto.precio = 123.456789;
            assertEquals(123.456789, producto.precio);
        }

        @Test
        @DisplayName("Should allow setting price to Double.NaN")
        void testSetNaNPrice() {
            producto.precio = Double.NaN;
            assertTrue(Double.isNaN(producto.precio));
        }

        @Test
        @DisplayName("Should allow setting price to positive infinity")
        void testSetPositiveInfinityPrice() {
            producto.precio = Double.POSITIVE_INFINITY;
            assertEquals(Double.POSITIVE_INFINITY, producto.precio);
        }
    }

    @Nested
    @DisplayName("Cantidad (Quantity) Property Tests")
    class CantidadTests {
        @Test
        @DisplayName("Should initialize with default quantity 0")
        void testDefaultCantidad() {
            assertEquals(0, producto.cantidad);
        }

        @Test
        @DisplayName("Should allow setting positive quantity")
        void testSetPositiveQuantity() {
            producto.cantidad = 100;
            assertEquals(100, producto.cantidad);
        }

        @Test
        @DisplayName("Should allow setting negative quantity")
        void testSetNegativeQuantity() {
            producto.cantidad = -50;
            assertEquals(-50, producto.cantidad);
        }

        @Test
        @DisplayName("Should allow setting zero quantity")
        void testSetZeroQuantity() {
            producto.cantidad = 0;
            assertEquals(0, producto.cantidad);
        }

        @Test
        @DisplayName("Should allow setting large quantity")
        void testSetLargeQuantity() {
            producto.cantidad = Integer.MAX_VALUE;
            assertEquals(Integer.MAX_VALUE, producto.cantidad);
        }

        @Test
        @DisplayName("Should allow setting quantity to one")
        void testSetQuantityOne() {
            producto.cantidad = 1;
            assertEquals(1, producto.cantidad);
        }
    }

    @Nested
    @DisplayName("AlmacenId (Warehouse ID) Property Tests")
    class AlmacenIdTests {
        @Test
        @DisplayName("Should initialize with default almacenId 0")
        void testDefaultAlmacenId() {
            assertEquals(0, producto.almacenId);
        }

        @Test
        @DisplayName("Should allow setting positive warehouse ID")
        void testSetPositiveWarehouseId() {
            producto.almacenId = 5;
            assertEquals(5, producto.almacenId);
        }

        @Test
        @DisplayName("Should allow setting negative warehouse ID")
        void testSetNegativeWarehouseId() {
            producto.almacenId = -1;
            assertEquals(-1, producto.almacenId);
        }

        @Test
        @DisplayName("Should allow setting large warehouse ID")
        void testSetLargeWarehouseId() {
            producto.almacenId = Integer.MAX_VALUE;
            assertEquals(Integer.MAX_VALUE, producto.almacenId);
        }
    }

    @Nested
    @DisplayName("Descripcion (Description) Property Tests")
    class DescripcionTests {
        @Test
        @DisplayName("Should initialize with null description")
        void testDefaultDescripcion() {
            assertNull(producto.descripcion);
        }

        @Test
        @DisplayName("Should allow setting simple description")
        void testSetSimpleDescription() {
            producto.descripcion = "A high-quality laptop";
            assertEquals("A high-quality laptop", producto.descripcion);
        }

        @Test
        @DisplayName("Should allow setting empty description")
        void testSetEmptyDescription() {
            producto.descripcion = "";
            assertEquals("", producto.descripcion);
        }

        @Test
        @DisplayName("Should allow setting very long description")
        void testSetVeryLongDescription() {
            String longDesc = "Long description ".repeat(1000);
            producto.descripcion = longDesc;
            assertEquals(longDesc, producto.descripcion);
        }

        @Test
        @DisplayName("Should allow setting description with special characters")
        void testSetDescriptionWithSpecialChars() {
            producto.descripcion = "Description with @#$%^&*() & <>!?";
            assertEquals("Description with @#$%^&*() & <>!?", producto.descripcion);
        }

        @Test
        @DisplayName("Should allow setting description with newlines")
        void testSetDescriptionWithNewlines() {
            producto.descripcion = "Line 1\nLine 2\nLine 3";
            assertEquals("Line 1\nLine 2\nLine 3", producto.descripcion);
        }

        @Test
        @DisplayName("Should allow setting null description")
        void testSetNullDescription() {
            producto.descripcion = "Initial";
            producto.descripcion = null;
            assertNull(producto.descripcion);
        }
    }

    @Nested
    @DisplayName("Complete Product Object Tests")
    class CompleteProductTests {
        @Test
        @DisplayName("Should create complete product with all properties set")
        void testCreateCompleteProduct() {
            producto.id = 1;
            producto.nombre = "Gaming Laptop";
            producto.precio = 1299.99;
            producto.cantidad = 3;
            producto.almacenId = 2;
            producto.descripcion = "High-performance gaming laptop";

            assertEquals(1, producto.id);
            assertEquals("Gaming Laptop", producto.nombre);
            assertEquals(1299.99, producto.precio);
            assertEquals(3, producto.cantidad);
            assertEquals(2, producto.almacenId);
            assertEquals("High-performance gaming laptop", producto.descripcion);
        }

        @Test
        @DisplayName("Should allow modifying all properties independently")
        void testModifyPropertiesIndependently() {
            producto.id = 1;
            assertEquals(1, producto.id);
            producto.nombre = "Item";
            assertEquals("Item", producto.nombre);
            producto.precio = 50.0;
            assertEquals(50.0, producto.precio);
            producto.cantidad = 10;
            assertEquals(10, producto.cantidad);
            producto.almacenId = 3;
            assertEquals(3, producto.almacenId);
            producto.descripcion = "Desc";
            assertEquals("Desc", producto.descripcion);
        }

        @Test
        @DisplayName("Should allow creating multiple instances with different properties")
        void testMultipleInstances() {
            Producto p1 = new Producto();
            Producto p2 = new Producto();
            
            p1.nombre = "Product 1";
            p2.nombre = "Product 2";
            
            assertEquals("Product 1", p1.nombre);
            assertEquals("Product 2", p2.nombre);
            assertNotEquals(p1.nombre, p2.nombre);
        }

        @Test
        @DisplayName("Should maintain data independence between instances")
        void testDataIndependenceBetweenInstances() {
            Producto p1 = new Producto();
            Producto p2 = new Producto();
            
            p1.id = 1;
            p1.precio = 100.0;
            
            p2.id = 2;
            p2.precio = 200.0;
            
            assertEquals(1, p1.id);
            assertEquals(2, p2.id);
            assertEquals(100.0, p1.precio);
            assertEquals(200.0, p2.precio);
        }
    }

    @Test
    @DisplayName("Should allow creating new instances without errors")
    void testMultipleProductCreation() {
        for (int i = 0; i < 100; i++) {
            Producto p = new Producto();
            assertNotNull(p);
        }
    }

    @Test
    @DisplayName("Producto should not override equals by default")
    void testDefaultEquality() {
        Producto p1 = new Producto();
        Producto p2 = new Producto();
        
        p1.id = 1;
        p2.id = 1;
        p1.nombre = "Same";
        p2.nombre = "Same";
        
        // Since Producto doesn't override equals, different instances are not equal
        assertNotEquals(p1, p2);
    }

    @Test
    @DisplayName("Producto instances should be objects")
    void testProductoIsObject() {
        assertNotNull(producto);
        assertTrue(producto instanceof Object);
    }
}
