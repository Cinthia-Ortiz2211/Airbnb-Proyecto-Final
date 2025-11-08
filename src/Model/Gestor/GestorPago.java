package Model.Gestor;

import Model.Pago.Pago;
import java.util.List;

public class GestorPago extends Gestor<Pago> {

    public Pago buscarPorId(int idPago) {
        List<Pago> pagos = listar();
        for (Pago p : pagos) {
            if (p.getIdPago() == idPago) {
                return p;
            }
        }
        return null;
    }

    public void listarPagosPorReserva(int idReserva) {
        List<Pago> pagos = listar();
        boolean encontrado = false;
        for (Pago p : pagos) {
            if (p.getIdReserva() == idReserva) {
                System.out.println(p);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No hay pagos asociados a la reserva con ID: " + idReserva);
        }
    }

    public void mostrarPagos() {
        List<Pago> pagos = listar();
        if (pagos.isEmpty()) {
            System.out.println("No hay pagos registrados");
            return;
        }

        for (Pago p : pagos) {
            System.out.println(p);
        }
    }
}
