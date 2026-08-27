package util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Validaciones {

    // 🔹 Solo letras
    public static void soloLetras(JTextField campo) {
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                // Permitir teclas de control (Backspace, Delete, etc.)
                if (Character.isISOControl(c)) return;

                if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                    e.consume();
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null,
                            "Solo se permiten letras en este campo",
                            "Error de validación",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    // 🔹 Solo números
    public static void soloNumeros(JTextField campo) {
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                // Permitir teclas de control
                if (Character.isISOControl(c)) return;

                if (!Character.isDigit(c)) {
                    e.consume();
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null,
                            "Solo se permiten números en este campo",
                            "Error de validación",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    // 🔹 Solo letras y números (para patentes, códigos, etc.)
    public static void soloLetrasYNumeros(JTextField campo) {
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                // Permitir teclas de control
                if (Character.isISOControl(c)) return;

                if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) {
                    e.consume();
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null,
                            "Solo se permiten letras y números en este campo",
                            "Error de validación",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    // 🔹 Validar formato de patente argentina (viejo o nuevo)
    public static boolean validarPatente(String patente) {
        patente = patente.trim().toUpperCase();

        String formatoViejo = "^[A-Z]{3}\\s?[0-9]{3}$";     // ABC 123
        String formatoNuevo = "^[A-Z]{2}\\s?[0-9]{3}\\s?[A-Z]{2}$"; // AB 123 CD

        if (patente.matches(formatoViejo) || patente.matches(formatoNuevo)) {
            return true;
        } else {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,
                    "Formato de patente inválido.\nEjemplos válidos:\n- ABC 123\n- AB 123 CD",
                    "Error de validación",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    // 🔹 Validar campo vacío
    public static boolean campoVacio(JTextField campo, String nombreCampo) {
        if (campo.getText().trim().isEmpty()) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,
                    "El campo '" + nombreCampo + "' no puede estar vacío",
                    "Campo requerido",
                    JOptionPane.WARNING_MESSAGE);
            campo.requestFocus();
            return true;
        }
        return false;
    }
}
