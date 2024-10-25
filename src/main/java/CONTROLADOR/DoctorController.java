package CONTROLADOR;

import MODELO.Doctor;
import MODELO.DoctorDAO;
import java.util.List;

public class DoctorController {

    private DoctorDAO doctorDAO;

    // Constructor
    public DoctorController() {
        this.doctorDAO = new DoctorDAO();
    }

    // Método para obtener todos los doctores
    public List<Doctor> obtenerDoctores() {
        return doctorDAO.listarDoctores();
    }

    // Método para agregar un nuevo doctor
    public boolean agregarDoctor(String nombre, String especialidad, String telefono) {
        Doctor doctor = new Doctor();
        doctor.setNombre(nombre);
        doctor.setEspecialidad(especialidad);
        doctor.setTelefono(telefono);
        doctor.setActivo(true);  // Por defecto, el doctor está activo

        return doctorDAO.agregarDoctor(doctor);
    }

    // Método para actualizar un doctor existente
    public boolean actualizarDoctor(int idDoctor, String nombre, String especialidad, String telefono, boolean activo) {
        Doctor doctor = doctorDAO.buscarDoctorPorId(idDoctor);
        if (doctor != null) {
            doctor.setNombre(nombre);
            doctor.setEspecialidad(especialidad);
            doctor.setTelefono(telefono);
            doctor.setActivo(activo);

            return doctorDAO.actualizarDoctor(doctor);
        } else {
            System.out.println("Doctor no encontrado con el ID: " + idDoctor);
            return false;
        }
    }

    // Método para desactivar (eliminar lógicamente) un doctor
    public boolean desactivarDoctor(int idDoctor) {
        return doctorDAO.desactivarDoctor(idDoctor);
    }

    // Método para buscar un doctor por ID
    public Doctor obtenerDoctorPorId(int idDoctor) {
        return doctorDAO.buscarDoctorPorId(idDoctor);
    }
}
