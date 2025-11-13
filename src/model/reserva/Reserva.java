package model.reserva;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import contract.Identificable;
import model.alojamiento.Alojamiento;
import model.usuario.Viajero;

public class Reserva implements Identificable {

    private static int contador = 0;

    private int id;
    private Alojamiento alojamiento;
    private Viajero viajero;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double costoTotal;
    private boolean pendiente;
    private EstadoReserva estadoReserva;


    public Reserva(Alojamiento alojamiento, Viajero viajero,
                   LocalDate fechaInicio, LocalDate fechaFin, boolean pendiente) {
        this.id = ++contador;
        this.alojamiento = alojamiento;
        this.viajero = viajero;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.pendiente = pendiente;
        this.costoTotal = calcularCosto();
        this.estadoReserva = EstadoReserva.PENDIENTE;
    }

    public Reserva(int id, Alojamiento alojamiento, Viajero viajero,
                   LocalDate fechaInicio, LocalDate fechaFin, boolean pendiente) {
        this.id = id;
        if (id > contador) contador = id;
        this.alojamiento = alojamiento;
        this.viajero = viajero;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.pendiente = pendiente;
        this.costoTotal = calcularCosto();
        this.estadoReserva = EstadoReserva.PENDIENTE;
    }

    public Reserva(Alojamiento alojamiento, Viajero viajero,
                   LocalDate fechaInicio, LocalDate fechaFin, double costoTotal) {
        this.id = ++contador;
        this.alojamiento = alojamiento;
        this.viajero = viajero;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.costoTotal = costoTotal;
        this.pendiente = true;
        this.estadoReserva = EstadoReserva.PENDIENTE;
    }

    public Reserva(int id, Alojamiento alojamiento, Viajero viajero,
                   LocalDate fechaInicio, LocalDate fechaFin, double costoTotal) {
        this.id = id;
        if (id > contador) contador = id;
        this.alojamiento = alojamiento;
        this.viajero = viajero;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.costoTotal = costoTotal;
        this.pendiente = true;
        this.estadoReserva = EstadoReserva.PENDIENTE;
    }


    public int getId() { return id; }
    public Alojamiento getAlojamiento() { return alojamiento; }
    public Viajero getViajero() { return viajero; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public double getCostoTotal() { return costoTotal; }
    public boolean isPendiente() { return pendiente; }
    public EstadoReserva getEstado() { return estadoReserva; }


    public double calcularCosto() {
        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        if (dias <= 0) {
            throw new IllegalArgumentException("La fecha de fin debe ser posterior a la de inicio.");
        }
        return dias * alojamiento.getPrecioPorNoche();
    }

    public void cancelar() {
        this.pendiente = false;
        System.out.println("Reserva #" + id + " cancelada con éxito.");
    }

    public String verDetalle() {
        return "Reserva #" + id + " | " +
                "Alojamiento: " + alojamiento.getDireccion() + " | " +
                "Viajero: " + viajero.getNombre() + " | " +
                "Desde: " + fechaInicio + " Hasta: " + fechaFin + " | " +
                "Costo total: $" + costoTotal + " | " +
                (pendiente ? "Pendiente" : "Confirmada/Cancelada");
    }

    public void setAlojamiento(Alojamiento nuevoAlojamiento) {
        if (this.estadoReserva != EstadoReserva.PENDIENTE) {
            throw new IllegalStateException("Solo se puede cambiar el alojamiento si la reserva está pendiente.");
        }
        this.alojamiento = nuevoAlojamiento;
        this.costoTotal = calcularCosto();
    }

    public static void actualizarContador(int ultimoId) {
        if (ultimoId > contador) contador = ultimoId;
    }

    public static int getSiguienteId() {
        return ++contador;
    }

    @Override
    public String toString() {
        return verDetalle();
    }
}