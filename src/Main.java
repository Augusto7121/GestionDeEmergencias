import vista.MenuPrincipal;
import controlador.MenuPrincipalControlador;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            // 🔹 Personalización de colores y fuentes Nimbus
            UIManager.put("control", new java.awt.Color(245, 245, 245));          // fondo general
            UIManager.put("nimbusBase", new java.awt.Color(50, 80, 160));         // color principal (botones, selección)
            UIManager.put("nimbusBlueGrey", new java.awt.Color(190, 200, 210));   // bordes suaves
            UIManager.put("nimbusFocus", new java.awt.Color(80, 120, 200));       // foco
            UIManager.put("text", new java.awt.Color(30, 30, 30));                // color del texto
            UIManager.put("nimbusSelectionBackground", new java.awt.Color(60, 100, 180)); // selección en tablas/listas

            UIManager.put("defaultFont", new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        } catch (Exception e) {
            System.out.println("No se pudo aplicar Nimbus: " + e.getMessage());
        }


        SwingUtilities.invokeLater(() -> {
            MenuPrincipal menu = new MenuPrincipal();
            new MenuPrincipalControlador(menu);
            menu.setVisible(true);
        });
    }
}
