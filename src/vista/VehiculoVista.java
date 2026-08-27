package vista;

import modelo.Vehiculo;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import util.Validaciones;

public class VehiculoVista extends JFrame {
    private JTextField txtPatente, txtTipo, txtEstado;
    private JButton btnAgregar, btnEditar, btnEliminar, btnRefrescar, btnVolver;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public VehiculoVista() {
        setTitle("Gestión de Vehículos");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(245, 245, 245));

        JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        form.setBackground(new Color(245, 245, 245));

        form.add(new JLabel("Patente:"));
        txtPatente = new JTextField();
        form.add(txtPatente);
        Validaciones.soloLetrasYNumeros(txtPatente);


        form.add(new JLabel("Nombre:"));
        txtTipo = new JTextField();
        form.add(txtTipo);
        Validaciones.soloLetrasYNumeros(txtTipo);

        form.add(new JLabel("Estado:"));
        txtEstado = new JTextField();
        form.add(txtEstado);
        Validaciones.soloLetras(txtEstado);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Patente", "Nombre", "Estado"}, 0);
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila != -1 && !e.getValueIsAdjusting()) {
                    cargarDatosDesdeFila(fila);
                }
            }
        });

        JPanel botones = new JPanel();
        botones.setBackground(new Color(245, 245, 245));

        btnAgregar = crearBoton("Agregar", new Color(0, 123, 255));
        btnEditar = crearBoton("Editar", new Color(40, 167, 69));
        btnEliminar = crearBoton("Eliminar", new Color(220, 53, 69));
        btnRefrescar = crearBoton("Refrescar", new Color(255, 193, 7));
        btnVolver = crearBoton("Volver", new Color(108, 117, 125));

        botones.add(btnAgregar);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnRefrescar);
        botones.add(btnVolver);

        setLayout(new BorderLayout());
        add(form, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return boton;
    }

    private void cargarDatosDesdeFila(int fila) {
        txtPatente.setText(modeloTabla.getValueAt(fila, 1).toString());
        txtTipo.setText(modeloTabla.getValueAt(fila, 2).toString());
        txtEstado.setText(modeloTabla.getValueAt(fila, 3).toString());
    }

    public String getTxtPatente() { return txtPatente.getText().trim(); }
    public String getTxtTipo() { return txtTipo.getText().trim(); }
    public String getTxtEstado() { return txtEstado.getText().trim(); }

    public JButton getBtnAgregar() { return btnAgregar; }
    public JButton getBtnEditar() { return btnEditar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnRefrescar() { return btnRefrescar; }
    public JButton getBtnVolver() { return btnVolver; }

    public int getIdSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) return -1;
        return Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
    }

    public void mostrarVehiculos(List<Vehiculo> lista) {
        modeloTabla.setRowCount(0);
        for (Vehiculo v : lista)
            modeloTabla.addRow(new Object[]{v.getId(), v.getPatente(), v.getTipo(), v.getEstado()});
    }

    public void limpiarCampos() {
        txtPatente.setText("");
        txtTipo.setText("");
        txtEstado.setText("");
        tabla.clearSelection();
    }
}
