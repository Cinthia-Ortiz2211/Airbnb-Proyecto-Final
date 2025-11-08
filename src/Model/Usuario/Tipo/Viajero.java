package Model.Usuario.Tipo;

import Model.Alojamiento.Alojamiento;
import Model.Gestor.GestorAlojamiento;
import Model.Gestor.GestorResena;
import Model.Gestor.GestorReserva;
import Model.Resena.Resena;
import Model.Usuario.Usuario;
import Enum.TipoCliente;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Viajero extends Usuario {
    /// CONSTRUCTOR
    public Viajero(int id, float valoracion, LocalDateTime fechaDeRegistro, String numeroTelefonico, String mail, String nombre, String contrasena, TipoCliente tipoCliente) {
        super(valoracion, fechaDeRegistro, numeroTelefonico, mail, nombre, contrasena, tipoCliente);
    }

    ///  METODOS

    // Buscar alojamiento por tipo (ej: casa, depto)
    public void buscarAlojamiento(GestorAlojamiento gestorAlojamientos, String tipoBuscado) {
        List<Alojamiento> lista = gestorAlojamientos.listar();
        boolean encontrado = false;
        for (Alojamiento a : lista) {
            if (a.getTipo().equalsIgnoreCase(tipoBuscado)) {
                System.out.println(a);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontraron alojamientos del tipo: " + tipoBuscado);
        }
    }

    // Filtrar alojamientos por precio
    public void filtrarAlojamientos(GestorAlojamiento gestorAlojamientos, float precioMax) {
        List<Alojamiento> lista = gestorAlojamientos.listar();
        boolean encontrado = false;
        for (Alojamiento a : lista) {
            if (a.getPrecioPorNoche() <= precioMax) {
                System.out.println(a);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontraron alojamientos con precio menor o igual a " + precioMax);
        }
    }

    // Mostrar historial de reservas del viajero
    public void verHistorialDeReservas(GestorReserva gestorReservas, int idViajero) {
        System.out.println("=== Historial de reservas del viajero ID: " + idViajero + " ===");
        gestorReservas.listarReservasPorViajero(idViajero);
    }

    // MAdnar una reseña
    public void enviarResena(GestorResena gestorResenas, int puntaje, String comentario, int idAlojamiento) {
        Date fecha = new Date();
        Resena nueva = new Resena(puntaje, comentario, fecha, this.getId(), idAlojamiento);
        gestorResenas.agregar(nueva);
        System.out.println("Reseña enviada correctamente.");
    }
}
