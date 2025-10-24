package Modelos.Reservas;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Reserva {
    private static int contador = 0;
    private int idReserva;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private float costoTotal;
    private String estado; // o hacemos con string: pendiente, confirmada, cancelada ; o conm enum, o directamente, pendiende o cancelada
    private ArrayList<String> diasReservados;
    private int idViajero;
    private int idAlojamiento;
    private int idPago;

    public Reserva(LocalDateTime fechaInicio, LocalDateTime fechaFin, float costoTotal, ArrayList<String> diasReservados, int idViajero, int idAlojamiento) {
        this.idReserva = ++contador;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.diasReservados = diasReservados;
        this.costoTotal = costoTotal;
        this.idViajero = idViajero;
        this.idAlojamiento = idAlojamiento;
        this.estado = "pendiente";
    }

    public int getIdReserva() {
        return idReserva;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public float getCostoTotal() {
        return costoTotal;
    }

    public String getEstado() {
        return estado;
    }

    public int getIdViajero() {
        return idViajero;
    }

    public int getIdAlojamiento() {
        return idAlojamiento;
    }

    public void crearReserva() {
        System.out.println("Reserva creada con ID: " + idReserva);
    }

    public void cancelarReserva() {
        this.estado = "Cancelada";
        System.out.println("Reserva " + idReserva + " cancelada.");
    }

    public void confirmarReserva() {
        this.estado = "Confirmada";
        System.out.println("Reserva " + idReserva + " confirmada.");
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
                '}';
    }
}
