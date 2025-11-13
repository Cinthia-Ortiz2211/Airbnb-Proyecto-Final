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


    @Override
    public void guardarEnArchivo() {
        JSONArray array = new JSONArray();

        try {
            for (int i = 0; i < elementos.size(); i++) {
                Pago p = elementos.get(i);
                JSONObject json = new JSONObject();

                json.put("id", p.getId());
                json.put("reserva", (p.getReserva() != null) ? p.getReserva().getId() : -1);
                json.put("monto", p.getMonto());
                json.put("estado", p.getEstado().toString());

                array.put(json);
            }
            JsonUtil.grabar(array, ARCHIVO);

        } catch (JSONException e) {
            System.err.println("Error al guardar pagos: " + e.getMessage());
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
                int idReserva = obj.getInt("reserva");
                double monto = obj.getDouble("monto");
                String estadoStr = obj.getString("estado");

                Reserva reserva = buscarReserva(idReserva);

                Pago pago = new Pago(id, reserva, monto);
                pago.setEstado(EstadoPago.valueOf(estadoStr));
                agregar(pago);

                if (id > maxId) maxId = id;
            }

            Pago.actualizarContador(maxId);

        } catch (JSONException e) {
            System.err.println("Error al cargar pagos: " + e.getMessage());
        }
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
}