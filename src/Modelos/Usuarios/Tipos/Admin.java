package Modelos.Usuarios.Tipos;

import Modelos.Gestores.GestorAlojamientos;
import Modelos.Gestores.GestorPagos;
import Modelos.Gestores.GestorUsuarios;
import Modelos.Usuarios.Usuario;

import java.time.LocalDateTime;
import Enum.TipoCliente;

public class Admin extends Usuario {
    ///  SIN ATRIBUTOS
    /// Constructor
    public Admin(int id, float valoracion, LocalDateTime fechaDeRegistro, String numeroTelefonico, String mail, String nombre, String contrasena, TipoCliente tipoCliente) {
        super(valoracion, fechaDeRegistro, numeroTelefonico, mail, nombre, contrasena, tipoCliente);
    }

    /// METODOS
    public void generarCodigoDescuento (int idCodigo){

    }

    // Listar todos los usuarios registrados
    public void listarUsuarios(GestorUsuarios gestorUsuarios) {
        System.out.println("=== Lista de usuarios ===");
        gestorUsuarios.mostrarUsuarios();
    }

    // Listar todos los alojamientos del sistema
    public void listarAlojamientos(GestorAlojamientos gestorAlojamientos) {
        System.out.println("=== Lista de alojamientos ===");
        gestorAlojamientos.mostrarAlojamientosDisponibles();
    }

    // Listar todos los pagos del sistema
    public void listarPagos(GestorPagos gestorPagos) {
        System.out.println("=== Lista de pagos ===");
        gestorPagos.mostrarPagos();

}
}
