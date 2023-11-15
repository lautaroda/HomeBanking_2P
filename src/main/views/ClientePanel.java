package main.views;

import main.modelo.*;
import main.service.CuentaService;
import main.service.TarjetaService;
import main.service.TransaccionService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ClientePanel extends JPanel {
    private Cliente cliente;
    private CuentaService cuentaService;
    private TarjetaService tarjetaService;
    private TransaccionService transaccionService;

    private JTabbedPane tabbedPane;
    private JTable tableCuentas;
    private JTable tableTarjetas;
    private JTable tableTransacciones;
    private JButton btnTransferir;
    private JComboBox<Cuenta> cbCuentas;
    private JComboBox<Cuenta> cbCuentasDestino;
    private JTextField txtMontoTransferencia;

    public ClientePanel(Usuario usuario, CuentaService cuentaServ, TarjetaService tarjetaServ, TransaccionService transaccionServ) {
        this.cliente = (Cliente) usuario;
        this.cuentaService = cuentaServ;
        this.tarjetaService = tarjetaServ;
        this.transaccionService = transaccionServ;
        initComponents();
        cargarDatosDelCliente();
    }

    private void initComponents() {
        // Configuración inicial del panel
        this.setLayout(new BorderLayout());
        tabbedPane = new JTabbedPane();

        // Pestaña de cuentas
        JPanel cuentasPanel = new JPanel(new BorderLayout());
        tableCuentas = new JTable(); // Aquí deberías usar un modelo de tabla adecuado
        cuentasPanel.add(new JScrollPane(tableCuentas), BorderLayout.CENTER);

        // Pestaña de tarjetas
        JPanel tarjetasPanel = new JPanel(new BorderLayout());
        tableTarjetas = new JTable(); // Aquí deberías usar un modelo de tabla adecuado
        tarjetasPanel.add(new JScrollPane(tableTarjetas), BorderLayout.CENTER);

        // Pestaña de transferencias
        JPanel transferenciasPanel = new JPanel(new BorderLayout());
        tableTransacciones = new JTable(); // Aquí deberías usar un modelo de tabla adecuado
        transferenciasPanel.add(new JScrollPane(tableTransacciones), BorderLayout.CENTER);


        // Configuración de los modelos de las tablas
        DefaultTableModel cuentasModel = new DefaultTableModel();
        cuentasModel.setColumnIdentifiers(new Object[]{"ID", "Tipo", "Saldo"});
        tableCuentas.setModel(cuentasModel);

        DefaultTableModel tarjetasModel = new DefaultTableModel();
        tarjetasModel.setColumnIdentifiers(new Object[]{"ID", "Tipo", "Límite", "Saldo a Pagar"});
        tableTarjetas.setModel(tarjetasModel);

        DefaultTableModel transaccionesModel = new DefaultTableModel();
        transaccionesModel.setColumnIdentifiers(new Object[]{"ID", "Emisor", "Receptor", "Monto"});
        tableTransacciones.setModel(transaccionesModel);

        // Componentes para realizar transferencias
        cbCuentas = new JComboBox<>();
        cbCuentasDestino = new JComboBox<>();
        txtMontoTransferencia = new JTextField(10);
        btnTransferir = new JButton("Transferir");
        btnTransferir.addActionListener(e -> realizarTransferencia());

        JPanel transferenciaPanel = new JPanel();
        transferenciaPanel.add(new JLabel("Cuenta Origen:"));
        transferenciaPanel.add(cbCuentas);
        transferenciaPanel.add(new JLabel("Cuenta Destino:"));
        transferenciaPanel.add(cbCuentasDestino);
        transferenciaPanel.add(new JLabel("Monto:"));
        transferenciaPanel.add(txtMontoTransferencia);
        transferenciaPanel.add(btnTransferir);

        // Agregar las pestañas al tabbedPane
        tabbedPane.addTab("Cuentas", cuentasPanel);
        tabbedPane.addTab("Tarjetas", tarjetasPanel);
        tabbedPane.addTab("Transferencias", transferenciasPanel);
        tabbedPane.addTab("Realizar Transferencia", transferenciaPanel);

        // Agregar tabbedPane al panel principal
        this.add(tabbedPane, BorderLayout.CENTER);
    }

    private void cargarDatosDelCliente() {
        // Cargar las cuentas del cliente
        List<Cuenta> cuentasCliente = cuentaService.getCuentasPorCliente(cliente.getId());
        // Actualizar el modelo de la tabla de cuentas con estas cuentas
        DefaultTableModel cuentasModel = (DefaultTableModel) tableCuentas.getModel();
        cuentasModel.setRowCount(0); // Limpia la tabla antes de agregar
        for (Cuenta cuenta : cuentasCliente) {
            cuentasModel.addRow(new Object[]{cuenta.getId(), cuenta.getTipo(), cuenta.getSaldo()});
        }

        // Cargar las tarjetas del cliente
        List<Tarjeta> tarjetasCliente = tarjetaService.getTarjetasPorCliente(cliente.getId());
        // Actualizar el modelo de la tabla de tarjetas con estas tarjetas
        DefaultTableModel tarjetasModel = (DefaultTableModel) tableTarjetas.getModel();
        tarjetasModel.setRowCount(0); // Limpia la tabla antes de agregar
        for (Tarjeta tarjeta : tarjetasCliente) {
            tarjetasModel.addRow(new Object[]{tarjeta.getId(), tarjeta.getTipo(), tarjeta.getLimite(), tarjeta.getSaldo_a_pagar()});
        }

        // Cargar las transferencias del cliente
        List<Transaccion> transaccionesCliente = transaccionService.getTransaccionesPorCliente(cliente.getId());
        // Actualizar el modelo de la tabla de transferencias con estas transacciones
        DefaultTableModel transaccionesModel = (DefaultTableModel) tableTransacciones.getModel();
        transaccionesModel.setRowCount(0); // Limpia la tabla antes de agregar
        for (Transaccion transaccion : transaccionesCliente) {
            transaccionesModel.addRow(new Object[]{transaccion.getId(), transaccion.getId_emisor(), transaccion.getId_receptor(), transaccion.getMonto()});
        }

        // Actualizar los JComboBox de transferencia
        cbCuentas.removeAllItems();
        for (Cuenta cuenta : cuentasCliente) {
            cbCuentas.addItem(cuenta);
        }

        // Actualizar cbCuentasDestino con cuentas que no son del cliente
        List<Cuenta> todasLasCuentas = cuentaService.getAll();
        cbCuentasDestino.removeAllItems();
        for (Cuenta cuenta : todasLasCuentas) {
            if (cuenta.getId_usuario() != cliente.getId()) { // Asegurarse de no agregar cuentas del mismo cliente
                cbCuentasDestino.addItem(cuenta);
            }
        }
    }


    private void realizarTransferencia() {
        Cuenta cuentaOrigen = (Cuenta) cbCuentas.getSelectedItem();
        Cuenta cuentaDestino = (Cuenta) cbCuentasDestino.getSelectedItem();

        // Verificar que la cuenta de destino no pertenece al cliente
        if (cuentaDestino.getId_usuario() == cliente.getId()) {
            JOptionPane.showMessageDialog(this, "No puedes transferir a tu propia cuenta.");
            return;
        }

        double monto;
        try {
            monto = Double.parseDouble(txtMontoTransferencia.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un monto válido.");
            return;
        }

        boolean transferenciaExitosa = transaccionService.realizarTransferencia(
                cuentaOrigen.getId(), cuentaDestino.getId(), monto);

        if (transferenciaExitosa) {
            JOptionPane.showMessageDialog(this, "Transferencia realizada con éxito a la cuenta " + cuentaDestino.getNumeroCuenta());
            actualizarInformacionCuentas();
            actualizarTablas();
        } else {
            JOptionPane.showMessageDialog(this, "Error al realizar la transferencia.");
        }
    }

    private void actualizarInformacionCuentas() {
        cbCuentas.removeAllItems();
        cbCuentasDestino.removeAllItems();

        List<Cuenta> cuentasActualizadas = cuentaService.getCuentasPorCliente(cliente.getId());
        cuentasActualizadas.forEach(cbCuentas::addItem);
        cuentaService.getCuentas().forEach(cbCuentasDestino::addItem);
    }
    // Método para actualizar la tabla de cuentas
    private void updateCuentasTableModel() {
        List<Cuenta> cuentasCliente = cuentaService.getCuentasPorCliente(cliente.getId());
        DefaultTableModel cuentasModel = (DefaultTableModel) tableCuentas.getModel();
        cuentasModel.setRowCount(0);
        for (Cuenta cuenta : cuentasCliente) {
            cuentasModel.addRow(new Object[]{cuenta.getId(), cuenta.getTipo(), cuenta.getSaldo()});
        }
    }

    // Método para actualizar la tabla de tarjetas
    private void updateTarjetasTableModel() {
        List<Tarjeta> tarjetasCliente = tarjetaService.getTarjetasPorCliente(cliente.getId());
        DefaultTableModel tarjetasModel = (DefaultTableModel) tableTarjetas.getModel();
        tarjetasModel.setRowCount(0);
        for (Tarjeta tarjeta : tarjetasCliente) {
            tarjetasModel.addRow(new Object[]{tarjeta.getId(), tarjeta.getTipo(), tarjeta.getLimite(), tarjeta.getSaldo_a_pagar()});
        }
    }

    // Método para actualizar la tabla de transacciones
    private void updateTransaccionesTableModel() {
        List<Transaccion> transaccionesCliente = transaccionService.getTransaccionesPorCliente(cliente.getId());
        DefaultTableModel transaccionesModel = (DefaultTableModel) tableTransacciones.getModel();
        transaccionesModel.setRowCount(0);
        for (Transaccion transaccion : transaccionesCliente) {
            transaccionesModel.addRow(new Object[]{transaccion.getId(), transaccion.getId_emisor(), transaccion.getId_receptor(), transaccion.getMonto()});
        }
    }

    // Método para invocar las actualizaciones de todas las tablas
    private void actualizarTablas() {
        updateCuentasTableModel();
        updateTarjetasTableModel();
        updateTransaccionesTableModel();
    }
}
