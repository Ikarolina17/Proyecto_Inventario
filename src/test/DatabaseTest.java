package mx.unison;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class DatabaseTest {
    private Database db;

    @BeforeEach
    void setUp() {
        db = new Database(); // Inicializa la base de datos antes de cada prueba
    }

    @Test
    void testAuthenticateSuccess() {
        // Prueba el inicio de sesión con el usuario administrador por defecto
        Usuario user = db.authenticate("ADMIN", "admin23");
        assertNotNull(user);
        assertEquals("ADMIN", user.rol);
    }

    @Test
    void testAuthenticateFailure() {
        // Prueba que un usuario inexistente o clave errónea devuelva null
        Usuario user = db.authenticate("NO_EXISTE", "1234");
        assertNull(user);
    }

    @Test
    void testInsertAndDeleteProducto() {
        Producto p = new Producto();
        p.nombre = "Producto Prueba";
        p.precio = 50.0;
        p.cantidad = 10;
        p.almacenId = 1;
        p.descripcion = "Test JUnit";

        // Inserta y verifica que aparezca en la lista con los filtros
        db.insertProducto(p, "TEST_USER");
        List<Producto> lista = db.listProductos("Producto Prueba", 0, 100);
        
        assertFalse(lista.isEmpty());
        int idGenerado = lista.get(0).id;

        // Borra y verifica que la lista esté vacía de nuevo
        db.deleteProducto(idGenerado);
        lista = db.listProductos("Producto Prueba", 0, 100);
        assertTrue(lista.isEmpty());
    }
}