package model.usuario;

public class Viajero extends Usuario {

    public Viajero(String nombre, String email, String contrasena) {
        super(nombre, email, contrasena, TipoUsuario.VIAJERO);
    }
}
