package modelo;

import java.util.UUID;

public abstract class Persona {
    public String identificacion;
    public String nombre;
    public String contacto;

    public Persona(UUID identificacion, String nombre, String contacto) {
        this.identificacion = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.contacto = contacto;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
    public abstract void mostrarInformacion();
}
