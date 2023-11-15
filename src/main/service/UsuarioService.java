package main.service;

import main.dao.interfaces.UsuarioDAO;
import main.modelo.Usuario;
import main.service.interfaces.Service;

import java.util.List;

public class UsuarioService implements Service<Usuario> {
    private UsuarioDAO usuarioDAO;
    public static class Roles {
        public static final String ADMIN = "Admin";
        public static final String CLIENT = "Client";
    }
    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @Override
    public Usuario get(int id) {
        return usuarioDAO.get(id);
    }

    @Override
    public List<Usuario> getAll() {
        return usuarioDAO.getAll();
    }

    @Override
    public void save(Usuario usuario) {
        usuarioDAO.save(usuario);
    }

    @Override
    public void update(Usuario usuario) {
        usuarioDAO.update(usuario);
    }

    @Override
    public void delete(Usuario usuario) {
        usuarioDAO.delete(usuario);
    }

    public Usuario getUsuario(String nombre, String contrasena) {
        return usuarioDAO.getByCredentials(nombre, contrasena);
    }
}

