package main.service;

import main.dao.interfaces.TransaccionDAO;
import main.modelo.Cuenta;
import main.modelo.Transaccion;
import main.service.interfaces.Service;

import java.util.List;

public class TransaccionService implements Service<Transaccion> {
    private TransaccionDAO transaccionDAO;
    private CuentaService cuentaService; // Asumiendo que cuentaService está disponible

    public TransaccionService(TransaccionDAO transaccionDAO, CuentaService cuentaService) {
        this.transaccionDAO = transaccionDAO;
        this.cuentaService = cuentaService;
    }

    public TransaccionService(TransaccionDAO transaccionDAO) {
        this.transaccionDAO = transaccionDAO;
    }

    @Override
    public Transaccion get(int id) {
        return transaccionDAO.get(id);
    }

    @Override
    public List<Transaccion> getAll() {
        return transaccionDAO.getAll();
    }
    public List<Transaccion> getTransaccionesPorCliente(int idCliente) {
        return transaccionDAO.getTransaccionesPorUsuario(idCliente);
    }
    @Override
    public void save(Transaccion transaccion) {
        transaccionDAO.save(transaccion);
    }

    @Override
    public void update(Transaccion transaccion) {
        transaccionDAO.update(transaccion);
    }

    @Override
    public void delete(Transaccion transaccion){
        transaccionDAO.delete(transaccion);
    }
    public boolean realizarTransferencia(int idCuentaEmisor, int idCuentaReceptor, double monto) {
        try {
            // Obtener las cuentas por sus IDs
            Cuenta cuentaEmisor = cuentaService.get(idCuentaEmisor);
            Cuenta cuentaReceptor = cuentaService.get(idCuentaReceptor);

            // Verificar si las cuentas existen y si el saldo es suficiente para la transferencia
            if (cuentaEmisor != null && cuentaReceptor != null && cuentaEmisor.getSaldo() >= monto) {
                // Actualizar saldos
                cuentaEmisor.setSaldo(cuentaEmisor.getSaldo() - monto);
                cuentaReceptor.setSaldo(cuentaReceptor.getSaldo() + monto);

                // Actualizar las cuentas en la base de datos
                cuentaService.update(cuentaEmisor);
                cuentaService.update(cuentaReceptor);

                // Registrar la transacción
                Transaccion transaccion = new Transaccion(0, cuentaEmisor.getId_usuario(), cuentaReceptor.getId_usuario(), monto);
                transaccionDAO.save(transaccion);

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}