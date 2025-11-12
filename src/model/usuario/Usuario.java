package model.usuario;

import java.time.LocalDateTime;

import contract.Identificable;
import exception.UsuarioNoEncontradoException;
import contract.Autenticable;

public abstract class Usuario implements Autenticable, Identificable {

    private static int contador = 0;

    protected int id;
    protected String nombre;
    protected String email;
    protected String contrasena;
    protected String numeroTelefonico;
    protected LocalDateTime fechaDeRegistro;
    protected TipoUsuario tipoUsuario;
    protected boolean sesionActiva = false;

    public Usuario(String nombre, String email, String contrasena, TipoUsuario tipoUsuario) {
        this.id = ++contador;
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
        this.fechaDeRegistro = LocalDateTime.now();
    }


    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public TipoUsuario getTipoUsuario() { return tipoUsuario; }
    public boolean isSesionActiva() { return sesionActiva; }


    @Override
    public boolean iniciarSesion(String email, String contrasena) {
        if (this.email.equals(email) && this.contrasena.equals(contrasena)) {
            this.sesionActiva = true;
            System.out.println(nombre + " inició sesión correctamente.");
            return true;
        }
        throw new UsuarioNoEncontradoException("Email o contraseña incorrectos.");
    }

    @Override
    public void cerrarSesion() {
        if (this.sesionActiva) {
            this.sesionActiva = false;
            System.out.println(nombre + " cerró sesión correctamente.");
        } else {
            System.out.println("El usuario " + nombre + " no tiene una sesión activa.");
        }
    }

    public void actualizarPerfil(String nombre, String email, String contrasena, String telefono) {
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.numeroTelefonico = telefono;
        System.out.println("Perfil actualizado correctamente para: " + this.nombre);
    }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s) - %s", id, nombre, tipoUsuario, email);
    }
}
