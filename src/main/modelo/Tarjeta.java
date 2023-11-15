package main.modelo;

public class Tarjeta {
    private int id;
    private String tipo;
    private double limite;
    private double saldo_a_pagar;
    private int id_usuario;

    public Tarjeta(int id, String tipo, double limite, double saldo_a_pagar, int id_usuario) {
        this.id = id;
        this.tipo = tipo;
        this.limite = limite;
        this.saldo_a_pagar = saldo_a_pagar;
        this.id_usuario = id_usuario;
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

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public double getSaldo_a_pagar() {
        return saldo_a_pagar;
    }

    public void setSaldo_a_pagar(double saldo_a_pagar) {
        this.saldo_a_pagar = saldo_a_pagar;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    // constructor, getters, setters
}
