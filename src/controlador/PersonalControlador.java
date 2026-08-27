package controlador;

import modelo.*;
import vista.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.List;

public class PersonalControlador {
    private PersonalVista vista;
    private PersonalDAO dao;
    private JerarquiaDAO jerarquiaDAO;
    private MenuPrincipal menu;

    public PersonalControlador(PersonalVista vista, PersonalDAO dao, JerarquiaDAO jerarquiaDAO, MenuPrincipal menu) {
        this.vista = vista;
        this.dao = dao;
        this.jerarquiaDAO = jerarquiaDAO;
        this.menu = menu;

        this.vista.getBtnAgregar().addActionListener(e -> agregar());
        this.vista.getBtnEditar().addActionListener(e -> editar());
        this.vista.getBtnEliminar().addActionListener(e -> eliminar());
        this.vista.getBtnRefrescar().addActionListener(e -> refrescar());
        this.vista.getBtnVolver().addActionListener(e -> volver());

        // 🔹 Cuando seleccionás una fila, se cargan los datos
        vista.getTabla().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int id = vista.getIdSeleccionado();
                    if (id != -1) {
                        Personal p = dao.obtenerPorId(id);
                        if (p != null) {
                            vista.mostrarPersonalSeleccionado(p);
                        }
                    }
                }
            }
        });

        refrescar();
    }

    private void agregar() {
        String nombre = vista.getTxtNombre();
        Jerarquia j = vista.getJerarquiaSeleccionada();
        if (nombre.isEmpty() || j == null) {
            JOptionPane.showMessageDialog(vista, "Complete todos los campos correctamente.");
            return;
        }

        Personal p = new Personal(0, nombre, j);
        dao.agregar(p);
        refrescar();
        vista.limpiarCampos();
    }

    private void editar() {
        int id = vista.getIdSeleccionado();
        if (id == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un personal para editar.");
            return;
        }
        String nombre = vista.getTxtNombre();
        Jerarquia j = vista.getJerarquiaSeleccionada();
        if (nombre.isEmpty() || j == null) {
            JOptionPane.showMessageDialog(vista, "Complete todos los campos correctamente.");
            return;
        }
        Personal p = new Personal(id, nombre, j);
        dao.actualizar(p);
        refrescar();
    }

    private void eliminar() {
        int id = vista.getIdSeleccionado();
        if (id == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un personal para eliminar.");
            return;
        }
        if (JOptionPane.showConfirmDialog(vista, "¿Eliminar este personal?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            dao.eliminar(id);
            refrescar();
        }
    }

    private void refrescar() {
        List<Jerarquia> jer = jerarquiaDAO.listar();
        vista.setJerarquias(jer);
        vista.mostrarPersonal(dao.listar());
        vista.limpiarCampos(); // 🔹 Limpia los campos al refrescar
    }

    private void volver() {
        vista.dispose();
        if (menu != null) menu.setVisible(true);
    }
}
