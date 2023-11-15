package main.dao;

import main.dao.interfaces.TransaccionDAO;
import main.modelo.Conexion;
import main.modelo.Transaccion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransaccionDAOImpl implements TransaccionDAO {

    public Transaccion get(int id) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM transacciones WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Transaccion(
                        rs.getInt("id"),
                        rs.getInt("id_emisor"),
                        rs.getInt("id_receptor"),
                        rs.getDouble("monto")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public List<Transaccion> getAll() {
        List<Transaccion> transacciones = new ArrayList<>();

        try (Connection conn = Conexion.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM transacciones")) {

            while (rs.next()) {
                Transaccion transaccion = new Transaccion(
                        rs.getInt("id"),
                        rs.getInt("id_emisor"),
                        rs.getInt("id_receptor"),
                        rs.getDouble("monto")
                );
                transacciones.add(transaccion);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return transacciones;
    }

    public void save(Transaccion transaccion) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO transacciones (id_emisor, id_receptor, monto) VALUES (?, ?, ?)")) {
            ps.setInt(1, transaccion.getId_emisor());
            ps.setInt(2, transaccion.getId_receptor());
            ps.setDouble(3, transaccion.getMonto());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(Transaccion transaccion) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement("UPDATE transacciones SET id_emisor = ?, id_receptor = ?, monto = ? WHERE id = ?")) {
            ps.setInt(1, transaccion.getId_emisor());
            ps.setInt(2, transaccion.getId_receptor());
            ps.setDouble(3, transaccion.getMonto());
            ps.setInt(4, transaccion.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void delete(Transaccion transaccion) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM transacciones WHERE id = ?")) {
            ps.setInt(1, transaccion.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public List<Transaccion> getTransaccionesPorUsuario(int idUsuario) {
        List<Transaccion> transacciones = new ArrayList<>();
        String sql = "SELECT * FROM transacciones WHERE id_emisor = ? OR id_receptor = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                transacciones.add(new Transaccion(
                        rs.getInt("id"),
                        rs.getInt("id_emisor"),
                        rs.getInt("id_receptor"),
                        rs.getDouble("monto")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return transacciones;
    }
}
