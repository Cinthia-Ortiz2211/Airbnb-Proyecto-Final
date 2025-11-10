package model.usuario;

public class Anfitrion extends Usuario {

    public Anfitrion(String nombre, String email, String contrasena) {
        super(nombre, email, contrasena, TipoUsuario.ANFITRION);
    }
}
