package model.gestor;

import contract.Persistible;
import model.usuario.*;
import model.alojamiento.Alojamiento;
import model.reserva.Reserva;
import model.descuento.CodigoDescuento;
import model.pago.Pago;
import util.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.List;

public class GestorReserva extends Gestor<Reserva> implements Persistible {

    private static final String ARCHIVO = "db/reservas.json";

    private GestorAlojamiento gestorAlojamiento;
    private GestorUsuario gestorUsuario;
    private GestorCodigoDescuento gestorCodigoDescuento;
    private GestorPago gestorPago;


    public GestorReserva(GestorAlojamiento gestorAlojamiento, GestorUsuario gestorUsuario) {
        this(gestorAlojamiento, gestorUsuario, null, null);
    }

    public GestorReserva(GestorAlojamiento gestorAlojamiento, GestorUsuario gestorUsuario,
                         GestorCodigoDescuento gestorCodigoDescuento) {
        this(gestorAlojamiento, gestorUsuario, gestorCodigoDescuento, null);
    }

    public GestorReserva(GestorAlojamiento gestorAlojamiento, GestorUsuario gestorUsuario,
                         GestorCodigoDescuento gestorCodigoDescuento, GestorPago gestorPago) {
        super();
        this.gestorAlojamiento = gestorAlojamiento;
        this.gestorUsuario = gestorUsuario;
        this.gestorCodigoDescuento = gestorCodigoDescuento;
        this.gestorPago = gestorPago;
        cargarDesdeArchivo();
    }


    public Reserva crearReserva(Viajero viajero, Alojamiento alojamiento,
                                LocalDate inicio, LocalDate fin, String codigoDescuento) {
        if (viajero == null || alojamiento == null) {
            throw new IllegalArgumentException("Datos insuficientes para crear la reserva.");
        }

        double costoBase = alojamiento.getPrecioPorNoche() * (fin.toEpochDay() - inicio.toEpochDay());
        double costoFinal = costoBase;

        if (gestorCodigoDescuento != null && codigoDescuento != null && !codigoDescuento.isEmpty()) {
            costoFinal = gestorCodigoDescuento.aplicarCodigo(costoBase, codigoDescuento);
            System.out.println(" Descuento aplicado (" + codigoDescuento + "): $" + (costoBase - costoFinal));
        }

        Reserva reserva = new Reserva(alojamiento, viajero, inicio, fin, costoFinal);
        agregar(reserva);
        guardarEnArchivo();

        System.out.println("Reserva #" + reserva.getId() + " creada correctamente. Total: $" + costoFinal);

        if (gestorPago != null) {
            Pago pago = gestorPago.procesarPago(reserva);
            System.out.println("Pago generado automáticamente para la reserva #" + reserva.getId());
        }

        return reserva;
    }

    public void cancelarReserva(Viajero viajero, Reserva reserva) {
        if (viajero == null || reserva == null) return;
        if (!reserva.getViajero().equals(viajero)) {
            throw new SecurityException("No se puede cancelar una reserva de otro viajero.");
        }
        reserva.cancelar();
        guardarEnArchivo();
        System.out.println("Reserva #" + reserva.getId() + " cancelada correctamente.");
    }

    public List<Reserva> listarReservas() {
        return listar();
    }

    @Override
    public void guardarEnArchivo() {
        JSONArray array = new JSONArray();
        try {
            for (Reserva r : elementos) {
                JSONObject json = new JSONObject();

                json.put("id", r.getId());
                json.put("alojamiento", r.getAlojamiento() != null ? r.getAlojamiento().getId() : -1);
                json.put("viajero", r.getViajero() != null ? r.getViajero().getEmail() : JSONObject.NULL);
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
                int idAlojamiento = obj.getInt("alojamiento");
                String emailViajero = obj.getString("viajero");
                LocalDate inicio = LocalDate.parse(obj.getString("fechaInicio"));
                LocalDate fin = LocalDate.parse(obj.getString("fechaFin"));
                double costo = obj.getDouble("costoTotal");
                boolean pendiente = obj.getBoolean("pendiente");

                Alojamiento alojamiento = null;
                for (Alojamiento a : gestorAlojamiento.listar()) {
                    if (a.getId() == idAlojamiento) {
                        alojamiento = a;
                        break;
                    }
                }

                Viajero viajero = null;
                for (Usuario u : gestorUsuario.listar()) {
                    if (u instanceof Viajero && u.getEmail().equals(emailViajero)) {
                        viajero = (Viajero) u;
                        break;
                    }
                }

                Reserva r = new Reserva(id, alojamiento, viajero, inicio, fin, costo);
                if (!pendiente) r.cancelar();

                agregar(r);
                if (id > maxId) maxId = id;
            }

            Reserva.actualizarContador(maxId);

        } catch (JSONException e) {
            System.err.println("Error al cargar reservas: " + e.getMessage());
        }
    }
}