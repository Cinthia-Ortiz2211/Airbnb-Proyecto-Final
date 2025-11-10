package contract;

import java.util.List;

/**
 * Interfaz genérica que define las operaciones básicas de gestión de entidades del sistema.
 * <p>
 * Permite estandarizar la lógica de negocio de los gestores (usuarios, alojamientos, reservas, etc.)
 * aplicando el principio de genericidad.
 *
 * @param <E> tipo de entidad gestionada (por ejemplo, Usuario, Reserva, Alojamiento)
 */
public interface IGestor<E> {

    /**
     * Agrega un nuevo elemento a la colección gestionada.
     *
     * @param elemento objeto de tipo T a agregar
     */
    void agregar(E elemento);

    /**
     * Devuelve una lista inmutable o copia de los elementos gestionados.
     *
     * @return lista de elementos
     */
    List<E> listar();

    /**
     * Busca un elemento por su identificador único.
     *
     * @param id identificador del elemento
     * @return el elemento encontrado o null si no existe
     */
    E buscarPorId(int id);

    /**
     * Elimina un elemento identificado por su id.
     *
     * @param id identificador del elemento a eliminar
     * @return true si la eliminación fue exitosa, false si el elemento no existía
     */
    boolean eliminar(int id);
}
