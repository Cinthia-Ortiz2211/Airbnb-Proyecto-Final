package Model.Gestor;

import Model.Reserva.Reserva;
import java.util.List;

public class GestorReserva extends Gestor<Reserva> {

    public Reserva buscarPorId(int idReserva) {
        List<Reserva> reservas = listar();
        for (Reserva r : reservas) {
            if (r.getIdReserva() == idReserva) {
                return r;
            }
        }
        return null;
    }

    public void listarReservasPorViajero(int idViajero) {
        List<Reserva> reservas = listar();
        boolean encontrado = false;
        for (Reserva r : reservas) {
            if (r.toString().contains("idViajero=" + idViajero)) {
                System.out.println(r);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("El viajero no tiene reservas registradas.");
        }
    }

    public void listarReservasPorEstado(String estado) {
        List<Reserva> reservas = listar();
        for (Reserva r : reservas) {
            if (r.getEstado().equalsIgnoreCase(estado)) {
                System.out.println(r);
            }
        }
    }
}
