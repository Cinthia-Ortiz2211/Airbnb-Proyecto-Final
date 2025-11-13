package exception;

/**
 * Excepción personalizada que se lanza cuando un usuario no es encontrado.
 */
public class UsuarioNoEncontradoException extends RuntimeException {

    /**
     * Constructor de la excepción con un mensaje personalizado.
     *
     * @param message Mensaje que describe el error.
     */
    public UsuarioNoEncontradoException(String message) {
        super(message);
    }
}
