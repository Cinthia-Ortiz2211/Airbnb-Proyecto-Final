package model.Gestor;

import model.Alojamiento.Alojamiento;
import java.util.List;

public class GestorAlojamiento extends Gestor<Alojamiento> {

    public Alojamiento buscarPorId(int id) {
        List<Alojamiento> alojamientos = listar();
        for (Alojamiento a : alojamientos) {
            if (a.getIdAlojamiento() == id) {
                return a;
            }
        }
        return null;
    }

    public void listarAlojamientosPorAnfitrion(int idAnfitrion) {
        List<Alojamiento> alojamientos = listar();
        boolean encontrado = false;
        for (Alojamiento a : alojamientos) {
            if (a.getIdAnfitrion() == idAnfitrion) {
                System.out.println(a);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("El anfitrión no tiene alojamientos registrados ");
        }
    }

    public void mostrarAlojamientosDisponibles() {
        List<Alojamiento> alojamientos = listar();
        if (alojamientos.isEmpty()) {
            System.out.println("nno hay alojamientos cargados ");
            return;
        }

        for (Alojamiento a : alojamientos) {
            System.out.println(a);
        }
    }
}
