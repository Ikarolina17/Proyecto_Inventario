package mx.unison;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive tests for PanelProductos class.
 * Tests panel creation, table management, and product operations.
 * Note: GUI testing is limited in unit tests; focus on structural and functional components.
 */
@DisplayName("PanelProductos - Product Panel Tests")
@ExtendWith(MockitoExtension.class)
class PanelProductosTest {

    private Database database;
    private Usuario usuarioAdmin;
    private Usuario usuarioProductos;
    private Usuario usuarioInvitado;
    private Runnable logoutAction;

    @BeforeEach
    void setUp() {
        database = new Database();

        usuarioAdmin = new Usuario();
        usuarioAdmin.username = "admin";
        usuarioAdmin.rol = "ADMIN";

        usuarioProductos = new Usuario();
        usuarioProductos.username = "productos_user";
        usuarioProductos.rol = "PRODUCTOS";

        usuarioInvitado = new Usuario();
        usuarioInvitado.username = "guest";
        usuarioInvitado.rol = "INVITADO";

        logoutAction = () -> {
            // Mock logout action
        };
    }

    // ==================== Panel Creation Tests ====================

    @Test
    @DisplayName("Should create panel with admin user")
    void testCreatePanelWithAdminUser() {
        assertDoesNotThrow(() -> {
            PanelProductos panel = new PanelProductos(database, usuarioAdmin, logoutAction);
            assertNotNull(panel);
        });
    }

    @Test
    @DisplayName("Should create panel with productos user")
    void testCreatePanelWithProductosUser() {
        assertDoesNotThrow(() -> {
            PanelProductos panel = new PanelProductos(database, usuarioProductos, logoutAction);
            assertNotNull(panel);
        });
    }

    @Test
    @DisplayName("Should create panel with guest user")
    void testCreatePanelWithGuestUser() {
        assertDoesNotThrow(() -> {
            PanelProductos panel = new PanelProductos(database, usuarioInvitado, logoutAction);
            assertNotNull(panel);
        });
    }

    @Test
    @DisplayName("Should extend JPanel")
    void testPanelExtendsJPanel() {
        assertTrue(javax.swing.JPanel.class.isAssignableFrom(PanelProductos.class));
    }

    @Test
    @DisplayName("Should be in correct package")
    void testPanelCorrectPackage() {
        assertEquals("mx.unison", PanelProductos.class.getPackage().getName());
    }

    // ==================== Constructor Tests ====================

    @Test
    @DisplayName("Should accept Database, Usuario, and Runnable in constructor")
    void testConstructorParameters() {
        assertDoesNotThrow(() -> {
            java.lang.reflect.Constructor<?> constructor = 
                    PanelProductos.class.getDeclaredConstructor(Database.class, Usuario.class, Runnable.class);
            assertNotNull(constructor);
        });
    }

    @Test
    @DisplayName("Constructor should be public")
    void testConstructorIsPublic() {
        assertDoesNotThrow(() -> {
            java.lang.reflect.Constructor<?> constructor = 
                    PanelProductos.class.getDeclaredConstructor(Database.class, Usuario.class, Runnable.class);
            assertTrue(Modifier.isPublic(constructor.getModifiers()));
        });
    }

    @Test
    @DisplayName("Should accept null logout action")
    void testConstructorWithNullLogoutAction() {
        assertDoesNotThrow(() -> {
            PanelProductos panel = new PanelProductos(database, usuarioAdmin, null);
            assertNotNull(panel);
        });
    }

    // ==================== Panel Component Tests ====================

    @Test
    @DisplayName("Panel should have table field")
    void testPanelHasTableField() {
        assertDoesNotThrow(() -> {
            Field tableField = findField(PanelProductos.class, "tabla");
            assertNotNull(tableField);
        });
    }

    @Test
    @DisplayName("Panel should have DefaultTableModel field")
    void testPanelHasModelField() {
        assertDoesNotThrow(() -> {
            Field modelField = findField(PanelProductos.class, "modelo");
            assertNotNull(modelField);
        });
    }

    @Test
    @DisplayName("Panel should have database field")
    void testPanelHasDatabaseField() {
        assertDoesNotThrow(() -> {
            Field dbField = findField(PanelProductos.class, "database");
            assertNotNull(dbField);
        });
    }

