package main.service;
import main.dao.interfaces.CuentaDAO;
import main.modelo.Cuenta;
import main.service.interfaces.Service;

import java.util.List;

public class CuentaService implements Service<Cuenta> {
    private CuentaDAO cuentaDAO;

    public List<Cuenta> getCuentasPorCliente(int id_cliente) {
        return cuentaDAO.getCuentasPorCliente(id_cliente);
    }
    public CuentaService(CuentaDAO cuentaDAO) {
        this.cuentaDAO = cuentaDAO;
    }
    public List<Cuenta> getCuentas() {
        return cuentaDAO.getAll();
    }
    @Override
    public Cuenta get(int id) {
        return cuentaDAO.get(id);
    }

    @Override
    public List<Cuenta> getAll() {
        return cuentaDAO.getAll();
    }

    @Override
    public void save(Cuenta cuenta) {
        cuentaDAO.save(cuenta);
    }

    @Override
    public void update(Cuenta cuenta) {
        cuentaDAO.update(cuenta);
    }

    @Override
    public void delete(Cuenta cuenta) {
        cuentaDAO.delete(cuenta);
    }
}