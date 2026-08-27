package controlador;

import modelo.*;
import vista.*;
import javax.swing.*;
import java.util.List;
import util.Validaciones;

public class JerarquiaControlador {
    private JerarquiaVista vista;
    private JerarquiaDAO dao;
    private MenuPrincipal menu;

    public JerarquiaControlador(JerarquiaVista vista, JerarquiaDAO dao, MenuPrincipal menu) {
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
        String nombre = vista.getTxtNombre();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese un nombre de jerarquía.");
            return;
        }
        Jerarquia j = new Jerarquia(0, nombre);
        dao.agregar(j);
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
            JOptionPane.showMessageDialog(vista, "Seleccione una jerarquía para editar.");
            return;
        }
        String nombre = vista.getTxtNombre();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese un nombre válido.");
            return;
        }
        Jerarquia j = new Jerarquia(id, nombre);
        dao.actualizar(j);
        refrescar();
    }

    private void eliminar() {
        int id = vista.getIdSeleccionado();
        if (id == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione una jerarquía para eliminar.");
            return;
        }
        if (JOptionPane.showConfirmDialog(vista, "¿Eliminar jerarquía?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            dao.eliminar(id);
            refrescar();
        }
    }

    private void refrescar() {
        List<Jerarquia> lista = dao.listar();
        vista.mostrarJerarquias(lista);
        // <<-- importante: limpiar campos y deseleccionar fila para que el formulario quede vacío
        vista.limpiarCampos();
    }

    private void volver() {
        vista.dispose();
        if (menu != null) menu.setVisible(true);
    }
}
