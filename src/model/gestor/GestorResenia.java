package model.gestor;

import contract.Persistible;
import model.resenia.Resenia;
import model.usuario.*;
import model.alojamiento.Alojamiento;
import util.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.List;

public class GestorResenia extends Gestor<Resenia> implements Persistible {

    private static final String ARCHIVO = "src/persistence/resenias.json";

    private GestorUsuario gestorUsuario;
    private GestorAlojamiento gestorAlojamiento;


    public GestorResenia(GestorUsuario gestorUsuario, GestorAlojamiento gestorAlojamiento) {
        super();
        this.gestorUsuario = gestorUsuario;
        this.gestorAlojamiento = gestorAlojamiento;
        cargarDesdeArchivo();
    }


    public Resenia crearReseniaAlojamiento(Viajero viajero, Alojamiento alojamiento, int puntaje, String comentario) {
        Resenia r = new Resenia(puntaje, comentario, viajero, LocalDateTime.now(), alojamiento, null);
        agregar(r);
        guardarEnArchivo();
        System.out.println("Reseña creada para alojamiento: " + alojamiento.getDireccion());
        return r;
    }

    public Resenia crearReseniaAnfitrion(Viajero viajero, Anfitrion anfitrion, int puntaje, String comentario) {
        Resenia r = new Resenia(puntaje, comentario, viajero, LocalDateTime.now(), null, anfitrion);
        agregar(r);
        guardarEnArchivo();
        System.out.println("Reseña creada para anfitrión: " + anfitrion.getNombre());
        return r;
    }

    public List<Resenia> listarPorAlojamiento(Alojamiento a) {
        List<Resenia> todas = listar();
        java.util.ArrayList<Resenia> filtradas = new java.util.ArrayList<>();

        for (int i = 0; i < todas.size(); i++) {
            Resenia r = todas.get(i);
            if (r.getAlojamiento() != null && r.getAlojamiento().getId() == a.getId()) {
                filtradas.add(r);
            }
        }
        return filtradas;
    }

    public List<Resenia> listarPorUsuario(Usuario u) {
        List<Resenia> todas = listar();
        java.util.ArrayList<Resenia> filtradas = new java.util.ArrayList<>();

        for (int i = 0; i < todas.size(); i++) {
            Resenia r = todas.get(i);
            if (r.getAutor() != null && r.getAutor().getEmail().equals(u.getEmail())) {
                filtradas.add(r);
            }
        }
        return filtradas;
    }

    public void eliminarResenia(Usuario solicitante, Resenia resenia) {
        if (!(solicitante instanceof Administrador)) {
            throw new SecurityException("Solo los administradores pueden eliminar reseñas.");
        }

        boolean eliminado = eliminar(resenia.getId());
        if (eliminado) {
            guardarEnArchivo();
            System.out.println("Reseña #" + resenia.getId() + " eliminada correctamente.");
        } else {
            System.out.println("No se encontró la reseña especificada.");
        }
    }


    @Override
    public void guardarEnArchivo() {
        JSONArray array = new JSONArray();
        try {
            for (Resenia r : elementos) {
                JSONObject json = new JSONObject();
                json.put("id", r.getId());
                json.put("puntaje", r.getPuntaje());
                json.put("comentario", r.getComentario());
                json.put("autor", r.getAutor() != null ? r.getAutor().getEmail() : JSONObject.NULL);
                json.put("fecha", r.getFecha().toString());
                if (r.getAlojamiento() != null) json.put("alojamiento", r.getAlojamiento().getId());
                if (r.getAnfitrion() != null) json.put("anfitrion", r.getAnfitrion().getEmail());
                array.put(json);
            }
            JsonUtil.grabar(array, ARCHIVO);
        } catch (JSONException e) {
            System.err.println("Error al guardar reseñas: " + e.getMessage());
        }
    }

    @Override
    public void cargarDesdeArchivo() {
        JSONArray array = JsonUtil.leer(ARCHIVO);
        if (array == null) return;

        int maxId = 0;

        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                int id = obj.getInt("id");
                int puntaje = obj.getInt("puntaje");
                String comentario = obj.getString("comentario");
                String autorEmail = obj.getString("autor");
                LocalDateTime fecha = LocalDateTime.parse(obj.getString("fecha"));

                Viajero autor = null;
                for (Usuario u : gestorUsuario.listar()) {
                    if (u instanceof Viajero && u.getEmail().equals(autorEmail)) {
                        autor = (Viajero) u;
                        break;
                    }
                }

                Alojamiento alojamiento = null;
                if (obj.has("alojamiento")) {
                    int idAlojamiento = obj.getInt("alojamiento");
                    for (Alojamiento a : gestorAlojamiento.listar()) {
                        if (a.getId() == idAlojamiento) {
                            alojamiento = a;
                            break;
                        }
                    }
                }

                Anfitrion anfitrion = null;
                if (obj.has("anfitrion")) {
                    String emailAnfitrion = obj.getString("anfitrion");
                    for (Usuario u : gestorUsuario.listar()) {
                        if (u instanceof Anfitrion && u.getEmail().equals(emailAnfitrion)) {
                            anfitrion = (Anfitrion) u;
                            break;
                        }
                    }
                }

                Resenia r = new Resenia(id, puntaje, comentario, autor, fecha, alojamiento, anfitrion);
                agregar(r);

                if (id > maxId) maxId = id;
            }

            Resenia.actualizarContador(maxId);

        } catch (JSONException e) {
            System.err.println("Error al cargar reseñas: " + e.getMessage());
        }
    }
}