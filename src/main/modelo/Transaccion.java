package main.modelo;

public class Transaccion {
    private int id;
    private int id_emisor;
    private int id_receptor;
    private double monto;

    public Transaccion(int id, int id_emisor, int id_receptor, double monto) {
        this.id = id;
        this.id_emisor = id_emisor;
        this.id_receptor = id_receptor;
        this.monto = monto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_emisor() {
        return id_emisor;
    }

    public void setId_emisor(int id_emisor) {
        this.id_emisor = id_emisor;
    }

    public int getId_receptor() {
        return id_receptor;
    }

    public void setId_receptor(int id_receptor) {
        this.id_receptor = id_receptor;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

}