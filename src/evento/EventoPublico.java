package evento;

import java.util.Date;

public class EventoPublico extends Evento{
    private int capacidadAsistente;
    private String patrocinador;

    public EventoPublico(String nombre, Date fecha, String lugar,
                         int capacidad, int capacidadAsistente, String patrocinador) {
        super(nombre, fecha, lugar, capacidad);
        this.capacidadAsistente = capacidadAsistente;
        this.patrocinador = patrocinador;
    }
    @Override
    public void mostrarDetalles(){
        System.out.println("Evento Publico");
        System.out.println("Nombre: " + nombre);
        System.out.println("Fecha: " + fecha);
        System.out.println("Lugar: " + lugar);
        System.out.println("Capacidad Asistente: " + capacidadAsistente);
        System.out.println("Patrocinador: " + patrocinador);
    }
    @Override
    public String tipoEvento(){
        return "Publico";
    }
}
