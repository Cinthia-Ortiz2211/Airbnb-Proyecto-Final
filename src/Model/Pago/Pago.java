package Model.Pago;

import java.time.LocalDateTime;

public class Pago {
    private static int contador = 0;
    private int idPago;
    private float monto;
    private LocalDateTime fechaDePago;
    private String estadoDePago; // Puede ser: "Aprobado", "Rechazado", "Pendiente"
    private int idReserva;

    public Pago(float monto, int idReserva) {
        this.idPago = ++contador;
        this.monto = monto;
        this.idReserva = idReserva;
        this.estadoDePago = "Pendiente";
        this.fechaDePago = null;
    }

    // ---------- METODOS PRINCIPALES ----------
    public void procesarPago() {
        System.out.println("Procesando pago ID " + idPago + " por $" + monto + "...");
        this.fechaDePago = LocalDateTime.now();
        this.estadoDePago = "Pendiente";
    }

    public void confirmarPago() {
        if (!this.estadoDePago.equals("Aprobado")) {
            this.estadoDePago = "Aprobado";
            this.fechaDePago = LocalDateTime.now();
            System.out.println("Pago confirmado (ID " + idPago + ")");
        } else {
            System.out.println("El pago ya estaba aprobado.");
        }
    }

    public void rechazarPago() {
        this.estadoDePago = "Rechazado";
        this.fechaDePago = LocalDateTime.now();
        System.out.println("Pago rechazado (ID " + idPago + ")");
    }

    // ---------- GETTERS Y SETTERS ----------
    public int getIdPago() {
        return idPago;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaDePago() {
        return fechaDePago;
    }

    public void setFechaDePago(LocalDateTime fechaDePago) {
        this.fechaDePago = fechaDePago;
    }

    public String getEstadoDePago() {
        return estadoDePago;
    }

    public void setEstadoDePago(String estadoDePago) {
        this.estadoDePago = estadoDePago;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "idPago=" + idPago +
                ", monto=" + monto +
                ", fechaDePago=" + fechaDePago +
                ", estadoDePago='" + estadoDePago + '\'' +
                ", idReserva=" + idReserva +
                '}';
    }
}
