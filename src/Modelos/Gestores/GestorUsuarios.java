package Modelos.Gestores;

import Modelos.Usuarios.Usuario;
import Excepciones.UsuarioNoEncontradoException;
import java.util.List;

public class GestorUsuarios extends Gestor<Usuario> {

    public Usuario buscarPorId(int id) {
        List<Usuario> usuarios = listar();
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                return u;
            }
        }
        throw new UsuarioNoEncontradoException("No se encontró un usuario con ID: " + id);
    }

    public Usuario buscarPorMail(String mail) {
        List<Usuario> usuarios = listar();
        for (Usuario u : usuarios) {
            if (u.getMail().equalsIgnoreCase(mail)) {
                return u;
            }
        }
        throw new UsuarioNoEncontradoException("No se encontró un usuario con mail: " + mail);
    }

    public void mostrarUsuarios() {
        List<Usuario> usuarios = listar();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados ");
            return;
        }

        for (Usuario u : usuarios) {
            System.out.println(u);
        }
    }
}
