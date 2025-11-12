package model.alojamiento;

import contract.Identificable;

import java.util.ArrayList;
import java.util.List;

public class Alojamiento implements Identificable {
    private static int contador = 0;
    private int id;
    private String direccion;
    private String tipo; // Puede ser:  casa, departamento, habitación
    private String descripcion;
    private float precioPorNoche;
    private List<String> diasDisponibles;
    private int idAnfitrion;

    public Alojamiento(String direccion, String tipo, String descripcion, float precioPorNoche, List<String> diasDisponibles, int idAnfitrion) {
        this.id = ++contador;
        this.direccion = direccion;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.precioPorNoche = precioPorNoche;
        this.diasDisponibles = new ArrayList<>(diasDisponibles);
        this.idAnfitrion = idAnfitrion;
    }


    public int getIdAlojamiento() {
        return id;
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
                "id=" + id +
                ", direccion='" + direccion + '\'' +
                ", tipo='" + tipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precioPorNoche=" + precioPorNoche +
                ", diasDisponibles=" + diasDisponibles +
                ", idAnfitrion=" + idAnfitrion +
                '}';
    }

    public int getId() {
        return id;
    }
}
