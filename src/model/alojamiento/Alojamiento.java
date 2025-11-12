package model.alojamiento;

import model.usuario.Anfitrion;
import model.enums.TipoAlojamiento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Alojamiento {

    private static int contador = 0;

    private int id;
    private String direccion;
    private TipoAlojamiento tipo;
    private String nivel;
    private String descripcion;
    private double precioPorNoche;
    private List<LocalDate> fechasDisponibles;
    private Anfitrion anfitrion;


    public Alojamiento() {
        this.id = ++contador;
        this.fechasDisponibles = new ArrayList<>();
    }

    public Alojamiento(
            int id,
            String direccion,
            String tipoStr,
            String nivel,
            String descripcion,
            double precioPorNoche,
            List<LocalDate> fechasDisponibles,
            Anfitrion anfitrion
    ) {
        this.id = id;
        if (id > contador) contador = id; // sincroniza el contador al leer desde JSON

        this.direccion = direccion;
        try {
            this.tipo = TipoAlojamiento.valueOf(tipoStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            this.tipo = TipoAlojamiento.CASA;
        }
        this.nivel = nivel;
        this.descripcion = descripcion;
        this.precioPorNoche = precioPorNoche;
        this.fechasDisponibles = (fechasDisponibles != null) ? fechasDisponibles : new ArrayList<>();
        this.anfitrion = anfitrion;
    }

    public Alojamiento(
            String direccion,
            TipoAlojamiento tipo,
            String nivel,
            String descripcion,
            double precioPorNoche,
            List<LocalDate> fechasDisponibles,
            Anfitrion anfitrion
    ) {
        this.id = ++contador;
        this.direccion = direccion;
        this.tipo = tipo;
        this.nivel = nivel;
        this.descripcion = descripcion;
        this.precioPorNoche = precioPorNoche;
        this.fechasDisponibles = (fechasDisponibles != null) ? fechasDisponibles : new ArrayList<>();
        this.anfitrion = anfitrion;
    }


    public int getId() { return id; }
    public void setId(int id) {
        this.id = id;
        if (id > contador) contador = id; // sincroniza el contador al restaurar desde JSON
    }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public TipoAlojamiento getTipo() { return tipo; }
    public void setTipo(TipoAlojamiento tipo) { this.tipo = tipo; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecioPorNoche() { return precioPorNoche; }
    public void setPrecioPorNoche(double precioPorNoche) { this.precioPorNoche = precioPorNoche; }

    public List<LocalDate> getFechasDisponibles() { return fechasDisponibles; }
    public void setFechasDisponibles(List<LocalDate> fechasDisponibles) { this.fechasDisponibles = fechasDisponibles; }

    public Anfitrion getAnfitrion() { return anfitrion; }
    public void setAnfitrion(Anfitrion anfitrion) { this.anfitrion = anfitrion; }


    public boolean esDisponible(LocalDate inicio, LocalDate fin) {
        if (fechasDisponibles == null || fechasDisponibles.isEmpty()) return false;
        for (int i = 0; i < fechasDisponibles.size(); i++) {
            LocalDate fecha = fechasDisponibles.get(i);
            if (!fecha.isBefore(inicio) && !fecha.isAfter(fin)) {
                return true;
            }
        }
        return false;
    }

    public static void actualizarContador(int ultimoId) {
        if (ultimoId > contador) contador = ultimoId;
    }

    public static int getSiguienteId() {
        return ++contador;
    }

    @Override
    public String toString() {
        return "Alojamiento{" +
                "id=" + id +
                ", direccion='" + direccion + '\'' +
                ", tipo=" + tipo +
                ", nivel='" + nivel + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precioPorNoche=" + precioPorNoche +
                ", anfitrion=" + (anfitrion != null ? anfitrion.getEmail() : "sin anfitrión") +
                '}';
    }
}