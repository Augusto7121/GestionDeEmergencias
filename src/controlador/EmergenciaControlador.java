package controlador;

import modelo.*;
import vista.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class EmergenciaControlador {
    private EmergenciaVista vista;
    private EmergenciaDAO dao;
    private TipoEmergenciaDAO tipoDAO;
    private PersonalDAO personalDAO;
    private VehiculoDAO vehiculoDAO;
    private EmergenciaPersonalDAO emergenciaPersonalDAO;
    private EmergenciaVehiculoDAO emergenciaVehiculoDAO;
    private MenuPrincipal menu;

    public EmergenciaControlador(EmergenciaVista vista, EmergenciaDAO dao, TipoEmergenciaDAO tipoDAO,
                                 PersonalDAO personalDAO, VehiculoDAO vehiculoDAO,
                                 EmergenciaPersonalDAO emergenciaPersonalDAO,
                                 EmergenciaVehiculoDAO emergenciaVehiculoDAO,
                                 MenuPrincipal menu) {
        this.vista = vista;
        this.dao = dao;
        this.tipoDAO = tipoDAO;
        this.personalDAO = personalDAO;
        this.vehiculoDAO = vehiculoDAO;
        this.emergenciaPersonalDAO = emergenciaPersonalDAO;
        this.emergenciaVehiculoDAO = emergenciaVehiculoDAO;
        this.menu = menu;

        this.vista.getBtnAgregar().addActionListener(e -> agregar());
        this.vista.getBtnEditar().addActionListener(e -> editar());
        this.vista.getBtnEliminar().addActionListener(e -> eliminar());
        this.vista.getBtnRefrescar().addActionListener(e -> refrescar());
        this.vista.getBtnVolver().addActionListener(e -> volver());

        // 🔹 Escucha selección en la tabla
        this.vista.getTabla().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int id = vista.getIdSeleccionado();
                    if (id != -1) {
                        Emergencia emergencia = dao.obtenerPorId(id); // 🔹 nuevo método
                        if (emergencia != null) {
                            // Cargar también los personales y vehículos relacionados
                            List<Personal> personales = emergenciaPersonalDAO.obtenerPorEmergencia(id);
                            List<Vehiculo> vehiculos = emergenciaVehiculoDAO.obtenerPorEmergencia(id);
                            emergencia.setPersonales(personales);
                            emergencia.setVehiculos(vehiculos);

                           // vista.mostrarEmergenciaSeleccionada(emergencia);
                        }
                    }
                }
            }
        });

        refrescar();
    }

    private void agregar() {
        Date fecha = vista.getFechaSeleccionada();
        Time hora = vista.getHoraSeleccionada();
        TipoEmergencia tipo = vista.getTipoSeleccionado();
        String direccion = vista.getTxtDireccion();
        String resultado = vista.getTxtResultado();

        if (fecha == null || hora == null || tipo == null || direccion.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Complete todos los campos correctamente.");
            return;
        }

        Emergencia emergencia = new Emergencia(0, fecha, hora, direccion, tipo, resultado);
        int idEmergencia = dao.agregarConRetorno(emergencia);

        if (idEmergencia > 0) {
            emergencia.setId(idEmergencia);

            List<Personal> personalesSeleccionados = vista.getPersonalSeleccionado();
            List<Vehiculo> vehiculosSeleccionados = vista.getVehiculosSeleccionados();

            for (Personal p : personalesSeleccionados) {
                emergenciaPersonalDAO.agregar(idEmergencia, p.getId());
            }

            for (Vehiculo v : vehiculosSeleccionados) {
                emergenciaVehiculoDAO.agregar(idEmergencia, v.getId());
            }

            emergencia.setPersonales(personalesSeleccionados);
            emergencia.setVehiculos(vehiculosSeleccionados);
        }

        refrescar();
        vista.limpiarCampos();
    }

    private void editar() {
        int id = vista.getIdSeleccionado();
        if (id == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione una emergencia para editar.");
            return;
        }

        Emergencia e = new Emergencia(
                id,
                vista.getFechaSeleccionada(),
                vista.getHoraSeleccionada(),
                vista.getTxtDireccion(),
                vista.getTipoSeleccionado(),
                vista.getTxtResultado()
        );

        // 🔹 Actualizar datos principales
        dao.actualizar(e);

        // 🔹 Primero eliminar relaciones viejas
        emergenciaPersonalDAO.eliminarPorEmergencia(id);
        emergenciaVehiculoDAO.eliminarPorEmergencia(id);

        // 🔹 Agregar las nuevas relaciones
        for (Personal p : vista.getPersonalSeleccionado()) {
            emergenciaPersonalDAO.agregar(id, p.getId());
        }
        for (Vehiculo v : vista.getVehiculosSeleccionados()) {
            emergenciaVehiculoDAO.agregar(id, v.getId());
        }

        refrescar();
        JOptionPane.showMessageDialog(vista, "Emergencia actualizada correctamente.");
    }

    private void eliminar() {
        int id = vista.getIdSeleccionado();
        if (id == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione una emergencia para eliminar.");
            return;
        }

        if (JOptionPane.showConfirmDialog(vista, "¿Seguro que desea eliminar esta emergencia?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            emergenciaPersonalDAO.eliminarPorEmergencia(id);
            emergenciaVehiculoDAO.eliminarPorEmergencia(id);
            dao.eliminar(id);
            refrescar();
        }
    }

    private void refrescar() {
        List<TipoEmergencia> tipos = tipoDAO.listar();
        vista.setTiposEmergencia(tipos);

        List<Personal> personales = personalDAO.listar();
        vista.setListaPersonal(personales);

        List<Vehiculo> vehiculos = vehiculoDAO.listar();
        vista.setListaVehiculos(vehiculos);

        vista.mostrarEmergencias(dao.listar());

        // 🔹 Limpia los campos del formulario y los checkboxes
        vista.limpiarCampos();
    }


    private void volver() {
        vista.dispose();
        menu.setVisible(true);
    }

}
