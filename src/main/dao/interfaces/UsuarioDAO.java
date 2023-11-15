package main.dao.interfaces;

import main.modelo.Usuario;

import java.util.List;

public interface UsuarioDAO {
    Usuario get(int id);
    List<Usuario> getAll();
    void save(Usuario usuario);
    void update(Usuario usuario);
    void delete(Usuario usuario);
    public Usuario getByCredentials(String nombre, String contrasena);

}
