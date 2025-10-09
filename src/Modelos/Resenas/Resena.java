package Modelos.Resenas;

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

    public void SubirResena(int id_r)
    {

    }

    public void EliminarResena(int id_r)
    {

    }
}
