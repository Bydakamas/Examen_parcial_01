package MODELO;

public class Doctor {
    private int idDoctor;
    private String nombre;
    private String especialidad;
    private String telefono;
    private boolean activo;  // true para activo, false para inactivo

    // Constructor
    public Doctor(int idDoctor, String nombre, String especialidad, String telefono, boolean activo) {
        this.idDoctor = idDoctor;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.activo = activo;
    }

    public Doctor() {
    }

    // Getters y Setters
    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}

