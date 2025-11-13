package exception;

/**
 * Se lanza cuando se intenta modificar una reserva
 * que ya no está en estado pendiente.
 */
public class ReservaNoModificableException extends Exception {

    public ReservaNoModificableException() {
        super("La reserva no puede modificarse porque ya fue confirmada o cancelada.");
    }

    public ReservaNoModificableException(String mensaje) {
        super(mensaje);
    }
}
