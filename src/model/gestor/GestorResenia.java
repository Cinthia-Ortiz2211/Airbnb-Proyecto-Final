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

    private static final String ARCHIVO = "db/resenias.json";

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
                json.put("fecha", r.getFecha().toString());
                json.put("autor", r.getAutor() != null ? r.getAutor().getEmail() : JSONObject.NULL);
                json.put("alojamiento", r.getAlojamiento() != null ? r.getAlojamiento().getId() : JSONObject.NULL);
                json.put("anfitrion", r.getAnfitrion() != null ? r.getAnfitrion().getEmail() : JSONObject.NULL);

                array.put(json);
            }

            JsonUtil.grabar(array, ARCHIVO);

        } catch (JSONException e) {
            System.err.println("Error al guardar reseñas: " + e.getMessage());
        }
    }

    @Override
    public void cargarDesdeArchivo() {
        JSONArray arr = JsonUtil.leer(ARCHIVO);
        if (arr == null) return;

        int maxId = 0;

        try {
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);

                int id = o.getInt("id");
                int puntaje = o.getInt("puntaje");
                String comentario = o.getString("comentario");
                LocalDateTime fecha = LocalDateTime.parse(o.getString("fecha"));

                String emailAutor = o.optString("autor", null);
                String emailAnfitrion = o.optString("anfitrion", null);
                Integer idAlojamiento = o.optInt("alojamiento");

                Viajero autor = null;
                for (Usuario u : gestorUsuario.listar()) {
                    if (u instanceof Viajero && u.getEmail().equals(emailAutor)) {
                        autor = (Viajero) u;
                        break;
                    }
                }

                Alojamiento alojamiento = null;
                for (Alojamiento a : gestorAlojamiento.listar()) {
                    if (a.getId() == idAlojamiento) {
                        alojamiento = a;
                        break;
                    }
                }

                Anfitrion anfitrion = null;
                for (Usuario u : gestorUsuario.listar()) {
                    if (u instanceof Anfitrion && u.getEmail().equals(emailAnfitrion)) {
                        anfitrion = (Anfitrion) u;
                        break;
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