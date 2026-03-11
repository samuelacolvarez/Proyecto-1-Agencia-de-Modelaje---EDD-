package logica;
import modelo.Modelo;
import modelo.Fotografo;
import modelo.Lugar;
import java.io.*;

public class GestorArchivos {

    private static final String RUTA = "datos/";

    // ─── GUARDAR MODELOS ──────────────────────────────────────────────────
    public static void guardarModelos(Modelo[] modelos, int cantidad) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(RUTA + "modelos.txt", false));

            for (int i = 0; i < cantidad; i++) {
                Modelo m = modelos[i];
                pw.println(
                        m.getCodigo() + ";" +
                                m.getNombre()         + ";" +
                                m.getIdentificacion() + ";" +
                                m.getContacto()       + ";" +
                                m.getEstatura()       + ";" +
                                m.getCategoria()      + ";" +
                                m.getDisponibilidad()
                );
            }
            pw.close();

        } catch (IOException e) {
            System.out.println("Error al guardar modelos: " + e.getMessage());
        }
    }

    // ─── CARGAR MODELOS ───────────────────────────────────────────────────
    public static int cargarModelos(Modelo[] modelos) {
        int cantidad = 0;
        try {
            File archivo = new File(RUTA + "modelos.txt");
            if (!archivo.exists()) return 0;

            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                modelos[cantidad] = new Modelo(
                        java.util.UUID.fromString(partes[0]),
                        partes[1],
                        partes[2],
                        partes[3],
                        Double.parseDouble(partes[4]),
                        partes[5],
                        partes[6]
                );
                cantidad++;
            }
            br.close();

        } catch (IOException e) {
            System.out.println("Error al cargar modelos: " + e.getMessage());
        }
        return cantidad;
    }

    // ─── GUARDAR FOTÓGRAFOS ───────────────────────────────────────────────
    public static void guardarFotografos(Fotografo[] fotografos, int cantidad) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(RUTA + "fotografos.txt", false));

            for (int i = 0; i < cantidad; i++) {
                Fotografo f = fotografos[i];
                pw.println(
                        f.getNombre() + ";" +
                                f.getIdentificacion() + ";" +
                                f.getContacto() + ";" +
                                f.getEspecialidad() + ";" +
                                f.getAniosExperiencia()
                );
            }
            pw.close();

        } catch (IOException e) {
            System.out.println("Error al guardar fotógrafos: " + e.getMessage());
        }
    }

    // ─── CARGAR FOTÓGRAFOS ────────────────────────────────────────────────
    public static int cargarFotografos(Fotografo[] fotografos) {
        int cantidad = 0;
        try {
            File archivo = new File(RUTA + "fotografos.txt");
            if (!archivo.exists()) return 0;

            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                fotografos[cantidad] = new Fotografo(
                        java.util.UUID.fromString(partes[0]),
                        partes[1],
                        partes[2],
                        partes[3],
                        Integer.parseInt(partes[4])
                );
                cantidad++;
            }
            br.close();

        } catch (IOException e) {
            System.out.println("Error al cargar fotógrafos: " + e.getMessage());
        }
        return cantidad;
    }

    // ─── GUARDAR LUGARES ──────────────────────────────────────────────────
    public static void guardarLugares(Lugar[] lugares, int cantidad) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(RUTA + "lugares.txt", false));

            for (int i = 0; i < cantidad; i++) {
                Lugar l = lugares[i];
                pw.println(
                        l.getNombre() + ";" +
                                l.getDireccion() + ";" +
                                l.getCiudad() + ";" +
                                l.getCapacidad() + ";"
                );
            }
            pw.close();

        } catch (IOException e) {
            System.out.println("Error al guardar lugares: " + e.getMessage());
        }
    }

    // ─── CARGAR LUGARES ───────────────────────────────────────────────────
    public static int cargarLugares(Lugar[] lugares) {
        int cantidad = 0;
        try {
            File archivo = new File(RUTA + "lugares.txt");
            if (!archivo.exists()) return 0;

            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                lugares[cantidad] = new Lugar(
                        partes[0],
                        partes[1],
                        partes[2],
                        Integer.parseInt(partes[3]),
                        partes[4]
                );
                cantidad++;
            }
            br.close();

        } catch (IOException e) {
            System.out.println("Error al cargar lugares: " + e.getMessage());
        }
        return cantidad;
    }
}