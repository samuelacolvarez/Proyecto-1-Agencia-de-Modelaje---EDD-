package vista;

import logica.Agencia;
import logica.GestorArchivos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaPrincipal extends JFrame {

    // JFrame es la ventana en sí. Al heredar de ella, la clase VentanaPrincipal ES una ventana.

    private Agencia agencia;

    public VentanaPrincipal() {

        agencia = new Agencia();

        setTitle("Agencia No Más Enanos Por Favor");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            // windowClosing se ejecuta justo cuando el usuario presiona la X
            @Override
            public void windowClosing(WindowEvent e) {

                // Antes de cerrar, guardamos todo en los archivos .txt
                GestorArchivos.guardarModelos(
                        agencia.getModelos(),
                        agencia.getCantidadModelos()
                );
                GestorArchivos.guardarFotografos(
                        agencia.getFotografos(),
                        agencia.getCantidadFotografos()
                );
                GestorArchivos.guardarLugares(
                        agencia.getLugares(),
                        agencia.getCantidadLugares()
                );

                // Mostramos un mensaje avisando que se guardó
                JOptionPane.showMessageDialog(
                        VentanaPrincipal.this,   // ventana padre del mensaje
                        "Datos guardados correctamente.",
                        "Guardado",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // cerramos el programa completamente
                System.exit(0);
            }
        });

        setLocationRelativeTo(null);

        JTabbedPane pestanas = new JTabbedPane();

        pestanas.addTab("Modelos",     new PanelModelos(agencia));
        pestanas.addTab("Fotógrafos",  new PanelFotografos(agencia));
        pestanas.addTab("Lugares",     new PanelLugares(agencia));
        pestanas.addTab("Eventos",     new PanelEventos(agencia));

        add(pestanas);

        setVisible(true);
    }
}