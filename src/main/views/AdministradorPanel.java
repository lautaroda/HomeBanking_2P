package main.views;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;

import main.dao.UsuarioDAOImpl;
import main.dao.interfaces.UsuarioDAO;
import main.modelo.Cliente;
import main.modelo.Administrador;

import main.modelo.*;
import main.service.CuentaService;
import main.service.TarjetaService;
import main.service.TransaccionService;
import main.service.UsuarioService;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorPanel extends JPanel {
    private JTabbedPane tabbedPane;
    private JTable tableUsuarios;
    private JTable tableCuentas;
    private JTable tableTarjetas;
    private JTable tableTransacciones;

    // Servicios
    private UsuarioService usuarioService;
    private CuentaService cuentaService;
    private TarjetaService tarjetaService;
    private TransaccionService transaccionService;

    public AdministradorPanel(UsuarioService usuarioService, CuentaService cuentaService,
                              TarjetaService tarjetaService, TransaccionService transaccionService) {
        this.usuarioService = usuarioService;
        this.cuentaService = cuentaService;
        this.tarjetaService = tarjetaService;
        this.transaccionService = transaccionService;
        setPreferredSize(new Dimension(800, 600));
        initComponents();
        showPanel();
    }


    private void initComponents() {
        setLayout(new BorderLayout());
        tabbedPane = new JTabbedPane();

        // Inicializar las pestañas
        tabbedPane.addTab("Users", createUsersPanel());
        tabbedPane.addTab("Accounts", createAccountsPanel());
        tabbedPane.addTab("Cards", createCardsPanel());
        tabbedPane.addTab("Transactions", createTransactionsPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    //CREAR PANEL
    private JPanel createUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        tableUsuarios = new JTable();
        panel.add(new JScrollPane(tableUsuarios), BorderLayout.CENTER);
        panel.add(createCrudButtonsPanel(tableUsuarios, "Users"), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createAccountsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        tableCuentas = new JTable();
        panel.add(new JScrollPane(tableCuentas), BorderLayout.CENTER);
        panel.add(createCrudButtonsPanel(tableCuentas, "Accounts"), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createCardsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        tableTarjetas = new JTable();
        panel.add(new JScrollPane(tableTarjetas), BorderLayout.CENTER);
        panel.add(createCrudButtonsPanel(tableTarjetas, "Cards"), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createTransactionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        tableTransacciones = new JTable();
        panel.add(new JScrollPane(tableTransacciones), BorderLayout.CENTER);
        panel.add(createCrudButtonsPanel(tableTransacciones, "Transactions"), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createCrudButtonsPanel(JTable table, String entityType) {
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton readButton = new JButton("Read");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        // Asignar acciones a los botones
        addButton.addActionListener(e -> addEntity(entityType));
        readButton.addActionListener(e -> readEntity(tableUsuarios, "Users"));
        updateButton.addActionListener(e -> updateEntity(table, entityType));
        deleteButton.addActionListener(e -> deleteEntity(table, entityType));

        buttonPanel.add(addButton);
        buttonPanel.add(readButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        return buttonPanel;
    }

    //ADD Entity
    private void addEntity(String entityType) {
        switch (entityType) {
            case "Users":

                // Abrir una ventana de diálogo para ingresar los detalles del nuevo usuario
                JFrame addUserFrame = new JFrame("Agregar Usuario");
                addUserFrame.setSize(400, 200);
                addUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JPanel addUserPanel = new JPanel();
                addUserFrame.add(addUserPanel);

                // Crear y configurar los componentes para ingresar detalles del usuario
                JLabel nameLabel = new JLabel("Nombre de usuario:");
                JTextField nameField = new JTextField(20);

                JLabel passwordLabel = new JLabel("Contraseña:");
                JPasswordField passwordField = new JPasswordField(20);

                JRadioButton clienteRadioButton = new JRadioButton("Cliente");
                JRadioButton adminRadioButton = new JRadioButton("Administrador");

                ButtonGroup userTypeGroup = new ButtonGroup();
                userTypeGroup.add(clienteRadioButton);
                userTypeGroup.add(adminRadioButton);

                JButton addButton = new JButton("Agregar Usuario");

                addButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String nombre = nameField.getText();
                        String contrasena = new String(passwordField.getPassword());

                        // Validar que se hayan ingresado datos
                        if (nombre.isEmpty() || contrasena.isEmpty()) {
                            JOptionPane.showMessageDialog(addUserFrame, "Por favor, ingrese nombre de usuario y contraseña.");
                            return;
                        }

                        // Determinar si es cliente o administrador
                        if (clienteRadioButton.isSelected()) {
                            Cliente nuevoCliente = new Cliente(0, nombre, contrasena, "Client", new ArrayList<>(), new ArrayList<>());
                            System.out.println("Nuevo cliente creado:");
                            System.out.println("Nombre: " + nuevoCliente.getNombre());
                            System.out.println("Contraseña: " + nuevoCliente.getPassword());
                            System.out.println("Rol: " + nuevoCliente.getRole());
                            usuarioService.save(nuevoCliente);
                        } else if (adminRadioButton.isSelected()) {
                            Administrador nuevoAdmin = new Administrador(0, nombre, contrasena, "Admin");
                            System.out.println("Nuevo administrador creado:");
                            System.out.println("Nombre: " + nuevoAdmin.getNombre());
                            System.out.println("Contraseña: " + nuevoAdmin.getPassword());
                            System.out.println("Rol: " + nuevoAdmin.getRole());
                            usuarioService.save(nuevoAdmin);
                        }
                        // Actualizar la tabla de usuarios
                        updateUsersTableModel();
                        // Cerrar la ventana de diálogo y actualizar la tabla de usuarios
                        addUserFrame.dispose();
                    }
                });

                // Agregar componentes al panel
                addUserPanel.add(nameLabel);
                addUserPanel.add(nameField);
                addUserPanel.add(passwordLabel);
                addUserPanel.add(passwordField);
                addUserPanel.add(clienteRadioButton);
                addUserPanel.add(adminRadioButton);
                addUserPanel.add(addButton);

                addUserFrame.setVisible(true);

                break;
            case "Accounts":
                // Crear y configurar el frame para agregar cuenta
                JFrame addAccountFrame = new JFrame("Agregar Cuenta");
                addAccountFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                addAccountFrame.setLayout(new GridLayout(0, 2, 10, 10)); // Usar GridLayout para organizar labels y campos
                addAccountFrame.setSize(400, 250);

                // Crear y configurar los componentes para ingresar detalles
                JLabel titularLabel = new JLabel("Titular:");
                JComboBox<Usuario> comboBoxTitulares = new JComboBox<>();
                List<Usuario> usuarios = usuarioService.getAll();
                for (Usuario usuario : usuarios) {
                    comboBoxTitulares.addItem(usuario);
                }
                comboBoxTitulares.setRenderer(new DefaultListCellRenderer() {
                    @Override
                    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                        if (value instanceof Usuario) {
                            Usuario usuario = (Usuario) value;
                            setText(usuario.getNombre() + " (" + usuario.getRole() + ")");
                        }
                        return this;
                    }
                });

                JLabel tipoLabel = new JLabel("Tipo de cuenta:");
                JTextField tipoField = new JTextField(20);
                JLabel saldoLabel = new JLabel("Saldo inicial:");
                JTextField saldoField = new JTextField(20);
                JButton addAccountButton = new JButton("Agregar Cuenta");

                // Agregar los componentes al frame
                addAccountFrame.add(titularLabel);
                addAccountFrame.add(comboBoxTitulares);
                addAccountFrame.add(tipoLabel);
                addAccountFrame.add(tipoField);
                addAccountFrame.add(saldoLabel);
                addAccountFrame.add(saldoField);
                addAccountFrame.add(new JLabel(""));
                addAccountFrame.add(addAccountButton);

                // Listener para el botón agregar
                addAccountButton.addActionListener(e -> {
                    try {
                        String tipo = tipoField.getText();
                        double saldo = Double.parseDouble(saldoField.getText());
                        Usuario titularSeleccionado = (Usuario) comboBoxTitulares.getSelectedItem();
                        if (titularSeleccionado != null && !tipo.isEmpty() && saldo >= 0) {
                            int idUsuario = titularSeleccionado.getId();
                            // Crear y guardar la cuenta
                            Cuenta nuevaCuenta = new Cuenta(0, tipo, saldo, idUsuario);
                            cuentaService.save(nuevaCuenta);

                            // Actualizar la tabla de cuentas y cerrar el frame
                            updateAccountsTableModel();
                            addAccountFrame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(addAccountFrame, "Por favor, ingrese datos válidos y seleccione un titular.");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(addAccountFrame, "Por favor, ingrese un saldo válido.");
                    }
                });

                // Mostrar el frame
                addAccountFrame.pack();
                addAccountFrame.setLocationRelativeTo(null); // Centrar en la pantalla
                addAccountFrame.setVisible(true);
                break;


            case "Cards":
                JFrame addCardFrame = new JFrame("Agregar Tarjeta");
                addCardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                addCardFrame.setLayout(new GridLayout(0, 2, 10, 10)); // GridLayout para la organización de los componentes
                addCardFrame.setSize(400, 250);

                // Definir los componentes para el tipo de tarjeta y su límite
                JLabel newTipoLabel = new JLabel("Tipo de tarjeta:");
                JTextField newTipoField = new JTextField(20);
                JLabel limiteLabel = new JLabel("Límite:");
                JTextField limiteField = new JTextField(20);
                JLabel saldoPagarLabel = new JLabel("Saldo a pagar:");
                JTextField saldoPagarField = new JTextField(20);
                JLabel idUsuarioLabel = new JLabel("ID de usuario:");
                JTextField idUsuarioField = new JTextField(20);
                JButton addCardButton = new JButton("Agregar Tarjeta");

                // Agregar componentes al frame
                addCardFrame.add(newTipoLabel);
                addCardFrame.add(newTipoField);
                addCardFrame.add(limiteLabel);
                addCardFrame.add(limiteField);
                addCardFrame.add(saldoPagarLabel);
                addCardFrame.add(saldoPagarField);
                addCardFrame.add(idUsuarioLabel);
                addCardFrame.add(idUsuarioField);
                addCardFrame.add(new JLabel(""));
                addCardFrame.add(addCardButton);


                // Listener para el botón agregar
                addCardButton.addActionListener(e -> {
                    try {
                        String tipo = newTipoField.getText();
                        double limite = Double.parseDouble(limiteField.getText());
                        double saldoPagar = Double.parseDouble(saldoPagarField.getText());
                        int idUsuario = Integer.parseInt(idUsuarioField.getText());

                        // Validar que los campos no estén vacíos
                        if (tipo.isEmpty() || limite < 0 || saldoPagar < 0) {
                            JOptionPane.showMessageDialog(addCardFrame, "Por favor, ingrese datos válidos.");
                            return;
                        }

                        // Crear y guardar la tarjeta
                        Tarjeta nuevaTarjeta = new Tarjeta(0, tipo, limite, saldoPagar, idUsuario);
                        tarjetaService.save(nuevaTarjeta);

                        // Actualizar la tabla de tarjetas y cerrar el frame
                        updateCardsTableModel();
                        addCardFrame.dispose();

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(addCardFrame, "Por favor, ingrese números válidos.");
                    }
                });

                addCardFrame.pack();
                addCardFrame.setLocationRelativeTo(null);
                addCardFrame.setVisible(true);
                break;
            case "Transactions":
                JFrame addTransactionFrame = new JFrame("Crear Transacción");
                addTransactionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                addTransactionFrame.setLayout(new GridLayout(0, 2, 10, 10)); // GridLayout para organizar los componentes
                addTransactionFrame.setSize(400, 250);

                // Crear y configurar componentes para la transacción
                JLabel lblCuentaOrigen = new JLabel("Cuenta Origen:");
                JComboBox<Cuenta> cbCuentaOrigen = new JComboBox<>();
                List<Cuenta> cuentasOrigen = cuentaService.getAll();
                cuentasOrigen.forEach(cbCuentaOrigen::addItem);

                JLabel lblCuentaDestino = new JLabel("Cuenta Destino:");
                JComboBox<Cuenta> cbCuentaDestino = new JComboBox<>();
                List<Cuenta> cuentasDestino = cuentaService.getAll();
                cuentasDestino.forEach(cbCuentaDestino::addItem);

                JLabel lblMonto = new JLabel("Monto:");
                JTextField txtMonto = new JTextField(20);

                JButton btnCrearTransaccion = new JButton("Realizar Transacción");

                // Agregar los componentes al frame
                addTransactionFrame.add(lblCuentaOrigen);
                addTransactionFrame.add(cbCuentaOrigen);
                addTransactionFrame.add(lblCuentaDestino);
                addTransactionFrame.add(cbCuentaDestino);
                addTransactionFrame.add(lblMonto);
                addTransactionFrame.add(txtMonto);
                addTransactionFrame.add(new JLabel(""));
                addTransactionFrame.add(btnCrearTransaccion);

                // Listener para el botón de crear transacción
                btnCrearTransaccion.addActionListener(e -> {
                    Cuenta cuentaOrigen = (Cuenta) cbCuentaOrigen.getSelectedItem();
                    Cuenta cuentaDestino = (Cuenta) cbCuentaDestino.getSelectedItem();
                    double monto;
                    try {
                        monto = Double.parseDouble(txtMonto.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(addTransactionFrame, "Por favor, ingrese un monto válido.");
                        return;
                    }

                    // Validar que las cuentas no sean nulas y que el monto sea positivo
                    if (cuentaOrigen != null && cuentaDestino != null && monto > 0) {
                        boolean transaccionExitosa = transaccionService.realizarTransferencia(
                                cuentaOrigen.getId(), cuentaDestino.getId(), monto);
                        if (transaccionExitosa) {
                            JOptionPane.showMessageDialog(addTransactionFrame, "Transacción realizada con éxito.");
                            updateTransactionsTableModel();
                        } else {
                            JOptionPane.showMessageDialog(addTransactionFrame, "La transacción no pudo ser realizada.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(addTransactionFrame, "Por favor, seleccione cuentas válidas y un monto positivo.");
                    }
                });

                // Configuración final del frame
                addTransactionFrame.pack();
                addTransactionFrame.setLocationRelativeTo(null);
                addTransactionFrame.setVisible(true);
                break;


        }
    }

    private void readEntity(JTable table, String entityType) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int entityId = (int) table.getValueAt(selectedRow, 0); // Obtén el ID de la entidad seleccionada

            switch (entityType) {
                case "Users":
                    Usuario usuario = usuarioService.get(entityId); // Obtén el usuario por ID
                    if (usuario != null) {
                        JOptionPane.showMessageDialog(this,
                                "ID: " + usuario.getId() + "\n" +
                                        "Username: " + usuario.getNombre() + "\n" +
                                        "Role: " + usuario.getRole(),
                                "Read User",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                case "Accounts":
                    Cuenta cuenta = cuentaService.get(entityId);
                    if (cuenta != null) {
                        JOptionPane.showMessageDialog(this,
                                "ID: " + cuenta.getId() + "\n" +
                                        "Tipo: " + cuenta.getTipo() + "\n" +
                                        "Saldo: " + cuenta.getSaldo(),
                                "Detalles de la Cuenta",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                case "Cards":
                    Tarjeta tarjeta = tarjetaService.get(entityId);
                    if (tarjeta != null) {
                        JOptionPane.showMessageDialog(this,
                                "ID: " + tarjeta.getId() + "\n" +
                                        "Tipo: " + tarjeta.getTipo() + "\n" +
                                        "Límite: " + tarjeta.getLimite() + "\n" +
                                        "Saldo a pagar: " + tarjeta.getSaldo_a_pagar(),
                                "Detalles de la Tarjeta",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Unsupported entity type: " + entityType, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void updateEntity(JTable table, String entityType) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int entityId = (int) table.getValueAt(selectedRow, 0); // Obtén el ID de la entidad seleccionada

            switch (entityType) {
                case "Users":
                    Usuario usuario = usuarioService.get(entityId); // Obtén el usuario por ID
                    if (usuario != null) {
                        // Mostrar un cuadro de diálogo para editar los datos del usuario
                        String nombre = JOptionPane.showInputDialog(this, "Nuevo nombre de usuario:", usuario.getNombre());
                        String contrasena = JOptionPane.showInputDialog(this, "Nueva contraseña:", usuario.getPassword());

                        // Validar la entrada del usuario
                        if (nombre != null && contrasena != null) {
                            // Actualizar los datos del usuario
                            usuario.setNombre(nombre);
                            usuario.setPassword(contrasena);

                            // Actualizar la base de datos o realizar la acción necesaria
                            usuarioService.update(usuario);

                            // Actualizar la tabla de usuarios
                            updateUsersTableModel();
                        }
                    }
                    break;
                case "Accounts":
                    Cuenta cuenta = cuentaService.get(entityId);
                    if (cuenta != null) {
                        // Mostrar cuadro de diálogo para editar los datos de la cuenta
                        String nuevoTipo = JOptionPane.showInputDialog(this, "Nuevo tipo de cuenta:", cuenta.getTipo());
                        double nuevoSaldo = Double.parseDouble(JOptionPane.showInputDialog(this, "Nuevo saldo:", cuenta.getSaldo()));

                        cuenta.setTipo(nuevoTipo);
                        cuenta.setSaldo(nuevoSaldo);

                        cuentaService.update(cuenta);
                        updateAccountsTableModel();
                    }
                    break;

                case "Cards":
                    Tarjeta tarjeta = tarjetaService.get(entityId);
                    if (tarjeta != null) {
                        // Mostrar cuadro de diálogo para editar los datos de la tarjeta
                        String nuevoTipo = JOptionPane.showInputDialog(this, "Nuevo tipo de tarjeta:", tarjeta.getTipo());
                        double nuevoLimite = Double.parseDouble(JOptionPane.showInputDialog(this, "Nuevo límite:", tarjeta.getLimite()));
                        double nuevoSaldoAPagar = Double.parseDouble(JOptionPane.showInputDialog(this, "Nuevo saldo a pagar:", tarjeta.getSaldo_a_pagar()));

                        tarjeta.setTipo(nuevoTipo);
                        tarjeta.setLimite(nuevoLimite);
                        tarjeta.setSaldo_a_pagar(nuevoSaldoAPagar);

                        tarjetaService.update(tarjeta);
                        updateCardsTableModel();
                    }
                    break;

                default:
                    JOptionPane.showMessageDialog(this, "Unsupported entity type: " + entityType, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void deleteEntity(JTable table, String entityType) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea eliminar esta " + entityType + "?",
                    "Eliminar " + entityType,
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                int entityId = (int) table.getValueAt(selectedRow, 0);

                switch (entityType) {
                    case "Users":
                        Usuario usuario = usuarioService.get(entityId);
                        if (usuario != null) {
                            usuarioService.delete(usuario);
                            updateUsersTableModel();
                        }
                        break;

                    case "Accounts":
                        Cuenta cuenta = cuentaService.get(entityId);
                        if (cuenta != null) {
                            cuentaService.delete(cuenta);
                            updateAccountsTableModel();
                        }
                        break;

                    case "Cards":
                        Tarjeta tarjeta = tarjetaService.get(entityId);
                        if (tarjeta != null) {
                            tarjetaService.delete(tarjeta);
                            updateCardsTableModel();
                        }
                        break;

                    case "Transactions":
                        Transaccion transaccion = transaccionService.get(entityId);
                        if (transaccion != null) {
                            transaccionService.delete(transaccion);
                            updateTransactionsTableModel();
                        }
                        break;

                    default:
                        JOptionPane.showMessageDialog(this,
                                "Tipo de entidad no soportado: " + entityType,
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }


    // Método para eliminar la entidad en la base de datos
    private boolean deleteEntityInDatabase(int entityId) {
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        try {
            Usuario usuario = usuarioDAO.get(entityId); // Obtén el usuario por ID
            if (usuario != null) {
                usuarioDAO.delete(usuario); // Llama al método delete de UsuarioDAO
                return true; // Devuelve true si la eliminación fue exitosa
            } else {
                return false; // Devuelve false si el usuario no se encontró en la base de datos
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false; // Devuelve false si ocurrió una excepción durante la eliminación
        }
    }

    public void showPanel() {
        this.revalidate();
        this.repaint();
        updateTableModels();

    }


    //UPDATE TABLE
    private void updateTableModels() {
        updateUsersTableModel();
        updateAccountsTableModel();
        updateCardsTableModel();
        updateTransactionsTableModel();
    }

    private void updateAccountsTableModel() {
        List<Cuenta> cuentas = cuentaService.getAll();
        DefaultTableModel model = (DefaultTableModel) tableCuentas.getModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(new Object[]{"ID", "Account Name", "Balance"});

        if (cuentas != null) {
            for (Cuenta cuenta : cuentas) {
                model.addRow(new Object[]{cuenta.getId(), cuenta.getTipo(), cuenta.getSaldo()});
            }
        }
    }

    private void updateCardsTableModel() {
        List<Tarjeta> tarjetas = tarjetaService.getAll();
        DefaultTableModel model = (DefaultTableModel) tableTarjetas.getModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(new Object[]{"ID", "Card Type", "Limit", "Balance to Pay"});

        if (tarjetas != null) {
            for (Tarjeta tarjeta : tarjetas) {
                model.addRow(new Object[]{tarjeta.getId(), tarjeta.getTipo(), tarjeta.getLimite(), tarjeta.getSaldo_a_pagar()});
            }
        }
    }

    private void updateTransactionsTableModel() {
        List<Transaccion> transacciones = transaccionService.getAll();
        DefaultTableModel model = (DefaultTableModel) tableTransacciones.getModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(new Object[]{"ID", "Sender ID", "Receiver ID", "Amount"});

        if (transacciones != null) {
            for (Transaccion transaccion : transacciones) {
                model.addRow(new Object[]{transaccion.getId(), transaccion.getId_emisor(), transaccion.getId_receptor(), transaccion.getMonto()});
            }
        }
    }

    private void updateUsersTableModel() {
        List<Usuario> usuarios = usuarioService.getAll();
        DefaultTableModel model = (DefaultTableModel) tableUsuarios.getModel();
        model.setRowCount(0); //
        model.setColumnIdentifiers(new Object[]{"ID", "Username", "Role"});

        if (usuarios != null) {
            for (Usuario usuario : usuarios) {
                model.addRow(new Object[]{usuario.getId(), usuario.getNombre(), usuario.getRole()});
            }
        }
    }
}