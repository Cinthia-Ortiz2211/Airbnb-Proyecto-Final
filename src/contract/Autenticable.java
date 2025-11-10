package contract;

public interface Autenticable {
    boolean iniciarSesion(String email, String contrasena);
    void cerrarSesion();
}
