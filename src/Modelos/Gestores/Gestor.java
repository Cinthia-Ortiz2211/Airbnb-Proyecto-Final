package Modelos.Gestores;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Gestor<T> {
    private List<T> items;

    public Gestor()
    {
        items = new ArrayList<>();
    }

    public void agregar(T t)
    {
        items.add(t);
    }

    public boolean eliminar(T t)
    {
        return items.remove(t);
    }

    public List<T> listar()
    {
        return new ArrayList<>(items);
    }

    public int cantidad()
    {
        return items.size();
    }
}
