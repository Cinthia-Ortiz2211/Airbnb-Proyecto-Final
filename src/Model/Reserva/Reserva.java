package model.Reserva;

import contract.Reservable;
import java.time.LocalDateTime;
import java.util.ArrayList;

import exception.ReservaInvalidaException;


public class Reserva implements Reservable {
    private static int contador = 0;
    private int idReserva;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private float costoTotal;
    private String estado; // Pendiente, Confirmada, Cancelada
    private ArrayList<String> diasReservados;
    private int idViajero;
    private int idAlojamiento;
    private int idPago;

    public Reserva(LocalDateTime fechaInicio, LocalDateTime fechaFin, float costoTotal,
                   ArrayList<String> diasReservados, int idViajero, int idAlojamiento) {
        if (fechaFin.isBefore(fechaInicio)) {
            throw new ReservaInvalidaException("La fecha de fin no puede ser anterior a la de inicio");
        }
        if (costoTotal <= 0) {
            throw new ReservaInvalidaException("El costo total de la reserva debe ser mayor a cero ");
        }
        if (diasReservados == null || diasReservados.isEmpty()) {
            throw new ReservaInvalidaException("Debe incluir al menos un dia reservado ");
        }

        this.idReserva = ++contador;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.diasReservados = diasReservados;
        this.costoTotal = costoTotal;
        this.idViajero = idViajero;
        this.idAlojamiento = idAlojamiento;
        this.estado = "Pendiente";
    }


    // immplementación de la interfaz Reservable
    @Override
    public void crearReserva() {
        System.out.println("Reserva creada con ID: " + idReserva);
    }

    @Override
    public void confirmarReserva() {
        this.estado = "Confirmada";
        System.out.println("Reserva " + idReserva + " confirmada.");
    }

    @Override
    public void cancelarReserva() {
        this.estado = "Cancelada";
        System.out.println("Reserva " + idReserva + " cancelada.");
    }

    public void asociarPago(int idPago) {
        this.idPago = idPago;
        System.out.println("Pago asociado a la reserva " + idReserva);
    }

    // Getters y toString
    public int getIdReserva() {
        return idReserva;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva=" + idReserva +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", costoTotal=" + costoTotal +
                ", estado='" + estado + '\'' +
                ", idViajero=" + idViajero +
                ", idAlojamiento=" + idAlojamiento +
                ", idPago=" + idPago +
                '}';
    }
}
