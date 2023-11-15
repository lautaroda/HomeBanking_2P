package main.modelo;

public class Administrador extends Usuario {
    //General constructor.
    public Administrador(int id, String nombre, String password, String role) {
        super(id, nombre, password, role);
    }
    //Constructor for "add new user in administradorPanel"
    public Administrador(int id) {
        super(id, "nombrePredeterminado", "passwordPredeterminado", "administrador");
    }
}
