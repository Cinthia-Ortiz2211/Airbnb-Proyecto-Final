package model.pago;

import contract.Identificable;
import model.reserva.Reserva;
import model.enums.EstadoPago;

public class Pago implements Identificable {

    private static int contador = 0;

    private int id;
    private Reserva reserva;
    private double monto;
    private EstadoPago estado;

    public Pago(Reserva reserva, double monto) {
        this.id = ++contador;
        this.reserva = reserva;
        this.monto = monto;
        this.estado = EstadoPago.PENDIENTE;
    }

    public Pago(int id, Reserva reserva, double monto) {
        this.id = id;
        if (id > contador) contador = id;
        this.reserva = reserva;
        this.monto = monto;
        this.estado = EstadoPago.PENDIENTE;
    }


    public int getId() { return id; }
    public Reserva getReserva() { return reserva; }
    public double getMonto() { return monto; }
    public EstadoPago getEstado() { return estado; }
    public void setEstado(EstadoPago estado) { this.estado = estado; }

    public void procesar() {
        if (estado == EstadoPago.PENDIENTE) {
            estado = EstadoPago.APROBADO;
            System.out.println("Pago #" + id + " procesado correctamente.");
        } else {
            System.out.println("El pago #" + id + " ya fue procesado o rechazado.");
        }
    }

    public String verDetalle() {
        return "Pago #" + id +
                " | Reserva: " + (reserva != null ? reserva.getId() : "sin referencia") +
                " | Monto: $" + monto +
                " | Estado: " + estado;
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