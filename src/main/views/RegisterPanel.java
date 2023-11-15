package main.views;

import main.dao.UsuarioDAOImpl;
import main.modelo.*;
import main.service.UsuarioService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RegisterPanel {
    private JFrame frame;
    private UsuarioService usuarioService;
    public class Roles {
        public static final String ADMIN = "Admin";
        public static final String CLIENT = "Client";
    }

    public RegisterPanel() {
        // Crear el marco de la ventana
        frame = new JFrame("Registro");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Crear el panel y añadirlo al marco
        JPanel panel = new JPanel();
        frame.add(panel);

        // Llamar al método que añade los componentes al panel
        placeComponents(panel);

        // Inicializar el servicio de usuario
        UsuarioDAOImpl usuarioDAO = new UsuarioDAOImpl(); // Reemplaza UsuarioDAOImpl con tu implementación real
        usuarioService = new UsuarioService(usuarioDAO);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // Crear y configurar las etiquetas y los campos de texto para el nombre de usuario, la contraseña y el rol
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

        // Crear y configurar el JComboBox para el rol
        JLabel roleLabel = new JLabel("Rol");
        roleLabel.setBounds(10, 80, 80, 25);
        panel.add(roleLabel);

        JComboBox<String> roleBox = new JComboBox<>(new String[]{Roles.ADMIN, Roles.CLIENT});

        roleBox.setBounds(100, 80, 165, 25);
        panel.add(roleBox);

        JTextField roleText = new JTextField(20);
        roleText.setBounds(100, 80, 165, 25);
        panel.add(roleText);

        // Crear y configurar el botón de registro
        JButton createAccountButton = new JButton("Crear cuenta");
        createAccountButton.setBounds(10, 110, 120, 25);

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = userText.getText();
                String contrasena = new String(passwordText.getPassword());
                List<Cuenta> cuentas = new ArrayList<>();
                List<Tarjeta> tarjetas = new ArrayList<>();

                String rol = (String) roleBox.getSelectedItem();

                Usuario newUser;
                if (Roles.ADMIN.equals(rol)) {
                    newUser = new Administrador(0, usuario, contrasena, rol);
                    JOptionPane.showMessageDialog(frame, "Administrador creado con éxito");
                } else if (Roles.CLIENT.equals(rol)) {
                    newUser = new Cliente(0, usuario, contrasena, rol, new ArrayList<>(), new ArrayList<>());
                    JOptionPane.showMessageDialog(frame, "Cliente creado con éxito");
                } else {
                    JOptionPane.showMessageDialog(frame, "Rol no reconocido.");
                    return;
                }

                usuarioService.save(newUser);
                frame.dispose();
            }
        });

        panel.add(createAccountButton);
    }

    public void show() {
        // Mostrar la ventana
        frame.setVisible(true);
    }
}
