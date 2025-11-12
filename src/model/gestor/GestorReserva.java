package model.gestor;

import contract.Persistible;
import model.reserva.Reserva;
import model.alojamiento.Alojamiento;
import model.usuario.Viajero;
import model.usuario.Usuario;
import util.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.List;

public class GestorReserva extends Gestor<Reserva> implements Persistible {

    private static final String ARCHIVO = "reservas.json";

    private GestorAlojamiento gestorAlojamiento;
    private GestorUsuario gestorUsuario;


    public GestorReserva() {
        super();
        this.gestorAlojamiento = null;
        this.gestorUsuario = null;
        cargarDesdeArchivo();
    }

    public GestorReserva(GestorAlojamiento gestorAlojamiento, GestorUsuario gestorUsuario) {
        super();
        this.gestorAlojamiento = gestorAlojamiento;
        this.gestorUsuario = gestorUsuario;
        cargarDesdeArchivo();
    }


    public Reserva crearReserva(Viajero viajero, Alojamiento alojamiento, LocalDate inicio, LocalDate fin) {
        Reserva reserva = new Reserva(Reserva.getSiguienteId(), alojamiento, viajero, inicio, fin, true);
        agregar(reserva);
        guardarEnArchivo();
        return reserva;
    }

    public void cancelarReserva(Viajero viajero, Reserva reserva) {
        if (reserva.getViajero().getId() != viajero.getId()) {
            System.err.println("No puede cancelar una reserva de otro usuario.");
            return;
        }
        reserva.cancelar();
        guardarEnArchivo();
    }

    public Reserva verDetalleReserva(int id, Usuario solicitante) {
        for (int i = 0; i < elementos.size(); i++) {
            Reserva r = elementos.get(i);
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    public List<Reserva> listarReservas() {
        return listar();
    }


    @Override
    public void guardarEnArchivo() {
        JSONArray array = new JSONArray();

        try {
            for (int i = 0; i < elementos.size(); i++) {
                Reserva r = elementos.get(i);
                JSONObject json = new JSONObject();

                json.put("id", r.getId());
                json.put("alojamiento", r.getAlojamiento().getId());
                json.put("viajero", r.getViajero().getEmail());
                json.put("fechaInicio", r.getFechaInicio().toString());
                json.put("fechaFin", r.getFechaFin().toString());
                json.put("costoTotal", r.getCostoTotal());
                json.put("pendiente", r.isPendiente());

                array.put(json);
            }

            JsonUtil.grabar(array, ARCHIVO);
        } catch (JSONException e) {
            System.err.println("Error al guardar reservas: " + e.getMessage());
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
                String emailViajero = obj.getString("viajero");
                int idAlojamiento = obj.getInt("alojamiento");

                Viajero viajero = buscarViajero(emailViajero);
                Alojamiento alojamiento = buscarAlojamiento(idAlojamiento);

                LocalDate inicio = LocalDate.parse(obj.getString("fechaInicio"));
                LocalDate fin = LocalDate.parse(obj.getString("fechaFin"));
                boolean pendiente = obj.getBoolean("pendiente");

                Reserva reserva = new Reserva(id, alojamiento, viajero, inicio, fin, pendiente);
                agregar(reserva);

                if (id > maxId) maxId = id;
            }

            Reserva.actualizarContador(maxId);

        } catch (JSONException e) {
            System.err.println("Error al cargar reservas: " + e.getMessage());
        }
    }


    private Viajero buscarViajero(String email) {
        if (gestorUsuario != null) {
            List<Usuario> usuarios = gestorUsuario.listar();
            for (int i = 0; i < usuarios.size(); i++) {
                Usuario u = usuarios.get(i);
                if (u instanceof Viajero && u.getEmail().equals(email)) {
                    return (Viajero) u;
                }
            }
        }
        return new Viajero("Viajero desconocido", email, "default");
    }

    private Alojamiento buscarAlojamiento(int id) {
        if (gestorAlojamiento != null) {
            List<Alojamiento> alojamientos = gestorAlojamiento.listar();
            for (int i = 0; i < alojamientos.size(); i++) {
                Alojamiento a = alojamientos.get(i);
                if (a.getId() == id) {
                    return a;
                }
            }
        }
        return new Alojamiento(id, "Dirección desconocida", "CASA", "", "", 0.0, null, null);
    }
}