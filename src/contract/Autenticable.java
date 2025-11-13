package contract;

/**
 * Interfaz que define los métodos para la autenticación de usuarios.
 */
public interface Autenticable {

    /**
     * Inicia sesión con el correo electrónico y la contraseña proporcionados.
     *
     * @param email El correo electrónico del usuario.
     * @param contrasena La contraseña del usuario.
     * @return true si el inicio de sesión es exitoso, false en caso contrario.
     */
    boolean iniciarSesion(String email, String contrasena);

    /**
     * Cierra la sesión del usuario actual.
     */
    void cerrarSesion();
}
