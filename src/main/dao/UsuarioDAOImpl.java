package main.dao;

import main.modelo.*;
import main.dao.interfaces.UsuarioDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {


    public Usuario getByCredentials(String nombre, String contrasena) {

        System.out.println("Iniciando búsqueda de usuario con credenciales: " + nombre + " / " + contrasena);

        try (Connection conn = Conexion.conectar()) {
            System.out.println("Conectado a la base de datos");

            PreparedStatement ps = conn.prepareStatement(
                    "SELECT id, nombre, password, role FROM usuarios WHERE nombre = ? AND password = ?"
            );

            ps.setString(1, nombre);
            ps.setString(2, contrasena);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Usuario encontrado en la base de datos con id: " + rs.getInt("id"));

                // obtener datos del usuario
                int id = rs.getInt("id");
                String userNombre = rs.getString("nombre");
                String userPass = rs.getString("password");
                String role = rs.getString("role");

                System.out.println("Preparando objeto de usuario con role: " + role);

                // crear objeto Usuario
                Usuario user = null;
                if ("Client".equals(role)) {
                    // crear Cliente
                    System.out.println("Creando objeto Cliente");
                    List<Cuenta> cuentas = getCuentasDelCliente(id);
                    List<Tarjeta> tarjetas  = getTarjetasDelCliente(id);

                    user = new Cliente(id, userNombre, userPass, role, cuentas, tarjetas);
                    System.out.println("Objeto Cliente creado con " + cuentas.size() + " cuentas y " + tarjetas.size() + " tarjetas.");

                } else if ("Admin".equals(role)) {
                    // crear Administrador
                    System.out.println("Creando objeto Administrador");
                    user = new Administrador(id, userNombre, userPass, role);
                    System.out.println("Objeto Administrador creado");
                }

                // devolver usuario encontrado
                return user;

            } else {
                System.out.println("No se encontró el usuario con las credenciales proporcionadas.");
            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Finalizando búsqueda de usuario sin éxito");
        return null;
    }
    @Override
    public Usuario get(int id) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM usuarios WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                String nombre = rs.getString("nombre");
                String password = rs.getString("password"); // obtener password
                System.out.println("Rol del usuario: " + role); // Agregar mensaje de depuración
                if ("Client".equals(role)) {
                    // Obtén las cuentas del cliente de la base de datos
                    List<Cuenta> cuentas = getCuentasDelCliente(id);
                    // Obtén las tarjetas del cliente de la base de datos
                    List<Tarjeta> tarjetas = getTarjetasDelCliente(id);
                    System.out.println("Cuentas del cliente: " + cuentas);
                    System.out.println("Tarjetas del cliente: " + tarjetas);
                    return new Cliente(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("password"),
                            role,
                            cuentas,
                            tarjetas
                    );
                } else if ("Admin".equals(role)) {
                    return new Administrador(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("password"),
                            role
                    );
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }

        return null;
    }
    @Override
    public List<Usuario> getAll() {
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = Conexion.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios")) {

            if (!rs.isBeforeFirst()) {
                System.out.println("No se encontraron registros en la tabla de usuarios.");
            }

            while (rs.next()) {
                String role = rs.getString("role");
                System.out.println("Rol del usuario con ID " + rs.getInt("id") + ": " + role);

                if ("Client".equalsIgnoreCase(role)) {
                    int id = rs.getInt("id");
                    List<Cuenta> cuentas = getCuentasDelCliente(id);
                    List<Tarjeta> tarjetas = getTarjetasDelCliente(id);

                    System.out.println("Cuentas del cliente con ID " + id + ": " + cuentas.size());
                    System.out.println("Tarjetas del cliente con ID " + id + ": " + tarjetas.size());

                    usuarios.add(new Cliente(
                            id,
                            rs.getString("nombre"),
                            rs.getString("password"),
                            role,
                            cuentas,
                            tarjetas
                    ));
                } else if ("Admin".equalsIgnoreCase(role)) {
                    usuarios.add(new Administrador(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("password"),
                            role
                    ));
                } else {
                    System.out.println("El rol " + role + " no es reconocido.");
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            ex.printStackTrace();
        }

        return usuarios;
    }
    private List<Tarjeta> getTarjetasDelCliente(int idCliente) {
        List<Tarjeta> tarjetas = new ArrayList<>();
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM tarjetas WHERE id_usuario = ?")) {
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tarjetas.add(new Tarjeta(
                        rs.getInt("id"),
                        rs.getString("tipo"),
                        rs.getDouble("limite"),
                        rs.getDouble("saldo_a_pagar"),
                        idCliente
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
        return tarjetas;
    }
    private List<Cuenta> getCuentasDelCliente(int idCliente) {
        List<Cuenta> cuentas = new ArrayList<>();
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM cuentas WHERE id_usuario = ?")) {

            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                cuentas.add(new Cuenta(
                        rs.getInt("id"),
                        rs.getString("tipo"),
                        rs.getDouble("saldo"),
                        idCliente
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cuentas;
    }

    @Override
    public void save(Usuario usuario) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO usuarios (nombre, password, role) VALUES (?, ?, ?)")) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getRole());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Usuario usuario) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement("UPDATE usuarios SET nombre = ?, password = ?, role = ? WHERE id = ?")) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getRole());
            ps.setInt(4, usuario.getId());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Usuario usuario) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM usuarios WHERE id = ?")) {

            ps.setInt(1, usuario.getId());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
