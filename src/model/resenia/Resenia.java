package model.resenia;

import model.usuario.*;
import model.alojamiento.Alojamiento;

import java.time.LocalDateTime;

public class Resenia {

    private static int contador = 0;

    private int id;
    private int puntaje;
    private String comentario;
    private Viajero autor;
    private LocalDateTime fecha;
    private Alojamiento alojamiento;
    private Anfitrion anfitrion;


    public Resenia(int puntaje, String comentario, Viajero autor,
                   LocalDateTime fecha, Alojamiento alojamiento, Anfitrion anfitrion) {
        this.id = getSiguienteId();
        this.puntaje = puntaje;
        this.comentario = comentario;
        this.autor = autor;
        this.fecha = fecha;
        this.alojamiento = alojamiento;
        this.anfitrion = anfitrion;
    }

    public Resenia(int id, int puntaje, String comentario, Viajero autor,
                   LocalDateTime fecha, Alojamiento alojamiento, Anfitrion anfitrion) {
        this.id = id;
        if (id > contador) contador = id;
        this.puntaje = puntaje;
        this.comentario = comentario;
        this.autor = autor;
        this.fecha = fecha;
        this.alojamiento = alojamiento;
        this.anfitrion = anfitrion;
    }


    public static int getSiguienteId() {
        return ++contador;
    }

    public static void actualizarContador(int ultimoId) {
        if (ultimoId > contador) contador = ultimoId;
    }


    public int getId() { return id; }
    public int getPuntaje() { return puntaje; }
    public String getComentario() { return comentario; }
    public Viajero getAutor() { return autor; }
    public LocalDateTime getFecha() { return fecha; }
    public Alojamiento getAlojamiento() { return alojamiento; }
    public Anfitrion getAnfitrion() { return anfitrion; }


    public String verDetalle() {
        String destino = (alojamiento != null)
                ? "Alojamiento: " + alojamiento.getDireccion()
                : "Anfitrión: " + (anfitrion != null ? anfitrion.getNombre() : "Desconocido");

        return "Reseña #" + id + " - " + destino +
                "\nAutor: " + (autor != null ? autor.getNombre() : "Desconocido") +
                "\nPuntaje: " + puntaje +
                "\nComentario: " + comentario +
                "\nFecha: " + fecha;
    }

    @Override
    public String toString() {
        return verDetalle();
    }
}