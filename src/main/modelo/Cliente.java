package main.modelo;

import java.util.List;

public class Cliente extends Usuario {
    private List<Cuenta> cuentas;
    private List<Tarjeta> tarjetas;



    // Constructor con ID, nombre, contraseña, rol, cuentas y tarjetas
    public Cliente(int id, String nombre, String password, String role, List<Cuenta> cuentas, List<Tarjeta> tarjetas) {
        super(id, nombre, password, role);
        this.cuentas = cuentas;
        this.tarjetas = tarjetas;
    }
    //Constructor for "add new user in administradorPanel"
    // ID, nombre predeterminado, contraseña predeterminada, y rol "cliente"
    public Cliente(int id, List<Cuenta> cuentas, List<Tarjeta> tarjetas) {
        super(id, "nombrePredeterminado", "passwordPredeterminado", "cliente");
        this.cuentas = cuentas;
        this.tarjetas = tarjetas;
    }
    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public List<Tarjeta> getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(List<Tarjeta> tarjetas) {
        this.tarjetas = tarjetas;
    }
}
