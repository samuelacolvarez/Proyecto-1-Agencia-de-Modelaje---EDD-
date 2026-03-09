package modelo.evento;

import java.util.Date;

public class EventoPrivado extends Evento{
    private String cliente;
    private String nivelConfidencialidad;

    public EventoPrivado(String nombre, Date fecha, String lugar, int capacidad,
                         String cliente, String nivelConfidencialidad) {
        super(nombre, fecha, lugar, capacidad);
        this.cliente = cliente;
        this.nivelConfidencialidad = nivelConfidencialidad;
        }

        @Override
        public void mostrarDetalles(){
            System.out.println("Evento privado para el cliente:" + cliente);
            System.out.println("Nivel: " + nivelConfidencialidad);
    }
    @Override
    public String tipoEvento(){
        return "Privado";
    }
}
