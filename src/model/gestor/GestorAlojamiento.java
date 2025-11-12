package model.gestor;

import contract.Persistible;
import model.usuario.*;
import model.alojamiento.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.JsonUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorAlojamiento extends Gestor<Alojamiento> implements Persistible {

    private static final String ARCHIVO = "alojamientos.json";
    private GestorUsuario gestorUsuario;

    public GestorAlojamiento() {
        super();
        this.gestorUsuario = null;
        cargarDesdeArchivo();
    }

    public GestorAlojamiento(GestorUsuario gestorUsuario) {
        super();
        this.gestorUsuario = gestorUsuario;
        cargarDesdeArchivo();
    }


    public void agregarAlojamiento(Anfitrion anfitrion, Alojamiento alojamiento) {
        alojamiento.setAnfitrion(anfitrion);
        agregar(alojamiento);
        guardarEnArchivo();
    }

    public List<Alojamiento> listarAlojamientos() {
        return listar();
    }


    @Override
    public void guardarEnArchivo() {
        JSONArray array = new JSONArray();
        try {
            for (int i = 0; i < elementos.size(); i++) {
                Alojamiento a = elementos.get(i);
                JSONObject json = new JSONObject();

                json.put("id", a.getId());
                json.put("direccion", a.getDireccion());
                json.put("tipo", a.getTipo().toString());
                json.put("nivel", a.getNivel());
                json.put("descripcion", a.getDescripcion());
                json.put("precioPorNoche", a.getPrecioPorNoche());

                // Fechas disponibles
                JSONArray fechas = new JSONArray();
                List<LocalDate> disponibles = a.getFechasDisponibles();
                if (disponibles != null) {
                    for (int j = 0; j < disponibles.size(); j++) {
                        fechas.put(disponibles.get(j).toString());
                    }
                }
                json.put("fechasDisponibles", fechas);

                if (a.getAnfitrion() != null) {
                    json.put("anfitrion", a.getAnfitrion().getEmail());
                } else {
                    json.put("anfitrion", JSONObject.NULL);
                }

                array.put(json);
            }
            JsonUtil.grabar(array, ARCHIVO);
        } catch (JSONException e) {
            System.err.println("Error al guardar alojamientos: " + e.getMessage());
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

                int id = obj.optInt("id", 0);
                String direccion = obj.optString("direccion");
                String tipoStr = obj.optString("tipo");
                String nivel = obj.optString("nivel");
                String descripcion = obj.optString("descripcion");
                double precio = obj.optDouble("precioPorNoche", 0.0);

                // Fechas disponibles
                List<LocalDate> fechasDisponibles = new ArrayList<>();
                JSONArray fechasJson = obj.optJSONArray("fechasDisponibles");
                if (fechasJson != null) {
                    for (int j = 0; j < fechasJson.length(); j++) {
                        fechasDisponibles.add(LocalDate.parse(fechasJson.getString(j)));
                    }
                }

                String emailAnfitrion = obj.optString("anfitrion");
                Anfitrion anfitrion = buscarAnfitrion(emailAnfitrion);

                TipoAlojamiento tipo;
                try {
                    tipo = TipoAlojamiento.valueOf(tipoStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    tipo = TipoAlojamiento.CASA;
                }

                Alojamiento alojamiento = new Alojamiento(
                        id,
                        direccion,
                        tipo,
                        nivel,
                        descripcion,
                        precio,
                        fechasDisponibles,
                        anfitrion
                );

                agregar(alojamiento);

                if (id > maxId) maxId = id;
            }

            Alojamiento.actualizarContador(maxId);

        } catch (JSONException e) {
            System.err.println("Error al cargar alojamientos: " + e.getMessage());
        }
    }

    private Anfitrion buscarAnfitrion(String email) {
        if (gestorUsuario != null) {
            List<Usuario> usuarios = gestorUsuario.listar();
            for (int i = 0; i < usuarios.size(); i++) {
                Usuario u = usuarios.get(i);
                if (u instanceof Anfitrion) {
                    Anfitrion posible = (Anfitrion) u;
                    if (posible.getEmail().equals(email)) {
                        return posible;
                    }
                }
            }
        }
        return new Anfitrion("Anfitrión temporal", email, "default");
    }
}