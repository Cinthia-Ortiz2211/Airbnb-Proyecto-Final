package contract;

import java.util.List;


public interface IGestor<E> {

    void agregar(E elemento);

    List<E> listar();

    E buscarPorId(int id);

    boolean eliminar(int id);
}
