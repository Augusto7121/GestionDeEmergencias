package vista;

import modelo.Jerarquia;
import modelo.Personal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import util.Validaciones;

public class PersonalVista extends JFrame {
    private JTextField txtNombre;
    private JComboBox<Jerarquia> comboJerarquia;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar, btnEditar, btnEliminar, btnRefrescar, btnVolver;

    public PersonalVista() {
        // 🔹 Aplicar Look & Feel Nimbus (solo una vez)
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    UIManager.put("control", new Color(245, 245, 245));
                    UIManager.put("nimbusBase", new Color(60, 80, 160));
                    UIManager.put("nimbusBlueGrey", new Color(200, 200, 210));
                    UIManager.put("nimbusFocus", new Color(90, 130, 210));
                    UIManager.put("text", new Color(30, 30, 30));
                    UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 14));
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ No se pudo aplicar Nimbus: " + e.getMessage());
        }

        setTitle("Gestión de Personal");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel form = new JPanel(new GridLayout(2, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Datos del Personal"));
        form.setBackground(new Color(245, 245, 245));

        form.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        form.add(txtNombre);
        Validaciones.soloLetras(txtNombre);

        form.add(new JLabel("Jerarquía:"));
        comboJerarquia = new JComboBox<>();
        form.add(comboJerarquia);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Jerarquía"}, 0);
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(24);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        // 🔹 Escucha de clic en la tabla para cargar los datos en los campos
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila != -1) {
                    txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                    String jerarquiaNombre = modeloTabla.getValueAt(fila, 2).toString();

                    for (int i = 0; i < comboJerarquia.getItemCount(); i++) {
                        Jerarquia j = comboJerarquia.getItemAt(i);
                        if (j.getNombre().equalsIgnoreCase(jerarquiaNombre)) {
                            comboJerarquia.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        });

        // 🔹 Botones con colores y estilo
        JPanel botones = new JPanel();
        botones.setBackground(new Color(245, 245, 245));

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

        setLayout(new BorderLayout(10, 10));
        add(form, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

    // 🔹 Método auxiliar para crear botones bonitos
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ---------------------- Getters y métodos originales ----------------------
    public String getTxtNombre() { return txtNombre.getText().trim(); }

    public Jerarquia getJerarquiaSeleccionada() { return (Jerarquia) comboJerarquia.getSelectedItem(); }

    public void setJerarquias(List<Jerarquia> lista) {
        comboJerarquia.removeAllItems();
        for (Jerarquia j : lista) comboJerarquia.addItem(j);
    }

    public void mostrarPersonal(List<Personal> lista) {
        modeloTabla.setRowCount(0);
        for (Personal p : lista)
            modeloTabla.addRow(new Object[]{
                    p.getId(),
                    p.getNombre(),
                    p.getJerarquia() != null ? p.getJerarquia().getNombre() : "-"
            });
    }

    public int getIdSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) return -1;
        return Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
    }

    public void limpiarCampos() {
        txtNombre.setText("");
        comboJerarquia.setSelectedIndex(-1);
    }

    public void mostrarPersonalSeleccionado(Personal p) {
        txtNombre.setText(p.getNombre());
        comboJerarquia.setSelectedItem(p.getJerarquia());
    }

    public JTable getTabla() { return tabla; }
    public JButton getBtnAgregar() { return btnAgregar; }
    public JButton getBtnEditar() { return btnEditar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnRefrescar() { return btnRefrescar; }
    public JButton getBtnVolver() { return btnVolver; }
}
