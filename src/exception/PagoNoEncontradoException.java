package exception;

/**
 * Se lanza cuando no se encuentra un pago
 * con el índice o ID especificado.
 */
public class PagoNoEncontradoException extends Exception {

    public PagoNoEncontradoException() {
        super("El pago seleccionado no existe o es inválido.");
    }

    public PagoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
