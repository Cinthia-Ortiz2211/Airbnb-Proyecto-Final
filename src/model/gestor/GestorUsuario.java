package model.gestor;

import java.util.List;
import model.usuario.*;
import contract.Persistible;
import org.json.JSONException;
import util.JsonUtil;
import org.json.JSONArray;
import org.json.JSONObject;


public class GestorUsuario extends Gestor<Usuario> implements Persistible {

    private static final String ARCHIVO = "usuarios.json";

    public GestorUsuario() {
        super();
        cargarDesdeArchivo();
    }


    public Usuario registrar(String nombre, String email, String contrasena, String telefono, TipoUsuario tipo) {
        Usuario usuario = null;

        switch (tipo) {
            case ADMINISTRADOR:
                usuario = new Administrador(nombre, email, contrasena);
                break;
            case ANFITRION:
                usuario = new Anfitrion(nombre, email, contrasena);
                break;
            case VIAJERO:
                usuario = new Viajero(nombre, email, contrasena);
                break;
        }

        if (usuario != null) {
            usuario.actualizarPerfil(nombre, email, contrasena, telefono);
            agregar(usuario);
            guardarEnArchivo();
        }

        return usuario;
    }


    public Usuario iniciarSesion(String email, String contrasena) {
        for (Usuario u : elementos) {
            if (u.getEmail().equals(email)) {
                if (u.iniciarSesion(email, contrasena)) {
                    return u;
                } else {
                    throw new RuntimeException("Contraseña incorrecta.");
                }
            }
        }
        throw new RuntimeException("Usuario no encontrado: " + email);
    }


    public void cerrarSesion(Usuario usuario) {
        usuario.cerrarSesion();
    }


    public List<Usuario> listarUsuarios(Usuario solicitante) {
        if (!(solicitante instanceof Administrador)) {
            throw new SecurityException("Solo un administrador puede listar los usuarios.");
        }
        return listar();
    }


    @Override
    public void guardarEnArchivo() {
        JSONArray array = new JSONArray();
        try {
            for (Usuario u : elementos) {
                JSONObject json = new JSONObject();
                json.put("id", u.getId());
                json.put("nombre", u.getNombre());
                json.put("email", u.getEmail());
                json.put("tipo", u.getTipoUsuario().toString());
                array.put(json);
            }
            JsonUtil.grabar(array, ARCHIVO);
        } catch (JSONException e) {
            System.err.println("Error al generar JSON de usuarios: " + e.getMessage());
        }
    }


    @Override
    public void cargarDesdeArchivo() {
        JSONArray array = JsonUtil.leer(ARCHIVO);

        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String nombre = obj.getString("nombre");
                String email = obj.getString("email");
                String contrasena = obj.optString("contrasena", "default");
                TipoUsuario tipo = TipoUsuario.valueOf(obj.getString("tipo"));
                registrar(nombre, email, contrasena, "", tipo);
            }
        } catch (JSONException e) {
            System.err.println("Error al cargar usuarios desde JSON: " + e.getMessage());
        }
    }
}
