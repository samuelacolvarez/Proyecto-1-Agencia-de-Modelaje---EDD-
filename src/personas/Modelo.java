package personas;

import excepciones.AlturaMinimaException;

import java.util.UUID;

public class Modelo extends Persona {
    public String codigo;
    public float estatura;
    public String Categoria;
    public String disponibilidad;

    public Modelo(UUID identificacion, String nombre, String contacto, String codigo, float estatura, String categoria, String disponibilidad)throws AlturaMinimaException {
            super(identificacion, nombre, contacto);
        if(this.estatura<1.8f){
            throw new AlturaMinimaException("el modelo debe medir mas de 1.80 metros");
        }
        this.codigo = UUID.randomUUID().toString();
        this.estatura = estatura;
        Categoria = categoria;
        this.disponibilidad = disponibilidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public float getEstatura() {
        return estatura;
    }

    public void setEstatura(float estatura) {
        this.estatura = estatura;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
    public void mostrarInformacion() {
        System.out.println("Codigo: " + this.codigo);
        System.out.println("Nombre: " + this.nombre);
        System.out.println("Contacto: " + this.contacto);
        System.out.println("Estatura: " + this.estatura);
        System.out.println("Categoria: " + this.Categoria);
        System.out.println("Disponibilidad: " + this.disponibilidad);
        System.out.println("Identificacion: " + this.identificacion);
    }

    @Override
    public String toString() {
        return "Modelo{" +
                "codigo='" + codigo + '\'' +
                ", estatura=" + estatura +
                ", Categoria='" + Categoria + '\'' +
                ", disponibilidad='" + disponibilidad + '\'' +
                ", identificacion='" + identificacion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", contacto='" + contacto + '\'' +
                '}';
    }
}
