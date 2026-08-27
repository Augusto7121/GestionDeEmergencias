package vista;

import modelo.Jerarquia;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import util.Validaciones;

public class JerarquiaVista extends JFrame {
    private JTextField txtNombre;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar, btnEditar, btnEliminar, btnRefrescar, btnVolver;

    public JerarquiaVista() {
        setTitle("Gestión de Jerarquías");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel form = new JPanel(new GridLayout(1, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        form.setBackground(new Color(245, 245, 245));

        form.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        form.add(txtNombre);
        Validaciones.soloLetras(txtNombre);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0);
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila != -1)
                    txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
            }
        });

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botones.setBackground(new Color(230, 230, 250));

        btnAgregar = crearBoton("Agregar", new Color(0, 123, 255));   // azul
        btnEditar = crearBoton("Editar", new Color(40, 167, 69));    // verde
        btnEliminar = crearBoton("Eliminar", new Color(220, 53, 69));  // rojo
        btnRefrescar = crearBoton("Refrescar", new Color(255, 193, 7)); // naranja
        btnVolver = crearBoton("Volver", new Color(108, 117, 125));  // gris

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

    private JButton crearBoton(String texto, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return boton;
    }

    public String getTxtNombre() { return txtNombre.getText().trim(); }
    public void limpiarCampos() { txtNombre.setText(""); tabla.clearSelection(); }
    public int getIdSeleccionado() {
        int f = tabla.getSelectedRow();
        if (f == -1) return -1;
        return Integer.parseInt(modeloTabla.getValueAt(f, 0).toString());
    }
    public void mostrarJerarquias(List<Jerarquia> lista) {
        modeloTabla.setRowCount(0);
        for (Jerarquia j : lista)
            modeloTabla.addRow(new Object[]{j.getId(), j.getNombre()});
    }

    public JButton getBtnAgregar() { return btnAgregar; }
    public JButton getBtnEditar() { return btnEditar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnRefrescar() { return btnRefrescar; }
    public JButton getBtnVolver() { return btnVolver; }
}
