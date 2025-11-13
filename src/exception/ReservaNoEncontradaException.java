package exception;

/**
 * Se lanza cuando no se encuentra una reserva
 * con el ID o índice proporcionado.
 */
public class ReservaNoEncontradaException extends Exception {

    public ReservaNoEncontradaException() {
        super("La reserva indicada no existe.");
    }

    public ReservaNoEncontradaException(int id) {
        super("No se encontró una reserva con el ID: " + id);
    }

    public ReservaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
