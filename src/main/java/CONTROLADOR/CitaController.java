package CONTROLADOR;

import MODELO.Cita;
import MODELO.CitaDAO;
import MODELO.Paciente;
import MODELO.PacienteDAO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CitaController {

    private CitaDAO citaDAO;
    private PacienteDAO pacienteDAO; // Añadir el DAO de paciente

    // Constructor para inicializar el DAO
    public CitaController() {
        citaDAO = new CitaDAO();
        pacienteDAO = new PacienteDAO(); // Inicializar el DAO de paciente
    }

// Método en CitaController para agregar un paciente
    public boolean agregarPaciente(Paciente paciente) {
        PacienteDAO pacienteDAO = new PacienteDAO();
        return pacienteDAO.agregarPaciente(paciente);
    }

    // Método para obtener el ID del paciente por DNI
    public int obtenerIdPacientePorDni(String dni) {
        return pacienteDAO.obtenerIdPacientePorDni(dni);
    }

    public boolean agregarCita(String dniPaciente, int idDoctor, LocalDate fecha, LocalTime hora, String descripcion, String estado) {
        // Obtener el ID del paciente utilizando el DNI
        int idPaciente = obtenerIdPacientePorDni(dniPaciente);

        if (idPaciente == -1) {
            System.err.println("No se encontró un paciente con DNI: " + dniPaciente);
            return false; // Manejo de error si no se encuentra el paciente
        }

        Cita cita = new Cita();
        cita.setIdDoctor(idDoctor);
        cita.setIdPaciente(idPaciente);
        cita.setFecha(fecha);
        cita.setHora(hora);
        cita.setDescripcion(descripcion);
        cita.setEstado(estado);

        // Llamada al DAO para insertar la cita
        return citaDAO.agregarCita(cita);
    }

    // Método para listar todas las citas
    public List<Cita> listarCitas() {
        return citaDAO.listarCitas();
    }

    // Método para actualizar una cita (puede ser cambiar su estado o cualquier otro campo)
    public boolean actualizarCita(int idCita, String estado, String descripcion) {
        Cita cita = citaDAO.obtenerCitaPorId(idCita);
        if (cita != null) {
            cita.setEstado(estado);
            cita.setDescripcion(descripcion);
            return citaDAO.actualizarCita(cita);
        }
        return false;
    }

    // Método para eliminar o cancelar una cita
    public boolean eliminarCita(int idCita) {
        return citaDAO.eliminarCita(idCita);
    }

    // Método para listar citas por doctor
    public List<Cita> listarCitasPorDoctor(int idDoctor) {
        return citaDAO.listarCitasPorDoctor(idDoctor);
    }

    // Método para listar citas por paciente
    public List<Cita> listarCitasPorPaciente(int idPaciente) {
        return citaDAO.listarCitasPorPaciente(idPaciente);
    }
}
