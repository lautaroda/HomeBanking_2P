package main.modelo;

public class Cuenta {
    private int id;
    private String tipo;
    private double saldo;
    private int id_usuario;
    private int idTitular;

    public Cuenta(int id, String nombre, double saldo, int id_usuario) {
        this.id = id;
        this.tipo = nombre;
        this.saldo = saldo;
        this.id_usuario = id_usuario;
    }
    public int getIdTitular() {
        return this.idTitular;
    }

    // Método para obtener el número de cuenta
    public String getNumeroCuenta() {
        return getTipo(); // Asumiendo que 'nombre' es el número de cuenta
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String nombre) {
        this.tipo = nombre;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
    @Override
    public String toString() {
        return tipo + " - Saldo: " + saldo;
    }

}
