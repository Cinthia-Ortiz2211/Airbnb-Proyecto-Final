package Modelos.Usuarios.Tipos;

import Modelos.Usuarios.Usuario;
import Enum.TipoCliente;

import java.time.LocalDateTime;

public class Viajero extends Usuario {
    /// CONSTRUCTOR
    public Viajero(int id, float valoracion, LocalDateTime fechaDeRegistro, String numeroTelefonico, String mail, String nombre, String contrasena, TipoCliente tipoCliente) {
        super(valoracion, fechaDeRegistro, numeroTelefonico, mail, nombre, contrasena, tipoCliente);
    }

    ///  METODOS
    public void buscarAlojamiento(){

    }

    public void filtrarAlojamientos(){

    }

    public void verHistorialDeReservas (int idUsuario){

    }

    public void enviarResena(int idResena){


    }
}
