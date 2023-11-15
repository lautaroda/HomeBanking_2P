package main.dao;

import main.modelo.Conexion;
import main.modelo.Tarjeta;
import main.dao.interfaces.TarjetaDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarjetaDAOImpl implements TarjetaDAO {
    public List<Tarjeta> getTarjetasPorCliente(int id_usuario) {
        List<Tarjeta> tarjetas = new ArrayList<>();

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM tarjetas WHERE id_usuario = ?")) {

            ps.setInt(1, id_usuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tarjetas.add(new Tarjeta(
                        rs.getInt("id"),
                        rs.getString("tipo"),
                        rs.getDouble("limite"),
                        rs.getDouble("saldo_a_pagar"),
                        rs.getInt("id_usuario")
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tarjetas;
    }
    @Override
    public Tarjeta get(int id) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM tarjetas WHERE id = ?")) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Tarjeta(
                        rs.getInt("id"),
                        rs.getString("tipo"),
                        rs.getDouble("limite"),
                        rs.getDouble("saldo_a_pagar"),
                        rs.getInt("id_usuario")
                );
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Tarjeta> getAll() {
        List<Tarjeta> tarjetas = new ArrayList<>();

        try (Connection conn = Conexion.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tarjetas")) {

            while (rs.next()) {
                tarjetas.add(new Tarjeta(
                        rs.getInt("id"),
                        rs.getString("tipo"),
                        rs.getDouble("limite"),
                        rs.getDouble("saldo_a_pagar"),
                        rs.getInt("id_usuario")
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tarjetas;
    }

    @Override
    public void save(Tarjeta tarjeta) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO tarjetas (tipo, limite, saldo_a_pagar, id_usuario) VALUES (?, ?, ?, ?)")) {

            ps.setString(1, tarjeta.getTipo());
            ps.setDouble(2, tarjeta.getLimite());
            ps.setDouble(3, tarjeta.getSaldo_a_pagar());
            ps.setInt(4, tarjeta.getId_usuario());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Tarjeta tarjeta) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement("UPDATE tarjetas SET tipo = ?, limite = ?, saldo_a_pagar = ?, id_usuario = ? WHERE id = ?")) {

            ps.setString(1, tarjeta.getTipo());
            ps.setDouble(2, tarjeta.getLimite());
            ps.setDouble(3, tarjeta.getSaldo_a_pagar());
            ps.setInt(4, tarjeta.getId_usuario());
            ps.setInt(5, tarjeta.getId());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Tarjeta tarjeta) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM tarjetas WHERE id = ?")) {

            ps.setInt(1, tarjeta.getId());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
