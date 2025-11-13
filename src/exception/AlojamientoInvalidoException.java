package exception;

/**
 * Se lanza cuando el alojamiento seleccionado no existe
 * o el número ingresado está fuera del rango válido.
 */
public class AlojamientoInvalidoException extends Exception {

    public AlojamientoInvalidoException() {
        super("El alojamiento seleccionado no es válido.");
    }

    public AlojamientoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
