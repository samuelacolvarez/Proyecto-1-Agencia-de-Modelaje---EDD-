package vista;

import logica.Agencia;
import modelo.*;
import modelo.evento.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelEventos extends JPanel {

    private Agencia agencia;

    // Campos comunes a todo evento
    private JTextField txtNombre;
    private JTextField txtFecha;
    private JComboBox<String> cbLugar;
    private JComboBox<String> cbTipoEvento;

    // Campos solo para Evento Público
    private JTextField txtCapacidad;
    private JTextField txtPatrocinador;

    // Campos solo para Evento Privado
    private JTextField txtCliente;
    private JComboBox<String> cbConfidencialidad;

    private JPanel panelDinamico;
    private CardLayout cardLayout;

    // Listas para asignar modelos y fotógrafos
    private JList<String> listaModelosDisponibles;
    private JList<String> listaFotografosDisponibles;

    // Tabla de eventos
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    // ─── Constructor ──────────────────────────────────────────────────────
    public PanelEventos(Agencia agencia) {
        this.agencia = agencia;
        setLayout(new BorderLayout());

        // Panel superior
        JPanel panelSuperior = new JPanel(new GridLayout(1, 2, 10, 0));
        panelSuperior.add(crearFormulario());
        panelSuperior.add(crearPanelAsignacion());

        add(panelSuperior,  BorderLayout.NORTH);
        add(crearTabla(),   BorderLayout.CENTER);
        add(crearBotones(), BorderLayout.SOUTH);

        actualizarTabla();
        actualizarCombos();
    }

    //  FORMULARIO PRINCIPAL
    private JPanel crearFormulario() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Evento"));

        JPanel comunes = new JPanel(new GridLayout(0, 2, 10, 5));

        txtNombre = new JTextField();
        txtFecha  = new JTextField();
        txtFecha.setToolTipText("Formato: DD/MM/AAAA");

        cbLugar = new JComboBox<>();

        cbTipoEvento = new JComboBox<>(new String[]{"Público", "Privado"});
        cbTipoEvento.addActionListener(e -> cambiarTipoEvento());

        comunes.add(new JLabel("Nombre evento:")); comunes.add(txtNombre);
        comunes.add(new JLabel("Fecha:"));         comunes.add(txtFecha);
        comunes.add(new JLabel("Lugar:"));         comunes.add(cbLugar);
        comunes.add(new JLabel("Tipo:"));          comunes.add(cbTipoEvento);

        cardLayout    = new CardLayout();
        panelDinamico = new JPanel(cardLayout);
        panelDinamico.add(crearPanelPublico(),  "Público");
        panelDinamico.add(crearPanelPrivado(),  "Privado");

        panel.add(comunes,       BorderLayout.NORTH);
        panel.add(panelDinamico, BorderLayout.CENTER);

        return panel;
    }

    //PANEL DATOS EVENTO PÚBLICO
    private JPanel crearPanelPublico() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Datos Evento Público"));

        txtCapacidad    = new JTextField();
        txtPatrocinador = new JTextField();

        panel.add(new JLabel("Capacidad modelos/fotografos:")); panel.add(txtCapacidad);
        panel.add(new JLabel("Patrocinador:"));         panel.add(txtPatrocinador);

        return panel;
    }

    // PANEL DATOS EVENTO PRIVADO
    private JPanel crearPanelPrivado() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Datos Evento Privado"));

        txtCliente          = new JTextField();
        cbConfidencialidad  = new JComboBox<>(new String[]{
                "Bajo", "Medio", "Alto", "Máximo"
        });

        panel.add(new JLabel("Cliente:"));              panel.add(txtCliente);
        panel.add(new JLabel("Confidencialidad:"));     panel.add(cbConfidencialidad);

        return panel;
    }

    // PANEL DE ASIGNACIÓN
    private JPanel crearPanelAsignacion() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Asignar al Evento"));

        // Lista de modelos disponibles
        JPanel panelModelos = new JPanel(new BorderLayout());
        panelModelos.setBorder(BorderFactory.createTitledBorder("Modelos disponibles"));

        listaModelosDisponibles = new JList<>();
        listaModelosDisponibles.setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
        );

        panelModelos.add(new JScrollPane(listaModelosDisponibles));

        // Lista de fotógrafos disponibles
        JPanel panelFotos = new JPanel(new BorderLayout());
        panelFotos.setBorder(BorderFactory.createTitledBorder("Fotógrafos disponibles"));

        listaFotografosDisponibles = new JList<>();
        listaFotografosDisponibles.setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
        );

        panelFotos.add(new JScrollPane(listaFotografosDisponibles));

        panel.add(panelModelos);
        panel.add(panelFotos);

        return panel;
    }

    // TABLA
    private JScrollPane crearTabla() {
        String[] columnas = {
                "Nombre", "Fecha", "Lugar", "Tipo", "Detalle"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tabla = new JTable(modeloTabla);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(200);

        return new JScrollPane(tabla);
    }

    // BOTONES
    private JPanel crearBotones() {
        JPanel panel = new JPanel(new FlowLayout());

        JButton btnCrear    = new JButton("Crear Evento");
        JButton btnLimpiar  = new JButton("Limpiar Campos");
        JButton btnRefresh  = new JButton("Refrescar listas");

        btnCrear.addActionListener(e    -> crearEvento());
        btnLimpiar.addActionListener(e  -> limpiarCampos());
        btnRefresh.addActionListener(e  -> actualizarCombos());

        panel.add(btnCrear);
        panel.add(btnLimpiar);
        panel.add(btnRefresh);

        return panel;
    }

    //CAMBIAR TIPO DE EVENTO
    private void cambiarTipoEvento() {
        String tipo = cbTipoEvento.getSelectedItem().toString();
        cardLayout.show(panelDinamico, tipo);
    }

    // CREAR EVENTO ─────────────────────────────────────────────
    private void crearEvento() {

        if (txtNombre.getText().trim().isEmpty() ||
                txtFecha.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nombre y fecha son obligatorios",
                    "Campo vacío",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (cbLugar.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Debes registrar al menos un lugar primero",
                    "Sin lugares",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el lugar seleccionado
        String nombreLugar = cbLugar.getSelectedItem().toString();
        Lugar lugar = agencia.buscarLugar(nombreLugar);

        String tipo = cbTipoEvento.getSelectedItem().toString();
        Evento evento;

        int capacidad = 20;
        if (tipo.equals("Público")) {

            // Validar campos de evento público
            if (txtCapacidad.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "La capacidad es obligatoria para eventos públicos",
                        "Campo vacío", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                capacidad = Integer.parseInt(txtCapacidad.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "La capacidad debe ser un número",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(capacidad <= 0 || capacidad > 100) {
                JOptionPane.showMessageDialog(this,
                        "La capacidad debe estar entre 0 e 100",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            evento = new EventoPublico(
                    txtNombre.getText().trim(),
                    parsearFecha(txtFecha.getText().trim()),  // String → Date
                    lugar.getNombre(),
                    capacidad,
                    txtPatrocinador.getText().trim()
            );
        } else {

            // Validar campos de evento privado
            if (txtCliente.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "El cliente es obligatorio para eventos privados",
                        "Campo vacío", JOptionPane.WARNING_MESSAGE);
                return;
            }

            evento = new EventoPrivado(
                    txtNombre.getText().trim(),
                    parsearFecha(txtFecha.getText().trim()),
                    lugar.getNombre(),
                    20,
                    txtCliente.getText().trim(),
                    cbConfidencialidad.getSelectedItem().toString()
            );
        }
        if ( parsearFecha(txtFecha.getText().trim())==null) return;

        // Asignar modelos seleccionados en la lista
        int[] indicesModelos = listaModelosDisponibles.getSelectedIndices();
        for (int i = 0; i < indicesModelos.length; i++) {
            Modelo m = agencia.getModelos()[indicesModelos[i]];
            evento.agregarModelos(m);
            if(!evento.agregarModelos(m)){
                JOptionPane.showMessageDialog(this,
                        "La cantidad de modelos es mayor a la capacidad del evento",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Asignar fotógrafos seleccionados en la lista
        int[] indicesFotos = listaFotografosDisponibles.getSelectedIndices();
        for (int i = 0; i < indicesFotos.length; i++) {
            Fotografo f = agencia.getFotografos()[indicesFotos[i]];
            evento.agregarFotografos(f);
            if(!evento.agregarFotografos(f)){
                JOptionPane.showMessageDialog(this,
                        "La cantidad de fotografos es mayor a la capacidad del evento",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        agencia.agregarEvento(evento);
        actualizarTabla();
        limpiarCampos();

        JOptionPane.showMessageDialog(this,
                "Evento creado correctamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
    }



    // LIMPIAR
    private void limpiarCampos() {
        txtNombre.setText("");
        txtFecha.setText("");
        txtCapacidad.setText("");
        txtPatrocinador.setText("");
        txtCliente.setText("");
        cbTipoEvento.setSelectedIndex(0);
        cbConfidencialidad.setSelectedIndex(0);
        listaModelosDisponibles.clearSelection();
        listaFotografosDisponibles.clearSelection();
        cardLayout.show(panelDinamico, "Público");
    }

    // ACTUALIZAR COMBOS Y LISTAS
    public void actualizarCombos() {

        // Actualizar combo de lugares
        cbLugar.removeAllItems();
        Lugar[] lugares  = agencia.getLugares();
        int cantLugares  = agencia.getCantidadLugares();
        for (int i = 0; i < cantLugares; i++) {
            cbLugar.addItem(lugares[i].getNombre());
        }

        DefaultListModel<String> modelosModel = new DefaultListModel<>();
        Modelo[] modelos  = agencia.getModelos();
        int cantModelos   = agencia.getCantidadModelos();
        for (int i = 0; i < cantModelos; i++) {
            modelosModel.addElement(
                    modelos[i].getCodigo() + " - " + modelos[i].getNombre()
            );
        }
        listaModelosDisponibles.setModel(modelosModel);

        // Actualizar lista de fotógrafos
        DefaultListModel<String> fotosModel = new DefaultListModel<>();
        Fotografo[] fotografos = agencia.getFotografos();
        int cantFotos          = agencia.getCantidadFotografos();
        for (int i = 0; i < cantFotos; i++) {
            fotosModel.addElement(fotografos[i].getNombre());
        }
        listaFotografosDisponibles.setModel(fotosModel);
    }

    // ACTUALIZAR TABLA
    private void actualizarTabla() {
        modeloTabla.setRowCount(0);

        Evento[] eventos = agencia.getEventos();
        int cantidad     = agencia.getCantidadEventos();

        for (int i = 0; i < cantidad; i++) {
            Evento ev = eventos[i];
            modeloTabla.addRow(new Object[]{
                    ev.getNombre(),
                    ev.getFecha().toString(),
                    ev.getLugar(),
                    ev.tipoEvento(),
                    ev.tipoEvento()
            });
        }
    }

    private java.util.Date parsearFecha(String texto) {
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            return sdf.parse(texto);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Formato de fecha incorrecto. Usa DD/MM/AAAA",
                    "Error de fecha",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }
}
