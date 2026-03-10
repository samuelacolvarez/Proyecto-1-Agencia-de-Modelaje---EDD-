package vista;

import logica.Agencia;
import modelo.Lugar;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelLugares extends JPanel {

    private Agencia agencia;

    // Campos del formulario
    private JTextField txtNombre;
    private JTextField txtDireccion;
    private JTextField txtCiudad;
    private JTextField txtCapacidad;
    private JComboBox<String> cbTipoLugar;

    // Tabla
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    // ─── Constructor ──────────────────────────────────────────────────────
    public PanelLugares(Agencia agencia) {
        this.agencia = agencia;
        setLayout(new BorderLayout());

        add(crearFormulario(), BorderLayout.NORTH);
        add(crearTabla(),      BorderLayout.CENTER);
        add(crearBotones(),    BorderLayout.SOUTH);

        actualizarTabla();
    }

    // ─── FORMULARIO ───────────────────────────────────────────────────────
    private JPanel crearFormulario() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Registrar Lugar"));

        txtNombre    = new JTextField();
        txtDireccion = new JTextField();
        txtCiudad    = new JTextField();
        txtCapacidad = new JTextField();

        // Opciones del tipo de lugar según el enunciado
        cbTipoLugar = new JComboBox<>(new String[]{
                "Hotel", "Estudio", "Pasarela", "Salón", "Auditorio", "Otro"
        });

        panel.add(new JLabel("Nombre:"));     panel.add(txtNombre);
        panel.add(new JLabel("Dirección:"));  panel.add(txtDireccion);
        panel.add(new JLabel("Ciudad:"));     panel.add(txtCiudad);
        panel.add(new JLabel("Capacidad:"));  panel.add(txtCapacidad);
        panel.add(new JLabel("Tipo:"));       panel.add(cbTipoLugar);

        return panel;
    }

    // ─── TABLA ────────────────────────────────────────────────────────────
    private JScrollPane crearTabla() {
        String[] columnas = {
                "Nombre", "Dirección", "Ciudad", "Capacidad", "Tipo"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tabla = new JTable(modeloTabla);

        // Ajusta el ancho de las columnas para que se vean mejor
        tabla.getColumnModel().getColumn(0).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(100);

        return new JScrollPane(tabla);
    }

    // ─── BOTONES ──────────────────────────────────────────────────────────
    private JPanel crearBotones() {
        JPanel panel = new JPanel(new FlowLayout());

        JButton btnRegistrar = new JButton("Registrar Lugar");
        JButton btnLimpiar   = new JButton("Limpiar Campos");

        btnRegistrar.addActionListener(e -> registrarLugar());
        btnLimpiar.addActionListener(e   -> limpiarCampos());

        panel.add(btnRegistrar);
        panel.add(btnLimpiar);

        return panel;
    }

    // ─── ACCIÓN: REGISTRAR ────────────────────────────────────────────────
    private void registrarLugar() {

        // Validar campos obligatorios
        if (txtNombre.getText().trim().isEmpty() ||
                txtCiudad.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nombre y ciudad son obligatorios",
                    "Campo vacío",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar que capacidad sea número entero
        int capacidad;
        try {
            capacidad = Integer.parseInt(txtCapacidad.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "La capacidad debe ser un número entero (ejemplo: 200)",
                    "Error de formato",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que la capacidad sea positiva
        if (capacidad <= 0) {
            JOptionPane.showMessageDialog(this,
                    "La capacidad debe ser mayor a cero",
                    "Valor inválido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Verificar que no exista un lugar con el mismo nombre
        if (agencia.buscarLugar(txtNombre.getText().trim()) != null) {
            JOptionPane.showMessageDialog(this,
                    "Ya existe un lugar con ese nombre",
                    "Duplicado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Crear y registrar el lugar
        Lugar nuevo = new Lugar(
                txtNombre.getText().trim(),
                txtDireccion.getText().trim(),
                txtCiudad.getText().trim(),
                capacidad,
                cbTipoLugar.getSelectedItem().toString()
        );

        agencia.agregarLugar(nuevo);
        actualizarTabla();
        limpiarCampos();

        JOptionPane.showMessageDialog(this,
                "Lugar registrado correctamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
    }


    // LIMPIAR
    private void limpiarCampos() {
        txtNombre.setText("");
        txtDireccion.setText("");
        txtCiudad.setText("");
        txtCapacidad.setText("");
        cbTipoLugar.setSelectedIndex(0);
    }

    //ACTUALIZAR TABLA
    private void actualizarTabla() {
        modeloTabla.setRowCount(0);

        Lugar[] lugares  = agencia.getLugares();
        int cantidad     = agencia.getCantidadLugares();

        for (int i = 0; i < cantidad; i++) {
            Lugar l = lugares[i];
            modeloTabla.addRow(new Object[]{
                    l.getNombre(),
                    l.getDireccion(),
                    l.getCiudad(),
                    l.getCapacidad(),
                    l.getTipo()
            });
        }
    }
}