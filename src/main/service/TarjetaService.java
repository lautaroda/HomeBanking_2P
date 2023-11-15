package main.service;

import main.dao.interfaces.TarjetaDAO;
import main.modelo.Tarjeta;
import main.service.interfaces.Service;

import java.util.List;

public class TarjetaService implements Service<Tarjeta> {
    private TarjetaDAO tarjetaDAO;
    public List<Tarjeta> getTarjetasPorCliente(int id_cliente) {
        return tarjetaDAO.getTarjetasPorCliente(id_cliente);
    }
    public TarjetaService(TarjetaDAO tarjetaDAO) {
        this.tarjetaDAO = tarjetaDAO;
    }

    @Override
    public Tarjeta get(int id) {
        return tarjetaDAO.get(id);
    }

    @Override
    public List<Tarjeta> getAll() {
        return tarjetaDAO.getAll();
    }

    @Override
    public void save(Tarjeta tarjeta) {
        tarjetaDAO.save(tarjeta);
    }

    @Override
    public void update(Tarjeta tarjeta) {
        tarjetaDAO.update(tarjeta);
    }

    @Override
    public void delete(Tarjeta tarjeta) {
        tarjetaDAO.delete(tarjeta);
    }
}