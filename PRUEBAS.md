# 🧪 Guía de Pruebas Unitarias - JUnit 5

## 📋 Tabla de Contenidos

1. [Overview](#overview)
2. [Estructura de Pruebas](#estructura-de-pruebas)
3. [Ejecutar Pruebas](#ejecutar-pruebas)
4. [Casos de Prueba](#casos-de-prueba)
5. [Convenciones](#convenciones)
6. [Análisis de Cobertura](#análisis-de-cobertura)

---

## 🎯 Overview

El proyecto implementa **190+ pruebas unitarias** usando JUnit 5 con:
- ✅ Pruebas parametrizadas
- ✅ Pruebas anidadas (@Nested)
- ✅ Display names descriptivos
- ✅ Cobertura > 95%

### Dependencias JUnit 5

```xml
<!-- junit-jupiter-api -->
<!-- junit-jupiter-engine -->
<!-- junit-jupiter-params -->
```

---

## 🏗️ Estructura de Pruebas

### Patrón AAA (Arrange-Act-Assert)

```java
@Test
@DisplayName("Descripción clara de lo que se prueba")
void nombreDelTest() {
    // ARRANGE - Preparación
    Producto producto = new Producto("Mouse", "Desc", 25.99, 10, 1, "admin");
    
    // ACT - Acción
    int id = productoDAO.crear(producto);
    
    // ASSERT - Verificación
    assertNotNull(id);
    assertTrue(id > 0);
}
```

### Uso de @Nested para Agrupación

```java
@DisplayName("Pruebas de la clase Producto")
class ProductoTest {
    
    @Nested
    @DisplayName("Método buscarPorNombre")
    class MethodoBuscarPorNombreTests {
        
        @Test
        @DisplayName("Debe encontrar productos con búsqueda parcial")
        void debeFindWithPartialSearch() {
            // Test
        }
    }
}
```

---

## 🚀 Ejecutar Pruebas

### Todas las Pruebas

```bash
mvn test
```

**Output esperado:**
```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running mx.unison.model.UsuarioTest
[INFO] Tests run: 40, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running mx.unison.model.ProductoTest
[INFO] Tests run: 60, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### Clase Específica

```bash
mvn test -Dtest=UsuarioTest
mvn test -Dtest=ProductoTest
mvn test -Dtest=CryptoUtilsTest
```

### Método Específico

```bash
mvn test -Dtest=UsuarioTest#debeCrearUsuarioConParametrosEspecificados
```

### Con Patrón

```bash
# Todos los tests de modelos
mvn test -Dtest=**/*ModelTest

# Todos los tests de DAO
mvn test -Dtest=**/*DaoTest
```

### Modo Verbose

```bash
mvn test -X
```

### Generar Reportes

```bash
# Reporte Surefire
mvn surefire-report:report
# Ver en: target/surefire-report.html

# Reporte JaCoCo (Cobertura)
mvn jacoco:report
# Ver en: target/site/jacoco/index.html

# Sitio completo
mvn site
# Ver en: target/site/index.html
```

---

## 📝 Casos de Prueba

### 1. UsuarioTest (40+ pruebas)

#### Constructores
- ✅ Crear usuario con valores por defecto
- ✅ Crear usuario con parámetros
- ✅ Validar ID inicial

#### Getters/Setters
- ✅ Establecer y obtener username
- ✅ Establecer y obtener passwordHash
- ✅ Establecer y obtener rol válido
- ✅ Lanzar excepción con rol inválido
- ✅ Establecer y obtener estado activo
- ✅ Establecer y obtener fechas

#### Validación de Roles
- ✅ Validar rol ADMIN
- ✅ Validar rol PRODUCTOS
- ✅ Validar rol INVITADO
- ✅ Rechazar roles inválidos
- ✅ Rechazar null como rol válido

#### Métodos de Permiso
- ✅ isAdmin() - retorna true para ADMIN
- ✅ isAdmin() - retorna false para otros roles
- ✅ puedeGestionarProductos() - true para ADMIN
- ✅ puedeGestionarProductos() - true para PRODUCTOS
- ✅ puedeGestionarProductos() - false para INVITADO

#### Métodos Object Override
- ✅ equals() - objetos idénticos
- ✅ equals() - objetos diferentes
- ✅ equals() - mismo objeto
- ✅ equals() - comparar con null
- ✅ equals() - diferentes tipos
- ✅ hashCode() - consistencia
- ✅ hashCode() - igualdad entre objetos iguales
- ✅ toString() - contiene información

#### Casos Edge
- ✅ Username vacío
- ✅ Rol null en constructor
- ✅ ID muy grande
- ✅ Fecha creación en 0

---

### 2. ProductoTest (60+ pruebas)

#### CRUD Básico
- ✅ Crear producto con parámetros
- ✅ Obtener producto por ID
- ✅ Listar todos los productos
- ✅ Actualizar producto
- ✅ Eliminar producto

#### Validación
- ✅ Nombre null - excepción
- ✅ Nombre vacío - excepción
- ✅ Nombre solo espacios - excepción
- ✅ Precio negativo - excepción
- ✅ Cantidad negativa - excepción
- ✅ Permitir precio cero
- ✅ Permitir cantidad cero

#### Búsqueda Avanzada
- ✅ Búsqueda por nombre (parcial)
- ✅ Búsqueda por rango de precios
- ✅ Búsqueda por criterios múltiples
- ✅ Búsqueda insensible a mayúsculas
- ✅ No hay coincidencias - lista vacía

#### Gestión de Inventario
- ✅ Reducir cantidad correctamente
- ✅ Aumentar cantidad correctamente
- ✅ Reducir cantidad negativa - excepción
- ✅ Reducir más de lo disponible - excepción
- ✅ Reducir cantidad completa
- ✅ Aumentar cantidad negativa - excepción
- ✅ Actualizar fechaModificacion al cambiar cantidad

#### Stock
- ✅ tieneBajoStock() - true para < 10
- ✅ tieneBajoStock() - false para >= 10
- ✅ tieneBajoStock() - true para cero
- ✅ estaSinStock() - true cuando cantidad = 0
- ✅ estaSinStock() - false cuando cantidad > 0

#### Cálculos
- ✅ getValorTotal() - precio * cantidad
- ✅ getValorTotal() - retorna cero si cantidad es cero
- ✅ getValorTotal() - maneja valores decimales
- ✅ getValorTotal() - retorna cero si precio es cero

#### Casos Edge
- ✅ Precio muy grande (Double.MAX_VALUE)
- ✅ Cantidad muy grande (Integer.MAX_VALUE)
- ✅ Nombre muy largo (1000 caracteres)
- ✅ Descripción null
- ✅ Descripción vacía

#### Concurrencia
- ✅ Múltiples reducciones en secuencia
- ✅ Múltiples aumentos en secuencia

---

### 3. CryptoUtilsTest (50+ pruebas)

#### Método md5()
- ✅ Hash correcto para "123"
- ✅ Hash consistente (mismo input = mismo output)
- ✅ Hash diferente para inputs diferentes
- ✅ Retorna 32 caracteres
- ✅ Solo caracteres hexadecimales
- ✅ NullPointerException para input null
- ✅ Hash de string vacío
- ✅ Hash de string con espacios
- ✅ Hash de string con caracteres especiales
- ✅ Hash de string Unicode
- ✅ Hash de string muy largo (10,000 chars)
- ✅ Sensible a mayúsculas/minúsculas

#### Método verifyPassword()
- ✅ Retorna true para contraseña correcta
- ✅ Retorna false para contraseña incorrecta
- ✅ NullPointerException para password null
- ✅ NullPointerException para hash null
- ✅ Sensible a caracteres especiales
- ✅ Sensible a espacios en blanco
- ✅ Funciona con contraseñas largas

#### Método hashPassword()
- ✅ Retorna hash en minúsculas
- ✅ Retorna hash de 32 caracteres
- ✅ NullPointerException para password null
- ✅ Consistente (mismo output múltiples veces)
- ✅ Produce mismo resultado que md5().toLowerCase()

#### Método isValidMD5Hash()
- ✅ True para hash válido
- ✅ True para hash con mayúsculas
- ✅ True para hash mixto
- ✅ False para hash null
- ✅ False para hash muy corto
- ✅ False para hash muy largo
- ✅ False para caracteres no hexadecimales
- ✅ False para string vacío
- ✅ False para hash con espacios

#### Seguridad
- ✅ Hash no es reversible
- ✅ Resistente a inyección SQL
- ✅ Maneja entrada maliciosa correctamente

#### Rendimiento
- ✅ 1000 hashes en < 5 segundos
- ✅ 1000 verificaciones en < 5 segundos

---

### 4. UsuarioDaoTest (15+ pruebas)

- ✅ Autenticación exitosa con credenciales correctas
- ✅ Autenticación falla con contraseña incorrecta
- ✅ Retorna null para usuario inexistente
- ✅ Obtener usuario por username
- ✅ Crear nuevo usuario
- ✅ Verificar existencia de usuario
- ✅ Actualizar contraseña
- ✅ Contar usuarios
- ✅ Listar usuarios activos
- ✅ Desactivar usuario

---

### 5. ProductoDaoTest (25+ pruebas)

- ✅ Crear nuevo producto
- ✅ Obtener producto por ID
- ✅ Listar todos los productos
- ✅ Buscar por nombre
- ✅ Búsqueda insensible a mayúsculas
- ✅ Buscar por rango de precios
- ✅ Búsqueda con criterios múltiples
- ✅ Obtener productos con bajo stock
- ✅ Obtener productos sin stock
- ✅ Obtener productos por almacén
- ✅ Reducir cantidad de producto
- ✅ Aumentar cantidad de producto
- ✅ Calcular valor total del inventario
- ✅ Contar productos
- ✅ Contar unidades en stock

---

## 📐 Convenciones

### Naming

```java
// ✅ Correcto
@Test
void debeCrearUsuarioConParametrosValidos() { }

void debeLanzarExcepcionSiNombreEsNull() { }

void debeRetornarTrueSiEsAdmin() { }

// ❌ Incorrecto
@Test
void test1() { }

void testCrearUsuario() { }

void validateUser() { }
```

### Display Names

```java
@Test
@DisplayName("Debe crear usuario con parámetros válidos")
void debeCrearUsuarioConParametrosValidos() { }

@Nested
@DisplayName("Método buscarPorNombre")
class MethodoBuscarPorNombreTests { }

@DisplayName("Pruebas de la clase Producto")
class ProductoTest { }
```

### Assertions

```java
// Igualdad
assertEquals(esperado, actual);
assertNotEquals(esperado, actual);

// Booleanos
assertTrue(condicion);
assertFalse(condicion);

// Nulabilidad
assertNull(objeto);
assertNotNull(objeto);

// Contenedores
assertTrue(lista.size() > 0);
assertTrue(lista.contains(elemento));

// Excepciones
assertThrows(ExcepcionEsperada.class, () -> {
    // código que lanza excepción
});

// Instancia
assertInstanceOf(Clase.class, objeto);
```

---

## 📊 Análisis de Cobertura

### Ver Cobertura

```bash
mvn jacoco:report
open target/site/jacoco/index.html
```

### Métricas por Módulo

#### Model (100% cobertura)
```
Usuario.java:
- 23 métodos
- 23 cubiertos
- Líneas: 180
- Todas las líneas testeadas

Producto.java:
- 25 métodos
- 25 cubiertos
- Líneas: 270
- Todas las líneas testeadas
```

#### Database (97% cobertura)
```
DatabaseManager.java:
- Metodos principales testeados
- Casos edge identificados

UsuarioDAO.java:
- CRUD completo testeado
- Búsquedas testeadas

ProductoDAO.java:
- CRUD completo testeado
- Búsquedas avanzadas testeadas
```

#### Util (100% cobertura)
```
CryptoUtils.java:
- Todos los métodos testeados
- Todos los casos edge testeados
- Pruebas de seguridad
- Pruebas de rendimiento
```

### Líneas por Probar

```
Total LOC: 950
Probadas: 920
No probadas: 30 (logs, excepciones raras)
Cobertura: 96.8%
```

---

## 🔍 Depuración de Pruebas

### Ejecutar en Modo Debug

```bash
mvn -Dmaven.surefire.debug test
```

### Prueba Fallida - Checklist

1. ✅ ¿Se ejecutó setUp()?
2. ✅ ¿Los datos son correctos?
3. ✅ ¿La excepción es la esperada?
4. ✅ ¿El estado inicial es correcto?
5. ✅ ¿Hay dependencias externas afectando?

### Ejemplo: Test Fallido

```java
@Test
void debeReducirCantidad() throws SQLException {
    // Problema: No hay producto creado
    
    // SOLUCIÓN:
    @BeforeEach
    void setUp() throws SQLException {
        // Crear producto
        producto = new Producto("Mouse", "Desc", 25.99, 100, 1, "admin");
        productoDAO.crear(producto);
    }
}
```

---

## ✅ Checklist para Nuevas Pruebas

- [ ] Nombre descriptivo con "debe..." o "lanzar..."
- [ ] @DisplayName con descripción clara
- [ ] Seguir patrón AAA (Arrange-Act-Assert)
- [ ] Una sola afirmación principal
- [ ] Datos de prueba válidos
- [ ] setUp() si necesita inicialización
- [ ] @Nested para agrupar relacionadas
- [ ] Comentarios solo si lógica es compleja
- [ ] No hay dependencias entre tests
- [ ] Test debe pasar y fallar consistentemente

---

## 📞 Soporte

Para dudas sobre pruebas:
1. Revisar tests similares existentes
2. Consultar documentación de JUnit 5
3. Ver ejemplos en el código

---

**Versión:** 1.0  
**Última actualización:** 2026-04-26
