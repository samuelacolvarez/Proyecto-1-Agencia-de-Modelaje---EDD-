package vista;

import excepciones.AlturaMinimaException;
import logica.Agencia;
import modelo.Modelo;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelModelos extends JPanel {

    // ─── Referencias a la agencia y componentes ───────────────────────────
    private Agencia agencia;

    // Campos del formulario
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtIdentificacion;
    private JTextField txtContacto;
    private JTextField txtEstatura;
    private JComboBox<String> cbCategoria;
    private JCheckBox chkDisponible;

    // Tabla para mostrar los modelos
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    // DefaultTableModel es el "contenido" de la tabla.
    // JTable es la tabla visual. Son dos cosas separadas:
    // una maneja los datos, la otra los muestra.

    // ─── Constructor ──────────────────────────────────────────────────────
    public PanelModelos(Agencia agencia) {
        this.agencia = agencia;

        // BorderLayout divide el panel en 5 zonas:
        // NORTH, SOUTH, EAST, WEST, CENTER
        setLayout(new BorderLayout());

        // Construir y agregar cada sección
        add(crearFormulario(), BorderLayout.NORTH);
        add(crearTabla(),      BorderLayout.CENTER);
        add(crearBotones(),    BorderLayout.SOUTH);

        // Cargar los modelos que ya existen en la tabla
        actualizarTabla();
    }

    // ─── FORMULARIO ───────────────────────────────────────────────────────
    private JPanel crearFormulario() {

        JPanel panel = new JPanel();
        // GridLayout(filas, columnas) organiza los componentes en cuadrícula
        // 0 en filas significa "las que sean necesarias"
        panel.setLayout(new GridLayout(0, 2, 10, 5));
        // 10 y 5 son los espacios horizontal y vertical entre celdas

        panel.setBorder(BorderFactory.createTitledBorder("Registrar Modelo"));
        // Esto crea un borde con título alrededor del panel

        // Crear campos y etiquetas
        // Cada JLabel es el texto descriptivo
        // Cada JTextField es el cajita donde el usuario escribe
        txtCodigo         = new JTextField();
        txtNombre         = new JTextField();
        txtIdentificacion = new JTextField();
        txtContacto       = new JTextField();
        txtEstatura       = new JTextField();

        // JComboBox es un menú desplegable
        cbCategoria = new JComboBox<>(new String[]{
                "Pasarela", "Comercial", "Fitness", "Editorial"
        });

        // JCheckBox es una casilla de verificación (tildable)
        chkDisponible = new JCheckBox("Disponible");
        chkDisponible.setSelected(true); // empieza tildado por defecto

        // Agregar al panel: primero la etiqueta, luego el campo
        // GridLayout los organiza de izquierda a derecha, arriba a abajo
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

        // DefaultTableModel maneja los datos de la tabla
        // false al final significa que el usuario NO puede editar las celdas
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // ninguna celda es editable directamente
            }
        };

        tabla = new JTable(modeloTabla);
        // JTable recibe el modelo de datos y lo muestra visualmente

        // JScrollPane agrega barras de desplazamiento a la tabla
        // Sin esto, si hay muchos registros no podrías verlos todos
        return new JScrollPane(tabla);
    }

    // ─── BOTONES ──────────────────────────────────────────────────────────
    private JPanel crearBotones() {

        JPanel panel = new JPanel();
        // FlowLayout acomoda los componentes uno al lado del otro
        panel.setLayout(new FlowLayout());

        JButton btnRegistrar = new JButton("Registrar Modelo");
        JButton btnEliminar  = new JButton("Eliminar Modelo");
        JButton btnLimpiar   = new JButton("Limpiar Campos");

        // ActionListener es lo que pasa cuando presionas el botón
        // La flecha -> es una forma corta de escribir el listener
        btnRegistrar.addActionListener(e -> registrarModelo());
        btnEliminar.addActionListener(e  -> eliminarModelo());
        btnLimpiar.addActionListener(e   -> limpiarCampos());

        panel.add(btnRegistrar);
        panel.add(btnEliminar);
        panel.add(btnLimpiar);

        return panel;
    }

    // ─── ACCIÓN: REGISTRAR ────────────────────────────────────────────────
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
            return; // corta la ejecución si falta algo
        }

        // Validar que la estatura sea un número
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
            // Crear el modelo con los datos del formulario
            Modelo nuevo = new Modelo(
                    java.util.UUID.randomUUID(),
                    txtNombre.getText().trim(),
                    txtIdentificacion.getText().trim(),
                    txtContacto.getText().trim(),
                    estatura,
                    cbCategoria.getSelectedItem().toString(),
                    chkDisponible.isSelected() ? "Sí" : "No"
                    // si el checkbox está tildado guarda "Sí", si no "No"
            );
            agencia.agregarModelo(nuevo);  // agrega a la agencia y guarda en archivo
            actualizarTabla();             // refresca la tabla visual
            limpiarCampos();                // limpia el formulario
            JOptionPane.showMessageDialog(
                    this,
                    "Modelo registrado correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }catch (AlturaMinimaException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "La estatura debe ser un número mayor o igual a 1.85",
                    "Error de formato",
                    JOptionPane.ERROR_MESSAGE
            );
        }



    }

    // ─── ACCIÓN: ELIMINAR ─────────────────────────────────────────────────
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

        // Obtener el código de la fila seleccionada (columna 0)
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

    // ─── ACCIÓN: LIMPIAR CAMPOS ───────────────────────────────────────────
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

        // Primero borra todas las filas actuales de la tabla
        modeloTabla.setRowCount(0);

        // Luego vuelve a llenarla con los datos actuales de la agencia
        Modelo[] modelos = agencia.getModelos();
        int cantidad     = agencia.getCantidadModelos();

        for (int i = 0; i < cantidad; i++) {
            Modelo m = modelos[i];

            // addRow agrega una fila con un arreglo de objetos
            // el orden debe coincidir con el orden de las columnas
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