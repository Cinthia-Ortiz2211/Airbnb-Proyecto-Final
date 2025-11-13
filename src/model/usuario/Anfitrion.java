package model.usuario;

import java.time.LocalDateTime;

public class Anfitrion extends Usuario {

    public Anfitrion(String nombre, String email, String contrasena) {
        super(nombre, email, contrasena, TipoUsuario.ANFITRION);
    }

    public Anfitrion(
            int id,
            String nombre,
            String email,
            String contrasena,
            String telefono,
            LocalDateTime fecha
    ) {
        super(id, nombre, email, contrasena, telefono, fecha, TipoUsuario.ANFITRION);
    }
}
