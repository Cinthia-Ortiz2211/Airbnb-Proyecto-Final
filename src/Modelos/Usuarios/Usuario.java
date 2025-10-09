package Modelos.Usuarios;

import java.time.LocalDateTime;
import java.util.List;
import Enum.TipoCliente;

public abstract class Usuario {
/// ATRIBUTOS
    private int id;
    private int count;
    private String nombre;
    private String mail;
    private String contrasena;
    private String numeroTelefonico;
    private LocalDateTime fechaDeRegistro;
    private float valoracion;
    private TipoCliente tipoCliente;

    /// CONSTRUCTORES.

    public Usuario(int id, float valoracion, LocalDateTime fechaDeRegistro, String numeroTelefonico, String mail, String nombre, String contrasena, TipoCliente tipoCliente) {
        this.id = count++;
        this.valoracion = valoracion;
        this.fechaDeRegistro = fechaDeRegistro;
        this.numeroTelefonico = numeroTelefonico;
        this.mail = mail;
        this.nombre = nombre;
        this.count = count;
        this.contrasena = contrasena;
        this.tipoCliente = tipoCliente;
    }

    ///  GETTERS AND SETTERS

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

   public LocalDateTime getFechaDeRegistro() {
        return fechaDeRegistro;
    }

    public void setFechaDeRegistro(LocalDateTime fechaDeRegistro) {
        this.fechaDeRegistro = fechaDeRegistro;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }

    /// ToString

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", mail='" + mail + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", numeroTelefonico='" + numeroTelefonico + '\'' +
                ", tipoDeUsuario='" + tipoCliente + '\'' +
                ", fechaDeRegistro=" + fechaDeRegistro +
                ", valoracion=" + valoracion +
                '}';
    }

    ///  METODOS

    public void registrar(String nombreUsuario, String contrasena, String mail){
        this.nombre = nombreUsuario;
        this.contrasena = contrasena;
        this.mail = mail;
        this.fechaDeRegistro = LocalDateTime.now();
        System.out.println("Usuario registrado correctamente");
    }

    public boolean ingresar (String nombreUsuario, String contrasena){
        return this.nombre.equals(nombreUsuario) && this.contrasena.equals(contrasena);
    }

    public void cerrarSesion(int idUsuario) {
        if (this.id == idUsuario) {
            System.out.println("Secion cerrada para el usuario con ID: " + idUsuario);
        }
    }

    public void actualizarPerfil (int idUsuario, String nombre, String mail, String contrasena, String numeroTelefonico){
        if (this.id == idUsuario){
            this.nombre = nombre;
            this.mail = mail;
            this.contrasena = contrasena;
            this.numeroTelefonico = numeroTelefonico;
            System.out.println("Perfil actualizado con exito!");
        }
    }


}
