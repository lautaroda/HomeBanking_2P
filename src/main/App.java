package main;
import main.dao.*;
import main.dao.interfaces.*;
import main.modelo.*;
import main.service.*;

import java.util.List;

public class App {
    public static void main(String[] args) {
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        UsuarioService usuarioService = new UsuarioService(usuarioDAO);

        CuentaDAO cuentaDao = new CuentaDAOImpl();
        CuentaService cuentaService = new CuentaService(cuentaDao);


        TarjetaDAO tarjetaDAO = new main.dao.TarjetaDAOImpl();
        TarjetaService tarjetaService = new TarjetaService(tarjetaDAO);

        TransaccionDAO transaccionDao = new TransaccionDAOImpl();
        TransaccionService transaccionService = new TransaccionService(transaccionDao, cuentaService);

        main.views.LoginView loginView = new main.views.LoginView();
        loginView.show();
    }
}
