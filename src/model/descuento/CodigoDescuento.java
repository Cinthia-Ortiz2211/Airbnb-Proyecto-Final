package model.descuento;

import java.time.LocalDateTime;

public class CodigoDescuento {

    private String codigo;
    private TipoCodigoDescuento tipo;
    private double monto;
    private LocalDateTime fechaExpiracion;


    public CodigoDescuento(String codigo, TipoCodigoDescuento tipo, double monto, LocalDateTime fechaExpiracion) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.monto = monto;
        this.fechaExpiracion = fechaExpiracion;
    }


    public String getCodigo() { return codigo; }
    public TipoCodigoDescuento getTipo() { return tipo; }
    public double getMonto() { return monto; }
    public LocalDateTime getFechaExpiracion() { return fechaExpiracion; }


    public boolean esValido(LocalDateTime fechaActual) {
        return fechaActual.isBefore(fechaExpiracion);
    }

    public double aplicarDescuento(double total) {
        if (tipo == TipoCodigoDescuento.PORCENTAJE) {
            return total - (total * monto / 100);
        } else {
            return Math.max(0, total - monto);
        }
    }

    public String verDetalle() {
        return "Código: " + codigo +
                " | Tipo: " + tipo +
                " | Monto: " + monto +
                " | Expira: " + fechaExpiracion;
    }

    @Override
    public String toString() {
        return verDetalle();
    }
}