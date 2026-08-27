package vista;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    private JButton btnPersonal;
    private JButton btnJerarquia;
    private JButton btnVehiculo;
    private JButton btnEmergencia;
    private JButton btnTipoEmergencia;

    public MenuPrincipal() {
        setTitle("Menú Principal - Gestión de Emergencias");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridLayout(5, 1, 10, 10));
        setBackground(Color.WHITE);

        btnPersonal = new JButton("\uD83D\uDC68\u200D Gestión de Personal");
        btnJerarquia = new JButton("\uD83E\uDD47 Gestión de Jerarquías");
        btnVehiculo = new JButton("\uD83D\uDE92 Gestión de Vehículos");
        btnEmergencia = new JButton("\uD83D\uDEA8 Gestión de Emergencias");
        btnTipoEmergencia = new JButton("\uD83D\uDD17 Gestión de Tipos de Emergencia");

        add(btnPersonal);
        add(btnJerarquia);
        add(btnVehiculo);
        add(btnEmergencia);
        add(btnTipoEmergencia);
    }

    // --- Getters ---
    public JButton getBtnPersonal() { return btnPersonal; }
    public JButton getBtnJerarquia() { return btnJerarquia; }
    public JButton getBtnVehiculo() { return btnVehiculo; }
    public JButton getBtnEmergencia() { return btnEmergencia; }
    public JButton getBtnTipoEmergencia() { return btnTipoEmergencia; }
}
