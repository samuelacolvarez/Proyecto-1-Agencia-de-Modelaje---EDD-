package vista;

import excepciones.AlturaMinimaException;
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


    public PanelModelos(Agencia agencia) {
        this.agencia = agencia;

        setLayout(new BorderLayout());

        add(crearFormulario(), BorderLayout.NORTH);
        add(crearTabla(),      BorderLayout.CENTER);
        add(crearBotones(),    BorderLayout.SOUTH);

        actualizarTabla();
    }

    private JPanel crearFormulario() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 10, 5));

        panel.setBorder(BorderFactory.createTitledBorder("Registrar Modelo"));

        txtNombre         = new JTextField();
        txtContacto       = new JTextField();
        txtEstatura       = new JTextField();

        cbCategoria = new JComboBox<>(new String[]{
                "Pasarela", "Comercial", "Fitness", "Editorial"
        });

        chkDisponible = new JCheckBox("Disponible");
        chkDisponible.setSelected(true);


        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);

        panel.add(new JLabel("Contacto:"));
        panel.add(txtContacto);

        panel.add(new JLabel("Estatura:"));
        panel.add(txtEstatura);

        panel.add(new JLabel("Categoría:"));
        panel.add(cbCategoria);

        panel.add(new JLabel(""));
        panel.add(chkDisponible);

        return panel;
    }


    private JScrollPane crearTabla() {

        String[] columnas = {
                "Código", "Nombre", "Identificación",
                "Contacto", "Estatura", "Categoría", "Disponible"
        };


        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);

        return new JScrollPane(tabla);
    }

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

    private void registrarModelo() {

        if (txtNombre.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "el nombre es obligatorio",
                    "Campo vacío",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Double estatura;
        try {
            estatura = Double.parseDouble(txtEstatura.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "La estatura debe ser un número (ejemplo: 1.85)",
                    "Error de formato",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            Modelo nuevo = new Modelo(
                    java.util.UUID.randomUUID(),
                    txtNombre.getText().trim(),
                    txtContacto.getText().trim(),
                    java.util.UUID.randomUUID().toString(),
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
        }catch (AlturaMinimaException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "La estatura debe ser un número mayor o igual a 1.8",
                    "Error de formato",
                    JOptionPane.ERROR_MESSAGE
            );
        }



    }


    private void eliminarModelo() {



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


        String codigo = modeloTabla.getValueAt(filaSeleccionada, 0).toString();


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


    private void limpiarCampos() {
        txtNombre.setText("");
        txtContacto.setText("");
        txtEstatura.setText("");
        cbCategoria.setSelectedIndex(0);
        chkDisponible.setSelected(true);
    }


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