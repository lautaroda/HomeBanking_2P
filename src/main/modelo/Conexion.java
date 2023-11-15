package main.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String url = "jdbc:postgresql://localhost:5432/mini_home_banking";
    private static final String user = "postgres";
    private static final String password = "";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
