package controlador;

import vista.*;
import modelo.*;

public class MenuPrincipalControlador {

    private MenuPrincipal menu;

    public MenuPrincipalControlador(MenuPrincipal menu) {
        this.menu = menu;

        // Botones del menú principal
        menu.getBtnPersonal().addActionListener(e -> abrirPersonal());
        menu.getBtnJerarquia().addActionListener(e -> abrirJerarquia());
        menu.getBtnVehiculo().addActionListener(e -> abrirVehiculo());
        menu.getBtnEmergencia().addActionListener(e -> abrirEmergencias());
        menu.getBtnTipoEmergencia().addActionListener(e -> abrirTipoEmergencia());
    }

    private void abrirPersonal() {
        PersonalVista vista = new PersonalVista();
        PersonalControlador controlador = new PersonalControlador(vista, new PersonalDAO(), new JerarquiaDAO(), menu);
        vista.setVisible(true);
        menu.setVisible(false);
    }

    private void abrirJerarquia() {
        JerarquiaVista vista = new JerarquiaVista();
        JerarquiaControlador controlador = new JerarquiaControlador(vista, new JerarquiaDAO(), menu);
        vista.setVisible(true);
        menu.setVisible(false);
    }

    private void abrirVehiculo() {
        VehiculoVista vista = new VehiculoVista();
        VehiculoControlador controlador = new VehiculoControlador(vista, new VehiculoDAO(), menu);
        vista.setVisible(true);
        menu.setVisible(false);
    }

    private void abrirEmergencias() {
        EmergenciaVista vista = new EmergenciaVista();
        EmergenciaControlador controlador = new EmergenciaControlador(
                vista,
                new EmergenciaDAO(),
                new TipoEmergenciaDAO(),
                new PersonalDAO(),
                new VehiculoDAO(),
                new EmergenciaPersonalDAO(),
                new EmergenciaVehiculoDAO(),
                menu
        );
        vista.setVisible(true);
        menu.setVisible(false);
    }

    private void abrirTipoEmergencia() {
        TipoEmergenciaVista vista = new TipoEmergenciaVista();
        TipoEmergenciaControlador controlador = new TipoEmergenciaControlador(vista, new TipoEmergenciaDAO(), menu);
        vista.setVisible(true);
        menu.setVisible(false);
    }
}
