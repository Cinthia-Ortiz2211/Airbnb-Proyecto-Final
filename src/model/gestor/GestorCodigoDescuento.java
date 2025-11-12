package model.gestor;

import contract.Persistible;
import model.descuento.CodigoDescuento;
import model.descuento.TipoCodigoDescuento;
import model.usuario.Administrador;
import util.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.List;

public class GestorCodigoDescuento extends Gestor<CodigoDescuento> implements Persistible {

    private static final String ARCHIVO = "codigos_descuento.json";


    public GestorCodigoDescuento() {
        super();
        cargarDesdeArchivo();
    }


    public void crearCodigo(Administrador admin, String codigo, TipoCodigoDescuento tipo, double monto, LocalDateTime expira) {
        if (admin == null) {
            throw new SecurityException("Solo un administrador puede crear códigos de descuento.");
        }

        if (buscarCodigo(codigo) != null) {
            System.out.println("El código '" + codigo + "' ya existe.");
            return;
        }

        CodigoDescuento nuevo = new CodigoDescuento(codigo, tipo, monto, expira);
        agregar(nuevo);
        guardarEnArchivo();
        System.out.println("Código de descuento creado correctamente: " + codigo);
    }

    public CodigoDescuento buscarCodigo(String codigo) {
        for (int i = 0; i < elementos.size(); i++) {
            CodigoDescuento c = elementos.get(i);
            if (c.getCodigo().equalsIgnoreCase(codigo)) {
                return c;
            }
        }
        return null;
    }

    public double aplicarCodigo(double total, String codigo) {
        CodigoDescuento c = buscarCodigo(codigo);
        if (c == null) {
            System.out.println("El código ingresado no existe.");
            return total;
        }
        if (!c.esValido(LocalDateTime.now())) {
            System.out.println("El código ha expirado.");
            return total;
        }
        return c.aplicarDescuento(total);
    }

    public List<CodigoDescuento> listarCodigos() {
        return listar();
    }


    @Override
    public void guardarEnArchivo() {
        JSONArray array = new JSONArray();
        try {
            for (CodigoDescuento c : elementos) {
                JSONObject json = new JSONObject();
                json.put("id", c.getId());
                json.put("codigo", c.getCodigo());
                json.put("tipo", c.getTipo().toString());
                json.put("monto", c.getMonto());
                json.put("fechaExpiracion", c.getFechaExpiracion().toString());
                array.put(json);
            }
            JsonUtil.grabar(array, ARCHIVO);
        } catch (JSONException e) {
            System.err.println("Error al guardar códigos: " + e.getMessage());
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
                String codigo = obj.getString("codigo");
                TipoCodigoDescuento tipo = TipoCodigoDescuento.valueOf(obj.getString("tipo"));
                double monto = obj.getDouble("monto");
                LocalDateTime fechaExpiracion = LocalDateTime.parse(obj.getString("fechaExpiracion"));

                CodigoDescuento c = new CodigoDescuento(id, codigo, tipo, monto, fechaExpiracion);
                agregar(c);
                if (id > maxId) maxId = id;
            }

            CodigoDescuento.actualizarContador(maxId);

        } catch (JSONException e) {
            System.err.println("Error al cargar códigos: " + e.getMessage());
        }
    }
}