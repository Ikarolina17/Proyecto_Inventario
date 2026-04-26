package mx.unison;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();

        // Ejecutar en el hilo de la interfaz de usuario
        SwingUtilities.invokeLater(() -> {
            // 1. Crear campos para el diálogo de Login
            JTextField userField = new JTextField();
            JPasswordField passField = new JPasswordField();
            Object[] message = {
                "Usuario:", userField,
                "Contraseña:", passField
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Login de Sistema", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                // 2. Autenticar usando la lógica de MD5
                Usuario user = db.authenticate(username, password);

                if (user != null) {
                    abrirVentanaPrincipal(db, user);
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private static void abrirVentanaPrincipal(Database db, Usuario user) {
        JFrame frame = new JFrame("Sistema de Inventario - Usuario: " + user.username);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        PanelProductos panel = new PanelProductos(db, user, () -> {
            frame.dispose();
            main(null); // Reiniciar al cerrar sesión
        });

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}