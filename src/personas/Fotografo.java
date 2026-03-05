package personas;

import java.util.UUID;

public class Fotografo extends Persona {
    private String especialidad;
    private int aniosExperiencia;

    public Fotografo(UUID identificacion, String nombre, String contacto, String especialidad, int aniosExperiencia) {
        super(identificacion, nombre, contacto);
        this.especialidad = especialidad;
        this.aniosExperiencia = aniosExperiencia;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public int getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(int aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    public void mostrarInformacion() {
        IO.println("Especialidad: " + this.especialidad);
        IO.println("Años de experiencia "+ this.aniosExperiencia);
        System.out.println("Nombre: " + this.nombre);
        System.out.println("Contacto: " + this.contacto);
        IO.println("Identificacion: " + this.identificacion);
    }
}