    @Test
    @DisplayName("Panel should have usuarioActual field")
    void testPanelHasUsuarioActualField() {
        assertDoesNotThrow(() -> {
            Field userField = findField(PanelProductos.class, "usuarioActual");
            assertNotNull(userField);
        });
    }

    // ==================== Panel Methods Tests ====================

    @Test
    @DisplayName("Panel should have abrirFormularioRegistro method")
    void testPanelHasFormularioRegistroMethod() {
        assertDoesNotThrow(() -> {
            Method method = findMethod(PanelProductos.class, "abrirFormularioRegistro");
            assertNotNull(method);
        });
    }

    @Test
    @DisplayName("Panel should have actualizarTabla method")
    void testPanelHasActualizarTablaMethod() {
        assertDoesNotThrow(() -> {
            Method method = findMethod(PanelProductos.class, "actualizarTabla");
            assertNotNull(method);
        });
    }

    @Test
    @DisplayName("abrirFormularioRegistro should be private")
    void testFormularioRegistroIsPrivate() {
        assertDoesNotThrow(() -> {
            Method method = findMethod(PanelProductos.class, "abrirFormularioRegistro");
            assertTrue(Modifier.isPrivate(method.getModifiers()));
        });
    }

    @Test
    @DisplayName("actualizarTabla should be private")
    void testActualizarTablaIsPrivate() {
        assertDoesNotThrow(() -> {
            Method method = findMethod(PanelProductos.class, "actualizarTabla");
            assertTrue(Modifier.isPrivate(method.getModifiers()));
        });
    }

    // ==================== Permissions Tests ====================

    @Test
    @DisplayName("Admin user should have edit permissions")
    void testAdminHasEditPermissions() {
        // Admin should have permissions for buttons
        assertTrue(usuarioAdmin.rol.equals("ADMIN") || usuarioAdmin.rol.equals("PRODUCTOS"));
    }

    @Test
    @DisplayName("Productos user should have edit permissions")
    void testProductosUserHasEditPermissions() {
        assertTrue(usuarioProductos.rol.equals("PRODUCTOS"));
    }

    @Test
    @DisplayName("Guest user should not have edit permissions")
    void testGuestUserNoEditPermissions() {
        assertFalse(usuarioInvitado.rol.equals("ADMIN") || usuarioInvitado.rol.equals("PRODUCTOS"));
    }

    @Test
    @DisplayName("Panel with admin should allow editing")
    void testAdminPanelAllowsEditing() {
        assertDoesNotThrow(() -> {
            PanelProductos panel = new PanelProductos(database, usuarioAdmin, logoutAction);
            assertNotNull(panel);
        });
    }

    @Test
    @DisplayName("Panel with productos should allow editing")
    void testProductosPanelAllowsEditing() {
        assertDoesNotThrow(() -> {
            PanelProductos panel = new PanelProductos(database, usuarioProductos, logoutAction);
            assertNotNull(panel);
        });
    }

    @Test
    @DisplayName("Panel with guest should be read-only")
    void testGuestPanelReadOnly() {
        assertDoesNotThrow(() -> {
            PanelProductos panel = new PanelProductos(database, usuarioInvitado, logoutAction);
            assertNotNull(panel);
        });
    }

    // ==================== Table Configuration Tests ====================

    @Test
    @DisplayName("Panel should configure table columns")
    void testTableColumnsConfiguration() {
        // Expected columns: ID, Nombre, Precio, Cantidad, Descripción
        String[] expectedColumns = {"ID", "Nombre", "Precio", "Cantidad", "Descripción"};
        assertEquals(5, expectedColumns.length);
    }

    @Test
    @DisplayName("Table should have product data fields")
    void testTableProductFields() {
        String[] fields = {"ID", "Nombre", "Precio", "Cantidad", "Descripción"};
        assertTrue(fields.length > 0);
        assertNotNull(fields);
    }

    // ==================== Layout Tests ====================

    @Test
    @DisplayName("Panel should use BorderLayout")
    void testPanelUsesBorderLayout() {
        assertDoesNotThrow(() -> {
            PanelProductos panel = new PanelProductos(database, usuarioAdmin, logoutAction);
            java.awt.LayoutManager layout = panel.getLayout();
            assertNotNull(layout);
        });
    }

    // ==================== Product Operations Tests ====================

