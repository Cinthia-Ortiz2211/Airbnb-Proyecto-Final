package model.usuario;

import java.time.LocalDateTime;

public class Viajero extends Usuario {

    public Viajero(String nombre, String email, String contrasena) {
        super(nombre, email, contrasena, TipoUsuario.VIAJERO);
    }

    public Viajero(
            int id,
            String nombre,
            String email,
            String contrasena,
            String telefono,
            LocalDateTime fecha
    ) {
        super(id, nombre, email, contrasena, telefono, fecha, TipoUsuario.VIAJERO);
    }
}
