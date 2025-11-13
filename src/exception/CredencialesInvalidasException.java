package exception;

/**
 * Se lanza cuando las credenciales proporcionadas
 * (email o contraseña) son inválidas durante el inicio de sesión.
 */
public class CredencialesInvalidasException extends Exception {

    public CredencialesInvalidasException() {
        super("Credenciales inválidas: el email o la contraseña son incorrectos.");
    }

    public CredencialesInvalidasException(String detalle) {
        super("Credenciales inválidas: " + detalle);
    }
}
