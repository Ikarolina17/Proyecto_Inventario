package mx.unison;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelProductos extends JPanel {
    private JTable tabla;
    private DefaultTableModel modelo;
    private Database database;
    private Usuario usuarioActual;

    public PanelProductos(Database db, Usuario user, Runnable logoutAction) {
        this.database = db;
        this.usuarioActual = user;
        setLayout(new BorderLayout(10, 10));

        // 1. Configuración de la Tabla
        String[] columnas = {"ID", "Nombre", "Precio", "Cantidad", "Descripción"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // 2. Panel Superior (Botones)
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addBtn = new JButton("Nuevo Producto");
        JButton deleteBtn = new JButton("Eliminar Seleccionado");

        boolean canEdit = user.rol.equals("ADMIN") || user.rol.equals("PRODUCTOS");
        addBtn.setVisible(canEdit);
        deleteBtn.setVisible(canEdit);

        panelBotones.add(addBtn);
        panelBotones.add(deleteBtn);
        add(panelBotones, BorderLayout.NORTH);

        // 3. Lógica del Botón Agregar (Pidiendo datos al usuario)
        addBtn.addActionListener(e -> {
            abrirFormularioRegistro();
        });

        // Lógica del Botón Eliminar
        deleteBtn.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila != -1) {
                int id = (int) modelo.getValueAt(fila, 0);
                database.deleteProducto(id);
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un producto de la tabla.");
            }
        });

        actualizarTabla();
    }

    private void abrirFormularioRegistro() {
        // Crear campos de texto
        JTextField campoNombre = new JTextField();
        JTextField campoPrecio = new JTextField();
        JTextField campoCantidad = new JTextField();
        JTextField campoDesc = new JTextField();

        // Crear el diseño del formulario emergente
        Object[] formulario = {
            "Nombre del Producto:", campoNombre,
            "Precio:", campoPrecio,
            "Cantidad:", campoCantidad,
            "Descripción:", campoDesc
        };

        int opcion = JOptionPane.showConfirmDialog(this, formulario, "Registrar Producto", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            try {
                // Validar y capturar datos
                Producto p = new Producto();
                p.nombre = campoNombre.getText();
                p.precio = Double.parseDouble(campoPrecio.getText());
                p.cantidad = Integer.parseInt(campoCantidad.getText());
                p.almacenId = 1; // Default para el ejemplo
                p.descripcion = campoDesc.getText();

                // Guardar en la "BD"
                database.insertProducto(p, usuarioActual.username);
                
                // Refrescar vista
                actualizarTabla();
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Precio y Cantidad deben ser números.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarTabla() {
        modelo.setRowCount(0);
        List<Producto> lista = database.listProductos("", 0, 999999);
        for (Producto p : lista) {
            Object[] fila = {p.id, p.nombre, p.precio, p.cantidad, p.descripcion};
            modelo.addRow(fila);
        }
    }
}