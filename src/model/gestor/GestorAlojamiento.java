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

    private static final String ARCHIVO = "db/alojamientos.json";
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

    @Override
    public void guardarEnArchivo() {
        JSONArray array = new JSONArray();

        try {
            for (Alojamiento a : elementos) {
                JSONObject json = new JSONObject();
                json.put("id", a.getId());
                json.put("direccion", a.getDireccion());
                json.put("tipo", a.getTipo().name());
                json.put("nivel", a.getNivel());
                json.put("descripcion", a.getDescripcion());
                json.put("precioPorNoche", a.getPrecioPorNoche());

                JSONArray fechas = new JSONArray();
                for (LocalDate f : a.getFechasDisponibles()) fechas.put(f.toString());
                json.put("fechasDisponibles", fechas);

                json.put("anfitrion", a.getAnfitrion() != null ? a.getAnfitrion().getEmail() : JSONObject.NULL);

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

                int id = obj.getInt("id");
                String direccion = obj.getString("direccion");
                TipoAlojamiento tipo = TipoAlojamiento.valueOf(obj.getString("tipo"));
                String nivel = obj.getString("nivel");
                String descripcion = obj.getString("descripcion");
                double precio = obj.getDouble("precioPorNoche");

                List<LocalDate> fechas = new ArrayList<>();
                JSONArray arr =obj.optJSONArray("fechasDisponibles");
                if (arr != null) {
                    for (int j = 0; j < arr.length(); j++)
                        fechas.add(LocalDate.parse(arr.getString(j)));
                }

                String emailAnfitrion = obj.optString("anfitrion", null);

                Anfitrion anfitrion = null;
                if (emailAnfitrion != null && !emailAnfitrion.equals("null")) {
                    for (Usuario u : gestorUsuario.listar()) {
                        if (u instanceof Anfitrion && u.getEmail().equals(emailAnfitrion)) {
                            anfitrion = (Anfitrion) u;
                            break;
                        }
                    }
                }

                Alojamiento alojamiento = new Alojamiento(id, direccion, tipo, nivel, descripcion, precio, fechas, anfitrion);

                agregar(alojamiento);
                if (id > maxId) maxId = id;
            }

            Alojamiento.actualizarContador(maxId);

        } catch (JSONException e) {
            System.err.println("Error al cargar alojamientos: " + e.getMessage());
        }
    }

    public List<Alojamiento> filtrarPorTipo(TipoAlojamiento tipo) {
        return elementos.stream()
                .filter(a -> a.getTipo() == tipo)
                .toList();
    }

    public List<Alojamiento> filtrarPorPrecio(double max) {
        return elementos.stream()
                .filter(a -> a.getPrecioPorNoche() <= max)
                .toList();
    }

    public List<Alojamiento> filtrarPorDisponibilidad(LocalDate fecha) {
        return elementos.stream()
                .filter(a -> a.getFechasDisponibles().contains(fecha))
                .toList();
    }


}