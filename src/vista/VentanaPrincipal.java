package vista;

import logica.Agencia;
import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    // JFrame es la ventana en sí. Al heredar de ella,
    // tu clase VentanaPrincipal ES una ventana.

    private Agencia agencia;

    public VentanaPrincipal() {

        // 1. Crear la agencia (esto carga los archivos automáticamente)
        agencia = new Agencia();

        // 2. Configurar la ventana
        setTitle("Agencia No Más Enanos Por Favor");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // EXIT_ON_CLOSE significa: cuando cierren la ventana, termina el programa

        setLocationRelativeTo(null);
        // null hace que la ventana aparezca en el centro de la pantalla

        // 3. Crear el panel de pestañas
        JTabbedPane pestanas = new JTabbedPane();
        // JTabbedPane es el componente de pestañas, como las del navegador

        // 4. Agregar cada panel como una pestaña
        // Le pasas la agencia a cada panel para que puedan usarla
        pestanas.addTab("Modelos",     new PanelModelos(agencia));
        pestanas.addTab("Fotógrafos",  new PanelFotografos(agencia));
        pestanas.addTab("Lugares",     new PanelLugares(agencia));
        pestanas.addTab("Eventos",     new PanelEventos(agencia));

        // 5. Agregar las pestañas a la ventana
        add(pestanas);

        // 6. Hacer visible la ventana (siempre al final)
        setVisible(true);
    }
}