package model.gestor;

import contract.Persistible;
import model.pago.Pago;
import model.pago.EstadoPago;
import model.reserva.Reserva;
import util.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class GestorPago extends Gestor<Pago> implements Persistible {

    private static final String ARCHIVO = "db/pagos.json";
    private GestorReserva gestorReserva;


    public GestorPago() {
        super();
        this.gestorReserva = null;
        cargarDesdeArchivo();
    }

    public GestorPago(GestorReserva gestorReserva) {
        super();
        this.gestorReserva = gestorReserva;
        cargarDesdeArchivo();
    }


    public Pago procesarPago(Reserva reserva) {
        Pago pago = new Pago(Pago.getSiguienteId(), reserva, reserva.getCostoTotal());
        pago.procesar();
        agregar(pago);
        guardarEnArchivo();
        return pago;
    }

    public List<Pago> listarPagos() {
        return listar();
    }


    private Reserva buscarReserva(int id) {
        if (gestorReserva != null) {
            List<Reserva> reservas = gestorReserva.listar();
            for (int i = 0; i < reservas.size(); i++) {
                Reserva r = reservas.get(i);
                if (r.getId() == id) {
                    return r;
                }
            }
        }
        return null;
    }

    @Override
    public void guardarEnArchivo() {
        JSONArray array = new JSONArray();

        try {
            for (Pago p : elementos) {
                JSONObject json = new JSONObject();
                json.put("id", p.getId());
                json.put("reserva", p.getReserva() != null ? p.getReserva().getId() : -1);
                json.put("monto", p.getMonto());
                json.put("estado", p.getEstado().name());
                array.put(json);
            }

            JsonUtil.grabar(array, ARCHIVO);

        } catch (JSONException e) {
            System.err.println("Error al guardar pagos: " + e.getMessage());
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
                int idReserva = o.getInt("reserva");
                double monto = o.getDouble("monto");
                EstadoPago estado = EstadoPago.valueOf(o.getString("estado"));

                Reserva reserva = null;
                for (Reserva r : gestorReserva.listar()) {
                    if (r.getId() == idReserva) {
                        reserva = r;
                        break;
                    }
                }

                Pago pago = new Pago(id, reserva, monto);
                pago.setEstado(estado);

                agregar(pago);
                if (id > maxId) maxId = id;
            }

            Pago.actualizarContador(maxId);

        } catch (JSONException e) {
            System.err.println("Error al cargar pagos: " + e.getMessage());
        }
    }

    public void aprobar(Pago p) { p.setEstado(EstadoPago.APROBADO); }
    public void rechazar(Pago p) { p.setEstado(EstadoPago.RECHAZADO); }
}