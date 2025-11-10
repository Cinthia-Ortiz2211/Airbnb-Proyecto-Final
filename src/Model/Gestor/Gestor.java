package model.Gestor;

import java.util.ArrayList;
import java.util.List;

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
