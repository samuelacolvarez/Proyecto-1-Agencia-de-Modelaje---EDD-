package personas;

import java.util.UUID;

public class Fotografo extends Persona {
    private String especialidad;
    private int añosExperiencia;

    public Fotografo(UUID identificacion, String nombre, String contacto, String especialidad, int añosExperiencia) {
        super(identificacion, nombre, contacto);
        this.especialidad = especialidad;
        this.añosExperiencia = añosExperiencia;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public int getAñosExperiencia() {
        return añosExperiencia;
    }

    public void setAñosExperiencia(int añosExperiencia) {
        this.añosExperiencia = añosExperiencia;
    }

    public void mostrarInformacion() {
        IO.println("Especialidad: " + this.especialidad);
        IO.println("Años de experiencia "+ this.añosExperiencia);
        System.out.println("Nombre: " + this.nombre);
        System.out.println("Contacto: " + this.contacto);
        IO.println("Identificacion: " + this.identificacion);
    }
}
