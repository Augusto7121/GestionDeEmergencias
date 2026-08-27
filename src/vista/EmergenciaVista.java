package vista;

import modelo.*;
import org.jdatepicker.impl.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class EmergenciaVista extends JFrame {

    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JDatePickerImpl datePicker;
    private JSpinner timeSpinner;
    private JTextField txtDireccion;
    private JTextArea txtResultado;
    private JComboBox<TipoEmergencia> comboTipo;
    private JButton btnAgregar, btnEditar, btnEliminar, btnRefrescar, btnVolver;
    private JPanel panelPersonal, panelVehiculos;

    private List<Personal> personales = new ArrayList<>();
    private List<Vehiculo> vehiculos = new ArrayList<>();
    private List<JCheckBox> checkBoxesPersonal = new ArrayList<>();
    private List<JCheckBox> checkBoxesVehiculos = new ArrayList<>();

    public EmergenciaVista() {
        setTitle("Emergencias");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createTitledBorder("Datos de la emergencia"));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fecha
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Fecha:"), gbc);
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        gbc.gridx = 1;
        form.add(datePicker, gbc);

        // Hora
        gbc.gridx = 2;
        form.add(new JLabel("Hora:"), gbc);
        SpinnerDateModel timeModel = new SpinnerDateModel();
        timeSpinner = new JSpinner(timeModel);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        gbc.gridx = 3;
        form.add(timeSpinner, gbc);

        // Dirección
        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Dirección:"), gbc);
        txtDireccion = new JTextField();
        gbc.gridx = 1; gbc.gridwidth = 3;
        form.add(txtDireccion, gbc);
        gbc.gridwidth = 1;

        // Tipo
        gbc.gridx = 0; gbc.gridy = 2;
        form.add(new JLabel("Tipo de emergencia:"), gbc);
        comboTipo = new JComboBox<>();
        gbc.gridx = 1; gbc.gridwidth = 3;
        form.add(comboTipo, gbc);
        gbc.gridwidth = 1;

        // Resultado
        gbc.gridx = 0; gbc.gridy = 3;
        form.add(new JLabel("Resultado:"), gbc);
        txtResultado = new JTextArea(2, 20);
        gbc.gridx = 1; gbc.gridwidth = 3;
        form.add(new JScrollPane(txtResultado), gbc);
        gbc.gridwidth = 1;

        panelPrincipal.add(form, BorderLayout.NORTH);

        // Panel de listas
        JPanel panelListas = new JPanel(new GridLayout(1, 2, 10, 10));

        panelPersonal = new JPanel();
        panelPersonal.setLayout(new BoxLayout(panelPersonal, BoxLayout.Y_AXIS));
        panelPersonal.setBorder(BorderFactory.createTitledBorder("Personal disponible"));
        panelListas.add(new JScrollPane(panelPersonal));

        panelVehiculos = new JPanel();
        panelVehiculos.setLayout(new BoxLayout(panelVehiculos, BoxLayout.Y_AXIS));
        panelVehiculos.setBorder(BorderFactory.createTitledBorder("Vehículos disponibles"));
        panelListas.add(new JScrollPane(panelVehiculos));

        panelPrincipal.add(panelListas, BorderLayout.CENTER);

        // Tabla
        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Fecha", "Hora", "Dirección", "Tipo", "Vehículos", "Personal", "Resultado"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tabla);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(90);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(70);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(160);
        tabla.getColumnModel().getColumn(6).setPreferredWidth(160);
        tabla.getColumnModel().getColumn(7).setPreferredWidth(750);
        tabla.setRowHeight(60);

        tabla.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JTextArea textArea = new JTextArea();
                textArea.setText(value != null ? value.toString() : "");
                textArea.setWrapStyleWord(true);
                textArea.setLineWrap(true);
                textArea.setOpaque(true);
                if (isSelected) {
                    textArea.setBackground(table.getSelectionBackground());
                    textArea.setForeground(table.getSelectionForeground());
                } else {
                    textArea.setBackground(table.getBackground());
                    textArea.setForeground(table.getForeground());
                }
                return textArea;
            }
        });

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnRefrescar = new JButton("Refrescar");
        btnVolver = new JButton("Volver");

        // --- SOLO CAMBIO VISUAL: estilizar botones (colores, fuente, sin foco) ---
        estiloBoton(btnAgregar, new Color(0, 123, 255));   // azul
        estiloBoton(btnEditar, new Color(40, 167, 69));    // verde
        estiloBoton(btnEliminar, new Color(220, 53, 69));  // rojo
        estiloBoton(btnRefrescar, new Color(255, 193, 7)); // naranja
        estiloBoton(btnVolver, new Color(108, 117, 125));  // gris

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRefrescar);
        panelBotones.add(btnVolver);

        add(panelPrincipal, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // 🔹 NUEVO: Listener para autocompletar campos al seleccionar una fila
        tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila != -1 && !e.getValueIsAdjusting()) {
                    cargarDatosDesdeFila(fila);
                }
            }
        });
    }

    // Método auxiliar para aplicar estilo a los botones (solo visual)
    private void estiloBoton(JButton boton, Color colorFondo) {
        if (boton == null) return;
        boton.setFocusPainted(false);
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // 🔹 Método auxiliar para llenar los campos desde la tabla
    private void cargarDatosDesdeFila(int fila) {
        try {
            // Fecha
            Object fechaObj = modeloTabla.getValueAt(fila, 1);
            if (fechaObj != null) {
                java.util.Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaObj.toString());
                // ✅ se fuerza el cast al modelo específico
                ((UtilDateModel) datePicker.getModel()).setValue(fecha);
            }

            // Hora
            Object horaObj = modeloTabla.getValueAt(fila, 2);
            if (horaObj != null) {
                java.util.Date hora = new SimpleDateFormat("HH:mm:ss").parse(horaObj.toString());
                timeSpinner.setValue(hora);
            }



            // Dirección
            txtDireccion.setText(modeloTabla.getValueAt(fila, 3).toString());

            // Tipo de emergencia
            String tipoNombre = modeloTabla.getValueAt(fila, 4).toString();
            for (int i = 0; i < comboTipo.getItemCount(); i++) {
                if (comboTipo.getItemAt(i).getNombre().equalsIgnoreCase(tipoNombre)) {
                    comboTipo.setSelectedIndex(i);
                    break;
                }
            }

            // Resultado
            txtResultado.setText(modeloTabla.getValueAt(fila, 7).toString());

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        String personalStr = modeloTabla.getValueAt(fila, 6).toString();
        for (JCheckBox c : checkBoxesPersonal) {
            c.setSelected(personalStr.contains(c.getText().split(" ")[0])); // marca si el nombre aparece
        }

        String vehiculoStr = modeloTabla.getValueAt(fila, 5).toString();
        for (JCheckBox c : checkBoxesVehiculos) {
            c.setSelected(vehiculoStr.contains(c.getText().split(" ")[0]));
        }

    }

    // ==== Métodos existentes ====
    public Date getFechaSeleccionada() {
        java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
        return (selectedDate != null) ? new Date(selectedDate.getTime()) : null;
    }

    public Time getHoraSeleccionada() {
        java.util.Date date = (java.util.Date) timeSpinner.getValue();
        return new Time(date.getTime());
    }

    public String getTxtDireccion() { return txtDireccion.getText().trim(); }
    public String getTxtResultado() { return txtResultado.getText().trim(); }
    public TipoEmergencia getTipoSeleccionado() { return (TipoEmergencia) comboTipo.getSelectedItem(); }

    public void setTiposEmergencia(List<TipoEmergencia> tipos) {
        comboTipo.removeAllItems();
        for (TipoEmergencia t : tipos) comboTipo.addItem(t);
    }

    public void setListaPersonal(List<Personal> lista) {
        personales = lista != null ? lista : new ArrayList<>();
        panelPersonal.removeAll();
        checkBoxesPersonal.clear();
        for (Personal p : personales) {
            JCheckBox check = new JCheckBox(p.getNombre() + " (" + p.getJerarquia().getNombre() + ")");
            checkBoxesPersonal.add(check);
            panelPersonal.add(check);
        }
        panelPersonal.revalidate();
        panelPersonal.repaint();
    }

    public void setListaVehiculos(List<Vehiculo> lista) {
        vehiculos = lista != null ? lista : new ArrayList<>();
        panelVehiculos.removeAll();
        checkBoxesVehiculos.clear();
        for (Vehiculo v : vehiculos) {
            JCheckBox check = new JCheckBox(v.getPatente() + " - " + v.getTipo() + " (" + v.getEstado() + ")");
            checkBoxesVehiculos.add(check);
            panelVehiculos.add(check);
        }
        panelVehiculos.revalidate();
        panelVehiculos.repaint();
    }

    public List<Personal> getPersonalSeleccionado() {
        List<Personal> seleccionados = new ArrayList<>();
        if (personales == null) return seleccionados;
        for (int i = 0; i < checkBoxesPersonal.size(); i++) {
            if (checkBoxesPersonal.get(i).isSelected()) {
                seleccionados.add(personales.get(i));
            }
        }
        return seleccionados;
    }

    public List<Vehiculo> getVehiculosSeleccionados() {
        List<Vehiculo> seleccionados = new ArrayList<>();
        if (vehiculos == null) return seleccionados;
        for (int i = 0; i < checkBoxesVehiculos.size(); i++) {
            if (checkBoxesVehiculos.get(i).isSelected()) {
                seleccionados.add(vehiculos.get(i));
            }
        }
        return seleccionados;
    }

    public void mostrarEmergencias(List<Emergencia> emergencias) {
        modeloTabla.setRowCount(0);
        for (Emergencia e : emergencias) {
            String personalStr = (e.getPersonales() != null && !e.getPersonales().isEmpty())
                    ? String.join(", ", e.getPersonales().stream().map(Personal::getNombre).toList())
                    : "-";

            String vehiculosStr = (e.getVehiculos() != null && !e.getVehiculos().isEmpty())
                    ? String.join(", ", e.getVehiculos().stream().map(Vehiculo::getPatente).toList())
                    : "-";

            modeloTabla.addRow(new Object[]{
                    e.getId(),
                    e.getFecha(),
                    e.getHora(),
                    e.getDireccion(),
                    e.getTipo().getNombre(),
                    vehiculosStr,
                    personalStr,
                    e.getResultado()
            });
        }
    }

    public void limpiarCampos() {
        txtDireccion.setText("");
        txtResultado.setText("");
        datePicker.getModel().setValue(null);
        timeSpinner.setValue(new java.util.Date());
        comboTipo.setSelectedIndex(-1);
        for (JCheckBox c : checkBoxesPersonal) c.setSelected(false);
        for (JCheckBox c : checkBoxesVehiculos) c.setSelected(false);
    }

    public int getIdSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) return -1;
        return Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
    }
    public JTable getTabla() { return tabla; }


    public JButton getBtnAgregar() { return btnAgregar; }
    public JButton getBtnEditar() { return btnEditar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnRefrescar() { return btnRefrescar; }
    public JButton getBtnVolver() { return btnVolver; }
}
