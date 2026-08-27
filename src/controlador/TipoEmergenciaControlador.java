package controlador;

import modelo.*;
import vista.*;
import javax.swing.*;
import java.util.List;
import util.Validaciones;

public class TipoEmergenciaControlador {
    private TipoEmergenciaVista vista;
    private TipoEmergenciaDAO dao;
    private MenuPrincipal menu;

    public TipoEmergenciaControlador(TipoEmergenciaVista vista, TipoEmergenciaDAO dao, MenuPrincipal menu) {
        this.vista = vista;
        this.dao = dao;
        this.menu = menu;

        vista.getBtnAgregar().addActionListener(e -> agregar());
        vista.getBtnEditar().addActionListener(e -> editar());
        vista.getBtnEliminar().addActionListener(e -> eliminar());
        vista.getBtnRefrescar().addActionListener(e -> refrescar());
        vista.getBtnVolver().addActionListener(e -> volver());

        refrescar();
    }

    private void agregar() {
        String nombre = vista.getTxtDescripcion();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese un tipo de emergencia.");
            return;
        }
        dao.agregar(new TipoEmergencia(0, nombre));
        refrescar();
        vista.limpiarCampos();

        // Validaciones básicas
        if (nombre.isEmpty() ) {
            JOptionPane.showMessageDialog(vista, "Complete todos los campos correctamente.");
            return;
        }
    }

    private void editar() {
        int id = vista.getIdSeleccionado();
        if (id == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un tipo de emergencia para editar.");
            return;
        }
        dao.actualizar(new TipoEmergencia(id, vista.getTxtDescripcion()));
        refrescar();
    }

    private void eliminar() {
        int id = vista.getIdSeleccionado();
        if (id == -1) return;
        dao.eliminar(id);
        refrescar();
    }

    private void refrescar() {
        List<TipoEmergencia> lista = dao.listar();
        vista.mostrarTiposEmergencia(lista);
        vista.limpiarCampos(); // 🔹 limpia campos y selección
    }


    private void volver() {
        vista.dispose();
        menu.setVisible(true);
    }

}
