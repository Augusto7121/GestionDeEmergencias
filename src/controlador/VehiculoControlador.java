package controlador;

import modelo.*;
import vista.*;
import util.Validaciones;
import javax.swing.*;
import java.util.List;

public class VehiculoControlador {
    private VehiculoVista vista;
    private VehiculoDAO dao;
    private MenuPrincipal menu;

    public VehiculoControlador(VehiculoVista vista, VehiculoDAO dao, MenuPrincipal menu) {
        this.vista = vista;
        this.dao = dao;
        this.menu = menu;

        this.vista.getBtnAgregar().addActionListener(e -> agregar());
        this.vista.getBtnEditar().addActionListener(e -> editar());
        this.vista.getBtnEliminar().addActionListener(e -> eliminar());
        this.vista.getBtnRefrescar().addActionListener(e -> refrescar());
        this.vista.getBtnVolver().addActionListener(e -> volver());

        refrescar();
    }

    private void agregar() {
        String patente = vista.getTxtPatente();
        String tipo = vista.getTxtTipo();
        String estado = vista.getTxtEstado();

        // Validaciones básicas
        if (patente.isEmpty() || tipo.isEmpty() || estado.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Complete todos los campos correctamente.");
            return;
        }

        // Validación del formato de la patente
        if (!Validaciones.validarPatente(patente)) {
            return; // si no es válida, cancela la acción
        }

        Vehiculo v = new Vehiculo(0, patente.toUpperCase(), tipo, estado);
        dao.agregar(v);
        refrescar();
    }

    private void editar() {
        int id = vista.getIdSeleccionado();
        if (id == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un vehículo para editar.");
            return;
        }

        String patente = vista.getTxtPatente();
        String tipo = vista.getTxtTipo();
        String estado = vista.getTxtEstado();

        // 🔹 Validaciones básicas
        if (patente.isEmpty() || tipo.isEmpty() || estado.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Complete todos los campos correctamente.");
            return;
        }

        // 🔹 Validación del formato de la patente
        if (!Validaciones.validarPatente(patente)) {
            return;
        }

        Vehiculo v = new Vehiculo(id, patente.toUpperCase(), tipo, estado);
        dao.actualizar(v);
        refrescar();
    }

    private void eliminar() {
        int id = vista.getIdSeleccionado();
        if (id == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un vehículo para eliminar.");
            return;
        }

        if (JOptionPane.showConfirmDialog(vista,
                "¿Seguro que desea eliminar este vehículo?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            dao.eliminar(id);
            refrescar();
        }
    }

    private void refrescar() {
        vista.mostrarVehiculos(dao.listar());
        vista.limpiarCampos(); // 🔹 limpia los campos y deselecciona la fila
    }

    private void volver() {
        vista.dispose();
        if (menu != null) menu.setVisible(true);
    }
}
