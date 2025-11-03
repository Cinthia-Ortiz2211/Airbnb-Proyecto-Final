package Modelos.Gestores;

import Modelos.Resenas.Resena;
import java.util.List;

public class GestorResenas extends Gestor<Resena> {

    //falta mostrar reseñans por alojamientos


    public void mostrarResenas() {
        List<Resena> resenas = listar();
        if (resenas.isEmpty()) {
            System.out.println("No hay reseñas cargadas.");
            return;
        }

        for (Resena r : resenas) {
            System.out.println(r);
        }
    }
}
