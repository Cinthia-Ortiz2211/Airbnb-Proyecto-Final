package exception;

/**
 * Excepción personalizada que se lanza cuando una reserva es inválida.
 */
public class ReservaInvalidaException extends RuntimeException {
    /**
     * Constructor de la excepción con un mensaje personalizado.
     *
     * @param message Mensaje que describe el error.
     */
    public ReservaInvalidaException(String message) {
        super(message);
    }
}
