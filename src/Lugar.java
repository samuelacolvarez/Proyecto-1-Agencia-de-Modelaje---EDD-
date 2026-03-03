public class Lugar {
    private String nombre;
    private String direccion;
    private String ciudad;
    private int capacidad;
    private String tipo;

    public Lugar(String nombre, String direccion, String ciudad, int capacidad, String tipo) throws IllegalArgumentException{
        if(capacidad<0) throw new IllegalArgumentException("la capacidad del lugar no puede ser negativa");
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.capacidad = capacidad;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Lugar{" +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", capacidad=" + capacidad +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
