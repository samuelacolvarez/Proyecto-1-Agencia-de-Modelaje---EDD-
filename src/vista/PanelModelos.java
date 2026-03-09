package vista;

import logica.Agencia;
import modelo.Modelo;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelModelos extends JPanel {

    private Agencia agencia;

    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtIdentificacion;
    private JTextField txtContacto;
    private JTextField txtEstatura;
    private JComboBox<String> cbCategoria;
    private JCheckBox chkDisponible;

    private JTable tabla;
    private DefaultTableModel modeloTabla;


    // ─── Constructor ──────────────────────────────────────────────────────
    public PanelModelos(Agencia agencia) {
        this.agencia = agencia;

        setLayout(new BorderLayout());

        add(crearFormulario(), BorderLayout.NORTH);
        add(crearTabla(),      BorderLayout.CENTER);
        add(crearBotones(),    BorderLayout.SOUTH);

        actualizarTabla();
    }

    // ─── FORMULARIO ───────────────────────────────────────────────────────
    private JPanel crearFormulario() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 10, 5));

        panel.setBorder(BorderFactory.createTitledBorder("Registrar Modelo"));
        txtCodigo         = new JTextField();
        txtNombre         = new JTextField();
        txtIdentificacion = new JTextField();
        txtContacto       = new JTextField();
        txtEstatura       = new JTextField();

        cbCategoria = new JComboBox<>(new String[]{
                "Pasarela", "Comercial", "Fitness", "Editorial"
        });

        chkDisponible = new JCheckBox("Disponible");
        chkDisponible.setSelected(true); // empieza tildado por defecto

        panel.add(new JLabel("Código:"));
        panel.add(txtCodigo);

        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);

        panel.add(new JLabel("Identificación:"));
        panel.add(txtIdentificacion);

        panel.add(new JLabel("Contacto:"));
        panel.add(txtContacto);

        panel.add(new JLabel("Estatura:"));
        panel.add(txtEstatura);

        panel.add(new JLabel("Categoría:"));
        panel.add(cbCategoria);

        panel.add(new JLabel(""));  // celda vacía para alinear
        panel.add(chkDisponible);

        return panel;
    }

    // ─── TABLA ────────────────────────────────────────────────────────────
    private JScrollPane crearTabla() {

        // Definir los nombres de las columnas
        String[] columnas = {
                "Código", "Nombre", "Identificación",
                "Contacto", "Estatura", "Categoría", "Disponible"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // ninguna celda es editable directamente
            }
        };

        tabla = new JTable(modeloTabla);

        return new JScrollPane(tabla);
    }

    //BOTONES
    private JPanel crearBotones() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton btnRegistrar = new JButton("Registrar Modelo");
        JButton btnEliminar  = new JButton("Eliminar Modelo");
        JButton btnLimpiar   = new JButton("Limpiar Campos");

        btnRegistrar.addActionListener(e -> registrarModelo());
        btnEliminar.addActionListener(e  -> eliminarModelo());
        btnLimpiar.addActionListener(e   -> limpiarCampos());

        panel.add(btnRegistrar);
        panel.add(btnEliminar);
        panel.add(btnLimpiar);

        return panel;
    }

    // REGISTROS
    private void registrarModelo() {

        // Primero validar que los campos no estén vacíos
        if (txtCodigo.getText().trim().isEmpty() ||
                txtNombre.getText().trim().isEmpty()) {

            // JOptionPane muestra ventanas emergentes de mensaje
            JOptionPane.showMessageDialog(
                    this,                           // componente padre
                    "Código y nombre son obligatorios",  // mensaje
                    "Campo vacío",                  // título
                    JOptionPane.WARNING_MESSAGE     // ícono de advertencia
            );
            return;
        }

        float estatura;
        try {
            estatura = Float.parseFloat(txtEstatura.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "La estatura debe ser un número (ejemplo: 1.75)",
                    "Error de formato",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Crear el modelo con los datos del formulario
        Modelo nuevo = new Modelo(
                java.util.UUID.randomUUID(),
                txtNombre.getText().trim(),
                txtIdentificacion.getText().trim(),
                txtContacto.getText().trim(),
                estatura,
                cbCategoria.getSelectedItem().toString(),
                chkDisponible.isSelected() ? "Sí" : "No"
        );

        agencia.agregarModelo(nuevo);
        actualizarTabla();
        limpiarCampos();

        JOptionPane.showMessageDialog(
                this,
                "Modelo registrado correctamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ELIMINAR
    private void eliminarModelo() {

        // getSelectedRow() devuelve el índice de la fila seleccionada
        // Si no hay ninguna seleccionada devuelve -1
        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecciona un modelo de la tabla primero",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Obtener el código de la fila seleccionada
        String codigo = modeloTabla.getValueAt(filaSeleccionada, 0).toString();

        // Confirmar antes de eliminar
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar el modelo " + codigo + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            agencia.eliminarModelo(codigo);
            actualizarTabla();
            JOptionPane.showMessageDialog(this, "Modelo eliminado");
        }
    }

    // LIMPIAR CAMPOS
    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtIdentificacion.setText("");
        txtContacto.setText("");
        txtEstatura.setText("");
        cbCategoria.setSelectedIndex(0);
        chkDisponible.setSelected(true);
    }

    //─── ACTUALIZAR TABLA ─────────────────────────────────────────────────
    private void actualizarTabla() {

        modeloTabla.setRowCount(0);

        Modelo[] modelos = agencia.getModelos();
        int cantidad     = agencia.getCantidadModelos();

        for (int i = 0; i < cantidad; i++) {
            Modelo m = modelos[i];

            modeloTabla.addRow(new Object[]{
                    m.getCodigo(),
                    m.getNombre(),
                    m.getIdentificacion(),
                    m.getContacto(),
                    m.getEstatura(),
                    m.getCategoria(),
                    m.getDisponibilidad()
            });
        }
    }
}