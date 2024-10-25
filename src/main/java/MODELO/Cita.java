package MODELO;
import java.time.LocalDate;
import java.time.LocalTime;

import java.sql.Date;

public class Cita {
    private int idCita;
    private int idDoctor;
    private int idPaciente;
    private LocalDate fecha;
    private LocalTime hora;
    private String descripcion;
    private String estado;  // Ejemplo: Pendiente, Finalizada, Cancelada

    // Constructor
    public Cita(int idCita, int idDoctor, int idPaciente, LocalDate fecha, LocalTime hora, String descripcion, String estado) {
        this.idCita = idCita;
        this.idDoctor = idDoctor;
        this.idPaciente = idPaciente;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Cita() {
    }

    // Getters y Setters
    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}