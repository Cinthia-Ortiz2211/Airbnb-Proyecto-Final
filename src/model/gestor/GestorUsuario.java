package model.gestor;

import java.time.LocalDateTime;
import java.util.List;

import exception.CredencialesInvalidasException;
import exception.UsuarioNoEncontradoException;
import model.usuario.*;
import contract.Persistible;
import org.json.JSONException;
import util.JsonUtil;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Gestor especializado en la administración de usuarios.
 * Implementa Persistible para guardar y cargar desde JSON.
 */
public class GestorUsuario extends Gestor<Usuario> implements Persistible {

    private static final String ARCHIVO = "db/usuarios.json";

    public GestorUsuario() {
        super();
        cargarDesdeArchivo();
    }

    /**
     * Registra un nuevo usuario y lo guarda en el archivo JSON.
     */
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

    /**
     * Inicia sesión para un usuario existente.
     */
    public Usuario iniciarSesion(String email, String contrasena)
            throws UsuarioNoEncontradoException, CredencialesInvalidasException {
        for (Usuario u : elementos) {
            if (u.getEmail().equals(email)) {
                if (u.iniciarSesion(email, contrasena)) {
                    return u;
                } else {
                    throw new CredencialesInvalidasException("La contraseña ingresada es incorrecta.");
                }
            }
        }
        throw new UsuarioNoEncontradoException("No existe un usuario con el email: " + email);
    }

    /**
     * Cierra sesión del usuario.
     */
    public void cerrarSesion(Usuario usuario) {
        usuario.cerrarSesion();
    }

    /**
     * Solo un administrador puede listar los usuarios.
     */
    public List<Usuario> listarUsuarios(Usuario solicitante) {
        if (!(solicitante instanceof Administrador)) {
            throw new SecurityException("Solo un administrador puede listar los usuarios.");
        }
        return listar();
    }

    /**
     * Guarda los usuarios en formato JSON.
     */
    @Override
    public void guardarEnArchivo() {
        JSONArray array = new JSONArray();
        try {
            for (Usuario u : elementos) {
                JSONObject json = new JSONObject();
                json.put("id", u.getId());
                json.put("nombre", u.getNombre());
                json.put("email", u.getEmail());
                json.put("contrasena", u.getContrasena());
                json.put("telefono", u.getTelefono());
                json.put("tipo", u.getTipoUsuario().name());
                json.put("fechaRegistro", u.getfechaRegistro().toString());
                array.put(json);
            }
            JsonUtil.grabar(array, ARCHIVO);
        } catch (JSONException e) {
            System.err.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    /**
     * Carga los usuarios desde el archivo JSON.
     */
    @Override
    public void cargarDesdeArchivo() {
        JSONArray array = JsonUtil.leer(ARCHIVO);
        if (array == null) return;

        int maxId = 0;

        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = array.getJSONObject(i);

                int id = o.getInt("id");
                String nombre = o.getString("nombre");
                String email = o.getString("email");
                String contrasena = o.getString("contrasena");
                String telefono = o.optString("telefono", null);
                LocalDateTime fecha = LocalDateTime.parse(o.getString("fechaRegistro"));
                TipoUsuario tipo = TipoUsuario.valueOf(o.getString("tipo"));

                Usuario u = switch (tipo) {
                    case ADMINISTRADOR -> new Administrador(id, nombre, email, contrasena, telefono, fecha);
                    case ANFITRION -> new Anfitrion(id, nombre, email, contrasena, telefono, fecha);
                    case VIAJERO -> new Viajero(id, nombre, email, contrasena, telefono, fecha);
                };

                agregar(u);
                if (id > maxId) maxId = id;
            }

            Usuario.actualizarContador(maxId);

        } catch (JSONException e) {
            System.err.println("Error al cargar usuarios: " + e.getMessage());
        }
    }
}
