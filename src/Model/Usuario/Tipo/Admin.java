package Model.Usuario.Tipo;

import Model.Gestor.GestorAlojamiento;
import Model.Gestor.GestorPago;
import Model.Gestor.GestorUsuario;
import Model.Usuario.Usuario;

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
    public void listarUsuarios(GestorUsuario gestorUsuarios) {
        System.out.println("=== Lista de usuarios ===");
        gestorUsuarios.mostrarUsuarios();
    }

    // Listar todos los alojamientos del sistema
    public void listarAlojamientos(GestorAlojamiento gestorAlojamientos) {
        System.out.println("=== Lista de alojamientos ===");
        gestorAlojamientos.mostrarAlojamientosDisponibles();
    }

    // Listar todos los pagos del sistema
    public void listarPagos(GestorPago gestorPagos) {
        System.out.println("=== Lista de pagos ===");
        gestorPagos.mostrarPagos();

}
}
