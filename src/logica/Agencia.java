package agencia;

import lugar.Lugar;
import evento.Evento;
import personas.Fotografo;
import personas.Modelo;

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
        modelos = new Modelo[capacidaMaxima];
        fotografos = new Fotografo[capacidaMaxima];
        eventos = new Evento[capacidaMaxima];
        lugares = new Lugar[capacidaMaxima];

        cantidadModelos = 0;
        cantidadFotografos = 0;
        cantidadEventos = 0;
        cantidadlugares = 0;
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
                for (int j = 0; j < cantidadModelos; j++) {
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
                for (int j = 0; j < cantidadFotografos; j++) {
                    fotografos[i] =  fotografos[j+1];
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
