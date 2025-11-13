package model.usuario;

import java.time.LocalDateTime;

public class Administrador extends Usuario {

    public Administrador(String nombre, String email, String contrasena) {
        super(nombre, email, contrasena, TipoUsuario.ADMINISTRADOR);
    }

    public Administrador(
            int id,
            String nombre,
            String email,
            String contrasena,
            String telefono,
            LocalDateTime fecha
    ) {
        super(id, nombre, email, contrasena, telefono, fecha, TipoUsuario.ADMINISTRADOR);
    }
}
