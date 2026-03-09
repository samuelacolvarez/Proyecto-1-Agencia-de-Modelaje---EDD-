package vista;

import logica.Agencia;
import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    // JFrame es la ventana en sí. Al heredar de ella, la clase VentanaPrincipal ES una ventana.

    private Agencia agencia;

    public VentanaPrincipal() {

        agencia = new Agencia();

        setTitle("Agencia No Más Enanos Por Favor");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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