package main.dao.interfaces;

import main.modelo.Cuenta;

import java.util.List;

public interface CuentaDAO {
    Cuenta get(int id);
    List<Cuenta> getAll();
    void save(Cuenta cuenta);
    void update(Cuenta cuenta);
    void delete(Cuenta cuenta);
    List<Cuenta> getCuentasPorCliente(int id_usuario);

}
