package logica;
import modelo.Lugar;
import modelo.evento.Evento;
import modelo.Fotografo;
import modelo.Modelo;

public class Agencia {

    private Modelo[]    modelos;
    private Fotografo[] fotografos;
    private Lugar[]     lugares;
    private Evento[]    eventos;

    private int cantidadModelos;
    private int cantidadFotografos;
    private int cantidadlugares;
    private int cantidadEventos;

    public Agencia() {
        // Arrancamos con 10, pero pueden crecer solos
        modelos    = new Modelo[10];
        fotografos = new Fotografo[10];
        lugares    = new Lugar[10];
        eventos    = new Evento[10];

        cantidadModelos    = GestorArchivos.cargarModelos(modelos);
        cantidadFotografos = GestorArchivos.cargarFotografos(fotografos);
        cantidadlugares    = GestorArchivos.cargarLugares(lugares);
        cantidadEventos    = 0;
    }

    // ── Métodos para crecer cada arreglo ─────────────────────────────────

    private Modelo[] crecerArregloModelos() {
        Modelo[] nuevo = new Modelo[modelos.length * 2];
        for (int i = 0; i < cantidadModelos; i++) {
            nuevo[i] = modelos[i];
        }
        return nuevo;
    }

    private Fotografo[] crecerArregloFotografos() {
        Fotografo[] nuevo = new Fotografo[fotografos.length * 2];
        for (int i = 0; i < cantidadFotografos; i++) {
            nuevo[i] = fotografos[i];
        }
        return nuevo;
    }

    private Lugar[] crecerArregloLugares() {
        Lugar[] nuevo = new Lugar[lugares.length * 2];
        for (int i = 0; i < cantidadlugares; i++) {
            nuevo[i] = lugares[i];
        }
        return nuevo;
    }

    private Evento[] crecerArregloEventos() {
        Evento[] nuevo = new Evento[eventos.length * 2];
        for (int i = 0; i < cantidadEventos; i++) {
            nuevo[i] = eventos[i];
        }
        return nuevo;
    }

    // ── Métodos agregar (ahora sin límite fijo) ───────────────────────────

    public void agregarModelo(Modelo modelo) {
        if (cantidadModelos == modelos.length) {
            modelos = crecerArregloModelos();
        }
        modelos[cantidadModelos] = modelo;
        cantidadModelos++;
    }

    public void agregarFotografo(Fotografo fotografo) {
        if (cantidadFotografos == fotografos.length) {
            fotografos = crecerArregloFotografos();
        }
        fotografos[cantidadFotografos] = fotografo;
        cantidadFotografos++;
    }

    public void agregarLugar(Lugar lugar) {
        if (cantidadlugares == lugares.length) {
            lugares = crecerArregloLugares();
        }
        lugares[cantidadlugares] = lugar;
        cantidadlugares++;
    }

    public void agregarEvento(Evento evento) {
        if (cantidadEventos == eventos.length) {
            eventos = crecerArregloEventos();
        }
        eventos[cantidadEventos] = evento;
        cantidadEventos++;
    }



    public Modelo[]    getModelos()    { return modelos; }
    public Fotografo[] getFotografos() { return fotografos; }
    public Lugar[]     getLugares()    { return lugares; }
    public Evento[]    getEventos()    { return eventos; }

    public int getCantidadModelos()    { return cantidadModelos; }
    public int getCantidadFotografos() { return cantidadFotografos; }
    public int getCantidadLugares()    { return cantidadlugares; }
    public int getCantidadEventos()    { return cantidadEventos; }

    public Modelo buscarModelo(String codigo) {
        for (int i = 0; i < cantidadModelos; i++) {
            if (modelos[i].getCodigo().equals(codigo)) return modelos[i];
        }
        return null;
    }

    public Fotografo buscarFotografo(String identificacion) {
        for (int i = 0; i < cantidadFotografos; i++) {
            if (fotografos[i].getIdentificacion().equals(identificacion)) return fotografos[i];
        }
        return null;
    }

    public Lugar buscarLugar(String nombre) {
        for (int i = 0; i < cantidadlugares; i++) {
            if (lugares[i].getNombre().equals(nombre)) return lugares[i];
        }
        return null;
    }

    public void eliminarModelo(String codigo) {
        for (int i = 0; i < cantidadModelos; i++) {
            if (modelos[i].getCodigo().equals(codigo)) {
                for (int j = i; j < cantidadModelos - 1; j++) {
                    modelos[j] = modelos[j + 1];
                }
                cantidadModelos--;
                break;
            }
        }
    }

    public void eliminarFotografo(String identificacion) {
        for (int i = 0; i < cantidadFotografos; i++) {
            if (fotografos[i].getIdentificacion().equals(identificacion)) {
                for (int j = i; j < cantidadFotografos - 1; j++) {
                    fotografos[j] = fotografos[j + 1];
                }
                cantidadFotografos--;
                break;
            }
        }
    }
}