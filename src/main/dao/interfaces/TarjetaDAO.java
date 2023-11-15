package main.dao.interfaces;

import main.modelo.Tarjeta;

import java.util.List;

public interface TarjetaDAO {
    Tarjeta get(int id);
    List<Tarjeta> getAll();
    void save(Tarjeta tarjeta);
    void update(Tarjeta tarjeta);
    void delete(Tarjeta tarjeta);
    List<Tarjeta> getTarjetasPorCliente(int id_usuario);

}
