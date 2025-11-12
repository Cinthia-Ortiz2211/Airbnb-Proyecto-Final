package model.alojamiento;

import java.util.ArrayList;
import java.util.List;

public class Alojamiento {
    private static int contador = 0;
    private int idAlojamiento;
    private String direccion;
    private String tipo; // Puede ser:  casa, departamento, habitación
    private String descripcion;
    private float precioPorNoche;
    private List<String> diasDisponibles;
    private int idAnfitrion;

    public Alojamiento(String direccion, String tipo, String descripcion, float precioPorNoche, List<String> diasDisponibles, int idAnfitrion) {
        this.idAlojamiento = ++contador;
        this.direccion = direccion;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.precioPorNoche = precioPorNoche;
        this.diasDisponibles = new ArrayList<>(diasDisponibles);
        this.idAnfitrion = idAnfitrion;
    }


    public int getIdAlojamiento() {
        return idAlojamiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public float getPrecioPorNoche() {
        return precioPorNoche;
    }

    public List<String> getDiasDisponibles() {
        return diasDisponibles;
    }

    public int getIdAnfitrion() {
        return idAnfitrion;
    }

    public void agregarDiaDisponible(String dia) {
        diasDisponibles.add(dia);
    }

    public void eliminarDiaDisponible(String dia) {
        diasDisponibles.remove(dia);
    }

    @Override
    public String toString() {
        return "Alojamiento{" +
                "idAlojamiento=" + idAlojamiento +
                ", direccion='" + direccion + '\'' +
                ", tipo='" + tipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precioPorNoche=" + precioPorNoche +
                ", diasDisponibles=" + diasDisponibles +
                ", idAnfitrion=" + idAnfitrion +
                '}';
    }
}
