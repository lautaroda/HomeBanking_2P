package main.views;

import main.dao.*;
import main.dao.interfaces.CuentaDAO;
import main.dao.interfaces.TarjetaDAO;
import main.dao.interfaces.TransaccionDAO;
import main.dao.interfaces.UsuarioDAO;
import main.modelo.Cuenta;
import main.modelo.Usuario;
import main.service.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginView {
    private JFrame frame;
    private UsuarioService usuarioService;
    private CuentaService cuentaService;
    private TarjetaService tarjetaService;
    private TransaccionService transaccionService;

    public static class Roles {
        public static final String ADMIN = "Admin";
        public static final String CLIENT = "Client";
    }

    public LoginView() {
        // Crear el marco de la ventana
        frame = new JFrame("Inicio de sesión");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear el panel y añadirlo al marco
        JPanel panel = new JPanel();
        frame.add(panel);

        // Llamar al método que añade los componentes al panel
        placeComponents(panel);

        // Inicializar el servicio de usuario y otros servicios
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        usuarioService = new UsuarioService(usuarioDAO);

        CuentaDAO cuentaDAO = new CuentaDAOImpl();
        cuentaService = new CuentaService(cuentaDAO);

        TarjetaDAO tarjetaDAO = new TarjetaDAOImpl();
        tarjetaService = new TarjetaService(tarjetaDAO);

        TransaccionDAO transaccionDAO = new TransaccionDAOImpl();
        transaccionService = new TransaccionService(transaccionDAO, cuentaService);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Usuario");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Contraseña");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("Iniciar sesión");
        loginButton.setBounds(10, 80, 120, 25);

        JButton registerButton = new JButton("Registrarse");
        registerButton.setBounds(140, 80, 120, 25);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear la ventana de registro
                JFrame registerFrame = new JFrame("Registro");
                registerFrame.setSize(300, 200);
                registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // Crear el panel de registro y añadirlo al marco
                RegisterPanel registerPanel = new RegisterPanel();
                registerPanel.show();

            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = userText.getText();
                String contrasena = new String(passwordText.getPassword());

                System.out.println("Intento de inicio de sesión para usuario: " + usuario);

                Usuario user = usuarioService.getUsuario(usuario, contrasena);



                if (user != null) {
                    CuentaDAO cuentaDao = new CuentaDAOImpl();
                    CuentaService cuentaService = new CuentaService(cuentaDao);
                    TarjetaDAO tarjetaDao = new TarjetaDAOImpl();
                    TarjetaService tarjetaService = new TarjetaService(tarjetaDao);

                    TransaccionDAO transaccionDao = new TransaccionDAOImpl();
                    TransaccionService transaccionService = new TransaccionService(transaccionDao, cuentaService);
                    List<Cuenta> cuentasUsuario = cuentaService.getAll();

                    if (UsuarioService.Roles.ADMIN.equals(user.getRole())) {
                        JFrame adminFrame = new JFrame("Panel de Administrador");
                        AdministradorPanel adminPanel = new AdministradorPanel(usuarioService, cuentaService, tarjetaService, transaccionService);
                        adminFrame.setContentPane(adminPanel);
                        adminFrame.pack();
                        adminFrame.setVisible(true);
                        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    } else if (UsuarioService.Roles.CLIENT.equals(user.getRole())) {
                        System.out.println("El usuario es un cliente.");
                        JFrame clientFrame = new JFrame("Panel de Cliente");

                        ClientePanel clientePanel = new ClientePanel(user, cuentaService, tarjetaService, transaccionService);

                        clientFrame.setContentPane(clientePanel);
                        clientFrame.pack();
                        clientFrame.setVisible(true);
                        clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Rol de usuario no reconocido.");
                    }
                    frame.dispose(); // Cierra la ventana de inicio de sesión
                } else {
                    JOptionPane.showMessageDialog(frame, "Nombre de usuario o contraseña incorrectos.");
                }
            }
        });


        panel.add(loginButton);
        panel.add(registerButton);

    }

    public void show() {
        frame.setVisible(true);
    }
}

