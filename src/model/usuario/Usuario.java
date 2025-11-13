package model.usuario;

import java.time.LocalDateTime;

import contract.Identificable;
import exception.UsuarioNoEncontradoException;
import contract.Autenticable;

/**
 * Clase abstracta base para todos los tipos de usuario del sistema.
 */
public abstract class Usuario implements Autenticable, Identificable {

    protected static int contador = 0;

    protected int id;
    protected String nombre;
    protected String email;
    protected String contrasena;
    protected String numeroTelefonico;
    protected LocalDateTime fechaRegistro;
    protected TipoUsuario tipoUsuario;
    protected boolean sesionActiva = false;

    /**
     * Constructor para crear un usuario nuevo (ID autogenerado).
     */
    public Usuario(String nombre, String email, String contrasena, TipoUsuario tipoUsuario) {
        this.id = ++contador;
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
        this.fechaRegistro = LocalDateTime.now();
    }

    /**
     * Constructor completo con ID (para cargar desde JSON).
     */
    public Usuario(
            int id,
            String nombre,
            String email,
            String contrasena,
            String telefono,
            LocalDateTime fecha,
            TipoUsuario tipoUsuario
    ) {
        this.id = id;
        if (id > contador) contador = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.numeroTelefonico = telefono;
        this.fechaRegistro = (fecha != null) ? fecha : LocalDateTime.now();
        this.tipoUsuario = tipoUsuario;
    }


    @Override
    public int getId() { return id; }

    public String getNombre() { return nombre; }

    public String getEmail() { return email; }

    public String getContrasena() { return contrasena; }

    public TipoUsuario getTipoUsuario() { return tipoUsuario; }

    public boolean isSesionActiva() { return sesionActiva; }

    public String getTelefono() { return numeroTelefonico; }

    public LocalDateTime getfechaRegistro() { return fechaRegistro; }


    public static void actualizarContador(int ultimoId) {
        if (ultimoId > contador) contador = ultimoId;
    }

    public static int getSiguienteId() {
        return ++contador;
    }


    @Override
    public boolean iniciarSesion(String email, String contrasena) {
        if (this.email.equals(email) && this.contrasena.equals(contrasena)) {
            this.sesionActiva = true;
            return true;
        }
        throw new UsuarioNoEncontradoException("Email o contraseña incorrectos.");
    }

    @Override
    public void cerrarSesion() {
        this.sesionActiva = false;
    }


    public void actualizarPerfil(String nombre, String email, String contrasena, String telefono) {
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.numeroTelefonico = telefono;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", tipoUsuario=" + tipoUsuario +
                ", telefono='" + (numeroTelefonico != null ? numeroTelefonico : "no especificado") + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}