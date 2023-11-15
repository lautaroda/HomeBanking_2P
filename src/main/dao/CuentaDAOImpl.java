package main.dao;

import main.modelo.Conexion;
import main.modelo.Cuenta;
import main.dao.interfaces.CuentaDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CuentaDAOImpl implements CuentaDAO {

    public List<Cuenta> getCuentasPorCliente(int id_usuario) {
        List<Cuenta> cuentas = new ArrayList<>();

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM cuentas WHERE id_usuario = ?")) {

            ps.setInt(1, id_usuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                cuentas.add(new Cuenta(
                        rs.getInt("id"),
                        rs.getString("tipo"),
                        rs.getDouble("saldo"),
                        rs.getInt("id_usuario")
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return cuentas;
    }
    @Override
    public Cuenta get(int id) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM cuentas WHERE id = ?")) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Cuenta(
                        rs.getInt("id"),
                        rs.getString("tipo"),
                        rs.getDouble("saldo"),
                        rs.getInt("id_usuario")
                );
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Cuenta> getAll() {
        List<Cuenta> cuentas = new ArrayList<>();

        try (Connection conn = Conexion.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, tipo, saldo, id_usuario FROM cuentas")) {

            while (rs.next()) {
                cuentas.add(new Cuenta(
                        rs.getInt("id"),
                        rs.getString("tipo"),
                        rs.getDouble("saldo"),
                        rs.getInt("id_usuario")
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return cuentas;
    }


    @Override
    public void save(Cuenta cuenta) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO cuentas (tipo, saldo, id_usuario) VALUES (?, ?, ?)")) {

            ps.setString(1, cuenta.getTipo());
            ps.setDouble(2, cuenta.getSaldo());
            ps.setInt(3, cuenta.getId_usuario());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Cuenta cuenta) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement("UPDATE cuentas SET tipo = ?, saldo = ?, id_usuario = ? WHERE id = ?")) {

            ps.setString(1, cuenta.getTipo());
            ps.setDouble(2, cuenta.getSaldo());
            ps.setInt(3, cuenta.getId_usuario());
            ps.setInt(4, cuenta.getId());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Cuenta cuenta) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM cuentas WHERE id = ?")) {

            ps.setInt(1, cuenta.getId());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
