package model.resenia;

import model.gestor.GestorResena;
import java.util.Date;

public class Resena {
    private static int c = 0;
    private int id_resena;
    private int puntaje;
    private String comentario;
    private Date fecha;
    private int id_autor;
    private int id_alojamiento;

    public Resena(int puntaje, String comentario, Date fecha, int id_autor, int id_alojamiento) {
        this.id_resena = ++c;
        this.puntaje = puntaje;
        this.comentario = comentario;
        this.fecha = fecha;
        this.id_autor = id_autor;
        this.id_alojamiento = id_alojamiento;
    }

    public int getIdResena() {
        return id_resena;
    }

    public int getIdAlojamiento() {
        return id_alojamiento;
    }

    public void SubirResena(GestorResena gestor) {
        gestor.agregar(this);
        System.out.println("Reseña subida con ID: " + id_resena);
    }

    public void EliminarResena(GestorResena gestor) {
        if (gestor.eliminar(this)) {
            System.out.println("Reseña eliminada (ID: " + id_resena + ")");
        } else {
            System.out.println("No se pudo eliminar la reseña ");
        }
    }

    @Override
    public String toString() {
        return "Resena{" +
                "id_resena=" + id_resena +
                ", puntaje=" + puntaje +
                ", comentario='" + comentario + '\'' +
                ", fecha=" + fecha +
                ", id_autor=" + id_autor +
                ", id_alojamiento=" + id_alojamiento +
                '}';
    }
}
