package vista;

import logica.Agencia;
import modelo.Fotografo;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelFotografos extends JPanel {

    private Agencia agencia;
    private JTextField txtNombre;
    private JTextField txtIdentificacion;
    private JTextField txtContacto;
    private JTextField txtEspecialidad;
    private JTextField txtAnios;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public PanelFotografos(Agencia agencia) {
        this.agencia = agencia;
        setLayout(new BorderLayout());
        add(crearFormulario(), BorderLayout.NORTH);
        add(crearTabla(),      BorderLayout.CENTER);
        add(crearBotones(),    BorderLayout.SOUTH);
        actualizarTabla();
    }

    private JPanel crearFormulario() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Registrar Fotógrafo"));

        txtNombre         = new JTextField();
        txtContacto       = new JTextField();
        txtEspecialidad   = new JTextField();
        txtAnios          = new JTextField();

        panel.add(new JLabel("Nombre:"));         panel.add(txtNombre);
        panel.add(new JLabel("Contacto:"));       panel.add(txtContacto);
        panel.add(new JLabel("Especialidad:"));   panel.add(txtEspecialidad);
        panel.add(new JLabel("Años exp:"));       panel.add(txtAnios);

        return panel;
    }

    private JScrollPane crearTabla() {
        String[] columnas = {"Nombre", "Identificación", "Contacto", "Especialidad", "Años"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        return new JScrollPane(tabla);
    }

    private JPanel crearBotones() {
        JPanel panel = new JPanel(new FlowLayout());
        JButton btnRegistrar = new JButton("Registrar Fotógrafo");
        JButton btnEliminar  = new JButton("Eliminar Fotógrafo");
        btnRegistrar.addActionListener(e -> registrarFotografo());
        btnEliminar.addActionListener(e  -> eliminarFotografo());
        panel.add(btnRegistrar);
        panel.add(btnEliminar);
        return panel;
    }

    private void registrarFotografo() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio",
                    "Campo vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int anios;
        try {
            anios = Integer.parseInt(txtAnios.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Los años deben ser un número",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(anios<0){
            JOptionPane.showMessageDialog(this, "Los años deben ser un número mayor o igual a 0",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Fotografo f = new Fotografo(
                java.util.UUID.randomUUID(),
                txtNombre.getText().trim(),
                txtContacto.getText().trim(),
                txtEspecialidad.getText().trim(),
                anios
        );
        agencia.agregarFotografo(f);
        actualizarTabla();
        txtNombre.setText("");
        txtContacto.setText(""); txtEspecialidad.setText("");
        txtAnios.setText("");
        JOptionPane.showMessageDialog(this, "Fotógrafo registrado");
    }

    private void eliminarFotografo() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un fotógrafo primero",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String identificacion = modeloTabla.getValueAt(fila, 1).toString();
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Eliminar a " + identificacion + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            agencia.eliminarFotografo(identificacion);
            actualizarTabla();
            JOptionPane.showMessageDialog(this, "Fotografo eliminado");
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        Fotografo[] fotografos = agencia.getFotografos();
        int cantidad = agencia.getCantidadFotografos();
        for (int i = 0; i < cantidad; i++) {
            Fotografo f = fotografos[i];
            modeloTabla.addRow(new Object[]{
                    f.getNombre(), f.getIdentificacion(),
                    f.getContacto(), f.getEspecialidad(), f.getAniosExperiencia()
            });
        }
    }
}
