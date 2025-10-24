package Modelos.Usuarios.Tipos;

import Modelos.Usuarios.Usuario;

import java.time.LocalDateTime;
import Enum.TipoCliente;

public class Anfitrion extends Usuario {
    /// CONSTRUCTOR
    public Anfitrion(int id, float valoracion, LocalDateTime fechaDeRegistro, String numeroTelefonico, String mail, String nombre, String contrasena, TipoCliente tipoCliente) {
        super(valoracion, fechaDeRegistro, numeroTelefonico, mail, nombre, contrasena, tipoCliente);
    }

    /// METODOS
    public void verReservas (int idUsuario){

    }

    public void listarAlojamientos (int idUsuario){

    }
}
