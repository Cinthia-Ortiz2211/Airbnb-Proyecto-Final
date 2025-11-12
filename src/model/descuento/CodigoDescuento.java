package model.descuento;

import contract.Identificable;

import java.time.LocalDateTime;

public class CodigoDescuento implements Identificable {

    private static int contador = 0;

    private int id;
    private String codigo;
    private TipoCodigoDescuento tipo;
    private double monto;
    private LocalDateTime fechaExpiracion;


    public CodigoDescuento(String codigo, TipoCodigoDescuento tipo, double monto, LocalDateTime fechaExpiracion) {
        this.id = ++contador;
        this.codigo = codigo;
        this.tipo = tipo;
        this.monto = monto;
        this.fechaExpiracion = fechaExpiracion;
    }

    public CodigoDescuento(int id, String codigo, TipoCodigoDescuento tipo, double monto, LocalDateTime fechaExpiracion) {
        this.id = id;
        if (id > contador) contador = id;
        this.codigo = codigo;
        this.tipo = tipo;
        this.monto = monto;
        this.fechaExpiracion = fechaExpiracion;
    }

    @Override
    public int getId() {
        return id;
    }

    public static void actualizarContador(int ultimoId) {
        if (ultimoId > contador) contador = ultimoId;
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
        return "Código #" + id + " (" + codigo + ") | Tipo: " + tipo +
                " | Monto: " + monto + " | Expira: " + fechaExpiracion;
    }
}