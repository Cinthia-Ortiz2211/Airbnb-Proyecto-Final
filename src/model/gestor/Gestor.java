package model.gestor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import contract.IGestor;
import contract.Identificable;

public class Gestor<E extends Identificable> implements IGestor<E> {

    protected List<E> elementos;

    public Gestor() {
        this.elementos = new ArrayList<>();
    }

    @Override
    public void agregar(E elemento) {
        elementos.add(elemento);
    }

    @Override
    public List<E> listar() {
        return new ArrayList<>(elementos);
    }

    @Override
    public E buscarPorId(int id) {
        for (E elemento : elementos) {
            if (elemento.getId() == id) {
                return elemento;
            }
        }
        return null;
    }

    @Override
    public boolean eliminar(int id) {
        boolean eliminado = false;
        Iterator<E> it = elementos.iterator();

        while (it.hasNext()) {
            E e = it.next();
            if (e.getId() == id) {
                it.remove();
                eliminado = true;
            }
        }
        return eliminado;
    }
}
