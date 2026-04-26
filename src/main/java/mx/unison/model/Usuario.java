package mx.unison.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Objects;

/**
 * Entidad de Usuario para el sistema de gestión de inventario.
 * 
 * Representa un usuario del sistema con información de autenticación y roles.
 * Esta clase está anotada con ORMLite para permitir la persistencia automática
 * en la base de datos SQLite.
 * 
 * <p><strong>Roles disponibles:</strong></p>
 * <ul>
 *   <li><b>ADMIN</b> - Acceso completo al sistema</li>
 *   <li><b>PRODUCTOS</b> - Gestión de productos</li>
 *   <li><b>INVITADO</b> - Acceso de solo lectura</li>
 * </ul>
 * 
 * <p><strong>Seguridad:</strong></p>
 * Las contraseñas se almacenan hasheadas usando MD5.
 * 
 * @author Irlanda Karolina Atondo Ruiz
 * @version 2.0
 * @since 1.0
 * 
 * @see CryptoUtils
 * @see com.j256.ormlite.field.DatabaseField
 */
@DatabaseTable(tableName = "usuarios")
public class Usuario {

    /**
     * Identificador único del usuario.
     * Campo clave primaria auto-generada por la base de datos.
     */
    @DatabaseField(generatedId = true)
    private int id;

    /**
     * Nombre de usuario (identificador único).
     * Requerido, no puede ser nulo y debe ser único en la base de datos.
     */
    @DatabaseField(canBeNull = false, unique = true)
    private String username;

    /**
     * Hash MD5 de la contraseña del usuario.
     * Requerido, se almacena hasheada por seguridad.
     * 
     * @see CryptoUtils#md5(String)
     */
    @DatabaseField(canBeNull = false)
    private String passwordHash;

    /**
     * Rol del usuario en el sistema.
     * Define los permisos y acciones disponibles.
     * Valores válidos: ADMIN, PRODUCTOS, INVITADO
     */
    @DatabaseField(canBeNull = false, defaultValue = "INVITADO")
    private String rol;

    /**
     * Indica si el usuario está activo en el sistema.
     * Requerido, por defecto es true.
     */
    @DatabaseField(canBeNull = false, defaultValue = "true")
    private boolean activo;

    /**
     * Timestamp de creación del usuario en formato unix time.
     */
    @DatabaseField(canBeNull = false)
    private long fechaCreacion;

    /**
     * Nombre del usuario que creó este registro (para auditoría).
     */
    @DatabaseField(canBeNull = true)
    private String creadoPor;

    /**
     * Constructor sin argumentos requerido por ORMLite.
     */
    public Usuario() {
        this.activo = true;
        this.fechaCreacion = System.currentTimeMillis();
        this.rol = "INVITADO";
    }

    /**
     * Constructor con parámetros principales.
     * 
     * @param username nombre de usuario
     * @param passwordHash hash MD5 de la contraseña
     * @param rol rol del usuario (ADMIN, PRODUCTOS, INVITADO)
     * @param creadoPor usuario que creó este registro
     */
    public Usuario(String username, String passwordHash, String rol, String creadoPor) {
        this();
        this.username = username;
        this.passwordHash = passwordHash;
        this.rol = rol;
        this.creadoPor = creadoPor;
    }

    // ==================== Getters y Setters ====================

    /**
     * Obtiene el identificador único del usuario.
     * @return ID del usuario
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del usuario.
     * @param id identificador único
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de usuario.
     * @return nombre de usuario
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el nombre de usuario.
     * @param username nombre único del usuario
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene el hash de la contraseña.
     * 
     * @return hash MD5 de la contraseña
     * @see CryptoUtils#md5(String)
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Establece el hash de la contraseña.
     * 
     * @param passwordHash hash MD5 de la contraseña
     * @see CryptoUtils#md5(String)
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Obtiene el rol del usuario.
     * @return rol del usuario (ADMIN, PRODUCTOS, INVITADO)
     */
    public String getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     * @param rol rol a asignar
     * @throws IllegalArgumentException si el rol no es válido
     */
    public void setRol(String rol) {
        if (!isRolValido(rol)) {
            throw new IllegalArgumentException("Rol inválido: " + rol);
        }
        this.rol = rol;
    }

    /**
     * Verifica si el usuario está activo.
     * @return true si está activo, false en caso contrario
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Establece el estado activo/inactivo del usuario.
     * @param activo estado del usuario
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Obtiene la fecha de creación del usuario.
     * @return timestamp de creación en milisegundos
     */
    public long getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Establece la fecha de creación del usuario.
     * @param fechaCreacion timestamp en milisegundos
     */
    public void setFechaCreacion(long fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Obtiene el nombre del usuario que creó este registro.
     * @return usuario auditor
     */
    public String getCreadoPor() {
        return creadoPor;
    }

    /**
     * Establece el usuario que creó este registro (para auditoría).
     * @param creadoPor nombre del usuario creador
     */
    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    // ==================== Métodos de Validación ====================

    /**
     * Valida si el rol proporcionado es válido.
     * 
     * <p>Los roles válidos son:
     * <ul>
     *   <li>ADMIN</li>
     *   <li>PRODUCTOS</li>
     *   <li>INVITADO</li>
     * </ul>
     * 
     * @param rol rol a validar
     * @return true si el rol es válido, false en caso contrario
     */
    public static boolean isRolValido(String rol) {
        return rol != null && (rol.equals("ADMIN") || rol.equals("PRODUCTOS") || rol.equals("INVITADO"));
    }

    /**
     * Verifica si el usuario tiene permisos administrativos.
     * @return true si el rol es ADMIN
     */
    public boolean isAdmin() {
        return "ADMIN".equals(rol);
    }

    /**
     * Verifica si el usuario tiene permisos para gestionar productos.
     * @return true si el rol es ADMIN o PRODUCTOS
     */
    public boolean puedeGestionarProductos() {
        return "ADMIN".equals(rol) || "PRODUCTOS".equals(rol);
    }

    // ==================== Object Override Methods ====================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id == usuario.id && Objects.equals(username, usuario.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", rol='" + rol + '\'' +
                ", activo=" + activo +
                ", fechaCreacion=" + fechaCreacion +
                ", creadoPor='" + creadoPor + '\'' +
                '}';
    }
}
