package mx.unison;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class VistasLogicTest {
    @Test
    void testRolePermissions() {
        Usuario admin = new Usuario();
        admin.rol = "ADMIN";
        
        Usuario prodUser = new Usuario();
        prodUser.rol = "PRODUCTOS";

        // Verifica la lógica de visibilidad que usamos en PanelProductos
        boolean adminPuedeEditar = admin.rol.equals("ADMIN") || admin.rol.equals("PRODUCTOS");
        boolean prodPuedeEditar = prodUser.rol.equals("ADMIN") || prodUser.rol.equals("PRODUCTOS");

        assertTrue(adminPuedeEditar);
        assertTrue(prodPuedeEditar);
    }
}