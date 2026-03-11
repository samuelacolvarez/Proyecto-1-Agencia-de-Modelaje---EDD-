package logica;
import modelo.Lugar;
import modelo.evento.Evento;
import modelo.Fotografo;
import modelo.Modelo;

public class Agencia {
    private Modelo[] modelos;
    private Fotografo[] fotografos;
    private Evento[] eventos;
    private Lugar[] lugares;

    private int cantidadModelos;
    private int cantidadFotografos;
    private int cantidadEventos;
    private int cantidadlugares;

    private int capacidaMaxima = 100;
    public Agencia(){
        // Creamos los arreglos vacíos con capacidad para 100 elementos cada uno
        modelos    = new Modelo[100];
        fotografos = new Fotografo[100];
        lugares    = new Lugar[100];
        eventos    = new Evento[100];

        // Cargamos los datos guardados y guardamos cuántos se cargaron
        // Si el archivo no existe, devuelve 0 y los arreglos quedan vacíos
        cantidadModelos    = GestorArchivos.cargarModelos(modelos);
        cantidadFotografos = GestorArchivos.cargarFotografos(fotografos);
        cantidadlugares    = GestorArchivos.cargarLugares(lugares);

        // Los eventos no se persisten en GestorArchivos,
        // así que siempre arrancan en 0 al abrir el programa
        cantidadEventos = 0;
    }

    public Modelo[] getModelos() {
        return modelos;
    }

    public Fotografo[] getFotografos() {
        return fotografos;
    }

    public Evento[] getEventos() {
        return eventos;
    }

    public Lugar[] getLugares() {
        return lugares;
    }

    public void agregarModelo(Modelo modelo){
        if(cantidadModelos < capacidaMaxima){
            modelos[cantidadModelos] = modelo;
            cantidadModelos++;
        }
    }
    public void agregarFotografo(Fotografo fotografo){
        if(cantidadFotografos < capacidaMaxima){
            fotografos[cantidadFotografos] = fotografo;
            cantidadFotografos++;
        }
    }
    public void agregarEvento(Evento evento){
        if(cantidadEventos < capacidaMaxima){
            eventos[cantidadEventos] = evento;
            cantidadEventos++;
        }
    }
    public void agregarLugar(Lugar lugar){
        if(cantidadlugares < capacidaMaxima){
            lugares[cantidadlugares] = lugar;
            cantidadlugares++;
        }
    }
    public Modelo buscarModelo(String codigo){
        for (int i = 0; i < cantidadModelos; i++) {
            if(modelos[i].getCodigo().equals(codigo)){
                return modelos[i];
            }
        }
        return null;
    }
    public Fotografo buscarFotografo(String identificacion){
        for (int i = 0; i < cantidadFotografos; i++) {
            if(fotografos[i].getIdentificacion().equals(identificacion)){
                return fotografos[i];
            }
        }
        return null;
    }
    public Lugar buscarLugar(String nombre){
        for (int i = 0; i < cantidadlugares; i ++){
            if(lugares[i].getNombre().equals(nombre)){
                return lugares[i];
            }
        }
        return null;
    }
    public void eliminarModelo(String codigo){
        for (int i = 0; i < cantidadModelos; i++) {
            if(modelos[i].getCodigo().equals(codigo)){
                for (int j = i; j < cantidadModelos -1; j++) {
                    modelos[j] =  modelos[j+1];
                }
                cantidadModelos--;
                break;
            }
        }
    }
    public void eliminarFotografo(String identificacion){
        for (int i = 0; i < cantidadFotografos; i++) {
            if(fotografos[i].getIdentificacion().equals(identificacion)){
                for (int j = i; j < cantidadFotografos -1; j++) {
                    fotografos[j] =  fotografos[j+1];
                }
                cantidadFotografos--;
                break;
            }
        }
    }

    public int getCantidadModelos() {
        return cantidadModelos;
    }

    public int getCantidadFotografos() {
        return cantidadFotografos;
    }

    public int getCantidadEventos() {
        return cantidadEventos;
    }

    public int getCantidadLugares() {
        return cantidadlugares;
    }
}
