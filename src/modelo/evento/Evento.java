package evento;

import java.util.Date;

public abstract class Evento {
    protected String nombre;
    protected Date fecha;
    protected String lugar;
    private String[] modelos;
    private String[] fotografos;

    private int contadorModelos;
    private int contadorFotografos;

    public Evento() {
    }

    public Evento(String nombre, Date fecha, String lugar, int capacidad) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.lugar = lugar;
        modelos = new Modelo[capacidad];
        fotografos = new Fotografo[capacidad];
    }

    public String getNombre() { return nombre; }

    public Date getFecha() {return fecha;}

    public String getLugar() {return lugar;}

    public abstract void mostrarDetalles();
    public abstract String tipoEvento();

    public void agregarModelos(Modelo modelo){
        if(contadorModelos < modelos.length){
            modelos[contadorModelos] = modelo;
            contadorModelos++;
        }else{
            System.out.println("No se pueden agregar más modelo");
        }
    }
    public void agregarFotografos(String fotografo){
        if(contadorFotografos < fotografos.length){
            fotografos[contadorFotografos] = fotografo;
            contadorFotografos++;
        }else{
            System.out.println("No se pueden agregar fotografo");
        }
    }
}
