package contract;

/**
 * Interfaz que define el comportamiento básico de persistencia de datos.
 * Cualquier clase que implemente esta interfaz debe poder guardar y
 * cargar su información desde una fuente externa.
 */
public interface Persistible {

    /**
     * Guarda el estado actual de los datos en una fuente persistente.
     * Ejemplo: escribir un archivo JSON con los elementos actuales.
     */
    void guardarEnArchivo();

    /**
     * Carga los datos desde una fuente persistente.
     * Ejemplo: leer desde un archivo JSON y reconstruir la lista de objetos.
     */
    void cargarDesdeArchivo();
}
