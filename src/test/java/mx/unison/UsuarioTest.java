package mx.unison;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive tests for Usuario (User) class.
 * Tests user creation and role management.
 */
@DisplayName("Usuario - User Entity Tests")
class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
    }

    @Nested
    @DisplayName("Username Property Tests")
    class UsernameTests {
        @Test
        @DisplayName("Should initialize with null username")
        void testDefaultUsername() {
            assertNull(usuario.username);
        }

        @Test
        @DisplayName("Should allow setting simple username")
        void testSetSimpleUsername() {
            usuario.username = "admin";
            assertEquals("admin", usuario.username);
        }

        @Test
        @DisplayName("Should allow setting username with numbers")
        void testSetUsernameWithNumbers() {
            usuario.username = "user123";
            assertEquals("user123", usuario.username);
        }

        @Test
        @DisplayName("Should allow setting username with underscores")
        void testSetUsernameWithUnderscores() {
            usuario.username = "user_name_123";
            assertEquals("user_name_123", usuario.username);
        }

        @Test
        @DisplayName("Should allow setting username with dots")
        void testSetUsernameWithDots() {
            usuario.username = "user.name.123";
            assertEquals("user.name.123", usuario.username);
        }

        @Test
        @DisplayName("Should allow setting empty username")
        void testSetEmptyUsername() {
            usuario.username = "";
            assertEquals("", usuario.username);
        }

        @Test
        @DisplayName("Should allow setting very long username")
        void testSetVeryLongUsername() {
            String longUsername = "user".repeat(500);
            usuario.username = longUsername;
            assertEquals(longUsername, usuario.username);
        }

        @Test
        @DisplayName("Should allow setting username with special characters")
        void testSetUsernameWithSpecialCharacters() {
            usuario.username = "user@domain.com";
            assertEquals("user@domain.com", usuario.username);
        }

        @Test
        @DisplayName("Should allow setting username with spaces")
        void testSetUsernameWithSpaces() {
            usuario.username = "  user name  ";
            assertEquals("  user name  ", usuario.username);
        }

        @Test
        @DisplayName("Should allow setting username with mixed case")
        void testSetUsernameWithMixedCase() {
            usuario.username = "AdminUser123";
            assertEquals("AdminUser123", usuario.username);
        }

        @Test
        @DisplayName("Should allow setting null username")
        void testSetNullUsername() {
            usuario.username = "initial";
            usuario.username = null;
            assertNull(usuario.username);
        }

        @Test
        @DisplayName("Should allow setting username with Unicode characters")
        void testSetUsernameWithUnicode() {
            usuario.username = "用户名";
            assertEquals("用户名", usuario.username);
        }

        @Test
        @DisplayName("Should allow resetting username")
        void testResetUsername() {
            usuario.username = "first";
            assertEquals("first", usuario.username);
            usuario.username = "second";
            assertEquals("second", usuario.username);
        }
    }

    @Nested
    @DisplayName("Rol (Role) Property Tests")
    class RolTests {
        @Test
        @DisplayName("Should initialize with null role")
        void testDefaultRol() {
            assertNull(usuario.rol);
        }

        @Test
        @DisplayName("Should allow setting ADMIN role")
        void testSetAdminRole() {
            usuario.rol = "ADMIN";
            assertEquals("ADMIN", usuario.rol);
        }

        @Test
        @DisplayName("Should allow setting PRODUCTOS role")
        void testSetProductosRole() {
            usuario.rol = "PRODUCTOS";
            assertEquals("PRODUCTOS", usuario.rol);
        }

        @Test
        @DisplayName("Should allow setting INVITADO role")
        void testSetInvitadoRole() {
            usuario.rol = "INVITADO";
            assertEquals("INVITADO", usuario.rol);
        }

        @Test
        @DisplayName("Should allow setting custom role")
        void testSetCustomRole() {
            usuario.rol = "CUSTOM_ROLE";
            assertEquals("CUSTOM_ROLE", usuario.rol);
        }

        @Test
        @DisplayName("Should allow setting empty role")
        void testSetEmptyRole() {
            usuario.rol = "";
            assertEquals("", usuario.rol);
        }

        @Test
        @DisplayName("Should allow setting lowercase role")
        void testSetLowercaseRole() {
            usuario.rol = "admin";
            assertEquals("admin", usuario.rol);
        }

        @Test
        @DisplayName("Should allow setting mixed case role")
        void testSetMixedCaseRole() {
            usuario.rol = "Admin";
            assertEquals("Admin", usuario.rol);
        }

        @Test
        @DisplayName("Should allow setting very long role")
        void testSetVeryLongRole() {
            String longRole = "ROLE_".repeat(200);
            usuario.rol = longRole;
            assertEquals(longRole, usuario.rol);
        }

        @Test
        @DisplayName("Should allow setting role with numbers")
        void testSetRoleWithNumbers() {
            usuario.rol = "ROLE_123";
            assertEquals("ROLE_123", usuario.rol);
        }

        @Test
        @DisplayName("Should allow setting role with underscores")
        void testSetRoleWithUnderscores() {
            usuario.rol = "ADMIN_SUPER_USER";
            assertEquals("ADMIN_SUPER_USER", usuario.rol);
        }

        @Test
        @DisplayName("Should allow setting role with spaces")
        void testSetRoleWithSpaces() {
            usuario.rol = "  ADMIN ROLE  ";
            assertEquals("  ADMIN ROLE  ", usuario.rol);
        }

        @Test
        @DisplayName("Should allow setting role with special characters")
        void testSetRoleWithSpecialCharacters() {
            usuario.rol = "ROLE-ADMIN@123";
            assertEquals("ROLE-ADMIN@123", usuario.rol);
        }

        @Test
        @DisplayName("Should allow setting null role")
        void testSetNullRole() {
            usuario.rol = "ADMIN";
            usuario.rol = null;
            assertNull(usuario.rol);
        }

        @Test
        @DisplayName("Should allow resetting role")
        void testResetRole() {
            usuario.rol = "ADMIN";
            assertEquals("ADMIN", usuario.rol);
            usuario.rol = "INVITADO";
            assertEquals("INVITADO", usuario.rol);
        }

        @Test
        @DisplayName("Should allow setting Unicode role")
        void testSetUnicodeRole() {
            usuario.rol = "管理员";
            assertEquals("管理员", usuario.rol);
        }
    }

    @Nested
    @DisplayName("Combined Username and Role Tests")
    class CombinedTests {
        @Test
        @DisplayName("Should set both username and role independently")
        void testSetBothUsernameAndRole() {
            usuario.username = "admin";
            usuario.rol = "ADMIN";

            assertEquals("admin", usuario.username);
            assertEquals("ADMIN", usuario.rol);
        }

        @Test
        @DisplayName("Should modify username without affecting role")
        void testModifyUsernameDoesNotAffectRole() {
            usuario.rol = "ADMIN";
            usuario.username = "newuser";

            assertEquals("newuser", usuario.username);
            assertEquals("ADMIN", usuario.rol);
        }

        @Test
        @DisplayName("Should modify role without affecting username")
        void testModifyRoleDoesNotAffectUsername() {
            usuario.username = "admin";
            usuario.rol = "INVITADO";

            assertEquals("admin", usuario.username);
            assertEquals("INVITADO", usuario.rol);
        }

        @Test
        @DisplayName("Should create admin user")
        void testCreateAdminUser() {
            usuario.username = "admin";
            usuario.rol = "ADMIN";

            assertEquals("admin", usuario.username);
            assertEquals("ADMIN", usuario.rol);
        }

        @Test
        @DisplayName("Should create guest user")
        void testCreateGuestUser() {
            usuario.username = "guest";
            usuario.rol = "INVITADO";

            assertEquals("guest", usuario.username);
            assertEquals("INVITADO", usuario.rol);
        }

        @Test
        @DisplayName("Should create product manager user")
        void testCreateProductManagerUser() {
            usuario.username = "product_manager";
            usuario.rol = "PRODUCTOS";

            assertEquals("product_manager", usuario.username);
            assertEquals("PRODUCTOS", usuario.rol);
        }

        @Test
        @DisplayName("Should allow changing user from admin to guest")
        void testChangeFromAdminToGuest() {
            usuario.username = "admin";
            usuario.rol = "ADMIN";

            usuario.rol = "INVITADO";

            assertEquals("admin", usuario.username);
            assertEquals("INVITADO", usuario.rol);
        }

        @Test
        @DisplayName("Should set very long combinations")
        void testVeryLongCombinations() {
            usuario.username = "user".repeat(500);
            usuario.rol = "ROLE_".repeat(200);

            assertEquals("user".repeat(500), usuario.username);
            assertEquals("ROLE_".repeat(200), usuario.rol);
        }

        @Test
        @DisplayName("Should handle null values for both properties")
        void testNullBothProperties() {
            usuario.username = null;
            usuario.rol = null;

            assertNull(usuario.username);
            assertNull(usuario.rol);
        }

        @Test
        @DisplayName("Should handle empty strings for both properties")
        void testEmptyStringsBothProperties() {
            usuario.username = "";
            usuario.rol = "";

            assertEquals("", usuario.username);
            assertEquals("", usuario.rol);
        }
    }

    @Nested
    @DisplayName("Multiple Usuario Instances Tests")
    class MultipleInstancesTests {
        @Test
        @DisplayName("Should create multiple independent usuarios")
        void testMultipleIndependentUsuarios() {
            Usuario usuario1 = new Usuario();
            Usuario usuario2 = new Usuario();

            usuario1.username = "user1";
            usuario1.rol = "ADMIN";

            usuario2.username = "user2";
            usuario2.rol = "INVITADO";

            assertEquals("user1", usuario1.username);
            assertEquals("ADMIN", usuario1.rol);
            assertEquals("user2", usuario2.username);
            assertEquals("INVITADO", usuario2.rol);
        }

        @Test
        @DisplayName("Should maintain independence between instances")
        void testInstanceIndependence() {
            Usuario usuario1 = new Usuario();
            Usuario usuario2 = new Usuario();
            Usuario usuario3 = new Usuario();

            usuario1.username = "admin";
            usuario2.username = "user";
            usuario3.username = "guest";

            assertEquals("admin", usuario1.username);
            assertEquals("user", usuario2.username);
            assertEquals("guest", usuario3.username);
        }

        @Test
        @DisplayName("Should create many usuarios without errors")
        void testCreateManyUsuarios() {
            for (int i = 0; i < 1000; i++) {
                Usuario u = new Usuario();
                u.username = "user" + i;
                u.rol = "ROLE_" + i;
                assertNotNull(u);
            }
        }
    }

    @Test
    @DisplayName("Usuario should not override equals by default")
    void testDefaultEquality() {
        Usuario usuario1 = new Usuario();
        Usuario usuario2 = new Usuario();

        usuario1.username = "same";
        usuario2.username = "same";
        usuario1.rol = "ADMIN";
        usuario2.rol = "ADMIN";

        // Since Usuario doesn't override equals, different instances are not equal
        assertNotEquals(usuario1, usuario2);
    }

    @Test
    @DisplayName("Usuario instances should be objects")
    void testUsuarioIsObject() {
        assertNotNull(usuario);
        assertTrue(usuario instanceof Object);
    }

    @Test
    @DisplayName("Should allow creating usuario with no-arg constructor")
    void testNoArgConstructor() {
        Usuario u = new Usuario();
        assertNotNull(u);
    }

    @Test
    @DisplayName("Should have public accessible properties")
    void testPublicProperties() {
        usuario.username = "test";
        usuario.rol = "TEST";

        assertDoesNotThrow(() -> {
            String username = usuario.username;
            String rol = usuario.rol;
            assertEquals("test", username);
            assertEquals("TEST", rol);
        });
    }
}