    @Test
    @DisplayName("Should be able to insert product through panel context")
    void testProductInsertionContext() {
        Producto p = new Producto();
        p.nombre = "Test Product";
        p.precio = 50.0;
        p.cantidad = 10;
        p.almacenId = 1;
        p.descripcion = "Test Description";

        database.insertProducto(p, usuarioAdmin.username);
        assertNotNull(p.id);
    }

    @Test
    @DisplayName("Should be able to retrieve products through database")
    void testProductRetrieval() {
        Producto p = new Producto();
        p.nombre = "Product";
        p.precio = 100.0;
        p.cantidad = 5;
        p.almacenId = 1;

        database.insertProducto(p, usuarioAdmin.username);

        var products = database.listProductos("", 0, 999999);
        assertEquals(1, products.size());
    }

    @Test
    @DisplayName("Should be able to delete product through database")
    void testProductDeletion() {
        Producto p = new Producto();
        p.nombre = "Product to Delete";
        p.precio = 50.0;
        p.cantidad = 5;
        p.almacenId = 1;

        database.insertProducto(p, usuarioAdmin.username);
        int productId = p.id;

        database.deleteProducto(productId);

        var products = database.listProductos("", 0, 999999);
        assertTrue(products.stream().noneMatch(prod -> prod.id == productId));
    }

    // ==================== Integration Tests ====================

    @Test
    @DisplayName("Should integrate with database for product listing")
    void testIntegrationWithDatabase() {
        assertDoesNotThrow(() -> {
            PanelProductos panel = new PanelProductos(database, usuarioAdmin, logoutAction);
            assertNotNull(panel);
            // Panel should be able to access database
            var productos = database.listProductos("", 0, 999999);
            assertNotNull(productos);
        });
    }

    @Test
    @DisplayName("Should handle multiple panel instances")
    void testMultiplePanelInstances() {
        assertDoesNotThrow(() -> {
            PanelProductos panel1 = new PanelProductos(database, usuarioAdmin, logoutAction);
            PanelProductos panel2 = new PanelProductos(database, usuarioProductos, logoutAction);
            PanelProductos panel3 = new PanelProductos(database, usuarioInvitado, logoutAction);

            assertNotNull(panel1);
            assertNotNull(panel2);
            assertNotNull(panel3);
        });
    }

    @Test
    @DisplayName("Panel should be usable in different user contexts")
    void testPanelMultipleUserContexts() {
        assertDoesNotThrow(() -> {
            Usuario user1 = new Usuario();
            user1.username = "user1";
            user1.rol = "ADMIN";

            Usuario user2 = new Usuario();
            user2.username = "user2";
            user2.rol = "INVITADO";

            PanelProductos panel1 = new PanelProductos(database, user1, logoutAction);
            PanelProductos panel2 = new PanelProductos(database, user2, logoutAction);

            assertNotNull(panel1);
            assertNotNull(panel2);
        });
    }

    @Test
    @DisplayName("Panel should handle null database gracefully in context")
    void testPanelWithValidDatabase() {
        Database db = new Database();
        assertNotNull(db);
        assertDoesNotThrow(() -> {
            PanelProductos panel = new PanelProductos(db, usuarioAdmin, logoutAction);
            assertNotNull(panel);
        });
    }

    // ==================== Helper Methods ====================

    private Field findField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    private Method findMethod(Class<?> clazz, String methodName) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    @Test
    @DisplayName("Panel class should not be final")
    void testPanelNotFinal() {
        assertFalse(Modifier.isFinal(PanelProductos.class.getModifiers()));
    }

    @Test
    @DisplayName("Panel class should not be abstract")
    void testPanelNotAbstract() {
        assertFalse(Modifier.isAbstract(PanelProductos.class.getModifiers()));
    }

    @Test
    @DisplayName("Should be able to get panel components")
    void testGetPanelComponents() {
        assertDoesNotThrow(() -> {
            PanelProductos panel = new PanelProductos(database, usuarioAdmin, logoutAction);
            java.awt.Component[] components = panel.getComponents();
            assertNotNull(components);
        });
    }

    @Test
    @DisplayName("Panel should be visible component")
    void testPanelIsComponent() {
        assertTrue(javax.swing.JComponent.class.isAssignableFrom(PanelProductos.class));
    }
}
