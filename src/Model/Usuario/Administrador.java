package model.usuario;

public class Administrador extends Usuario {

    public Administrador(String nombre, String email, String contrasena) {
        super(nombre, email, contrasena, TipoUsuario.ADMINISTRADOR);
    }
}
