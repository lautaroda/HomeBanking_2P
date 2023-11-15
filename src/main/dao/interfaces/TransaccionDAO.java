package main.dao.interfaces;

import main.modelo.Transaccion;

import java.util.List;

public interface TransaccionDAO {
    List<Transaccion> getTransaccionesPorUsuario(int idUsuario);

    Transaccion get(int id);
    List<Transaccion> getAll();
    void save(Transaccion transaccion);
    void update(Transaccion transaccion);
    void delete(Transaccion transaccion);
}
