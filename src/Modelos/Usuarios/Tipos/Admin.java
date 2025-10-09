package Modelos.Usuarios.Tipos;

import Modelos.Usuarios.Usuario;

import java.time.LocalDateTime;
import Enum.TipoCliente;

public class Admin extends Usuario {
    ///  SIN ATRIBUTOS
    /// Constructor
    public Admin(int id, float valoracion, LocalDateTime fechaDeRegistro, String numeroTelefonico, String mail, String nombre, String contrasena, TipoCliente tipoCliente) {
        super(id, valoracion, fechaDeRegistro, numeroTelefonico, mail, nombre, contrasena, tipoCliente);
    }

    /// METODOS
    public void generarCodigoDescuento (int idCodigo){

    }

    public void listarUsuarios(){
    }

    public void listarAlojamientos(){

    }

    public void listarPagos(){

    }

}
