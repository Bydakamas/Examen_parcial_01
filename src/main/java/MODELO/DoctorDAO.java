package MODELO;

import CONEXION.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {
// Método para listar todos los doctores

    public List<Doctor> listarDoctores() {
        List<Doctor> lista = new ArrayList<>();
        String sql = "SELECT * FROM Doctores";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setIdDoctor(rs.getInt("id_doctor"));
                doctor.setNombre(rs.getString("nombre"));
                doctor.setEspecialidad(rs.getString("especialidad"));
                doctor.setTelefono(rs.getString("telefono"));
                doctor.setActivo(rs.getBoolean("activo"));
                lista.add(doctor);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar doctores: " + e.getMessage());
        }
        return lista;
    }

    // Método para agregar un nuevo doctor
    public boolean agregarDoctor(Doctor doctor) {
        String sql = "INSERT INTO Doctores (nombre, especialidad, telefono, activo) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, doctor.getNombre());
            stmt.setString(2, doctor.getEspecialidad());
            stmt.setString(3, doctor.getTelefono());
            stmt.setBoolean(4, doctor.isActivo());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al agregar doctor: " + e.getMessage());
            return false;
        }
    }

    // Método para actualizar un doctor existente
    public boolean actualizarDoctor(Doctor doctor) {
        String sql = "UPDATE Doctores SET nombre = ?, especialidad = ?, telefono = ?, activo = ? WHERE id_doctor = ?";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, doctor.getNombre());
            stmt.setString(2, doctor.getEspecialidad());
            stmt.setString(3, doctor.getTelefono());
            stmt.setBoolean(4, doctor.isActivo());
            stmt.setInt(5, doctor.getIdDoctor());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar doctor: " + e.getMessage());
            return false;
        }
    }

    // Método para desactivar (eliminar) un doctor
    public boolean desactivarDoctor(int idDoctor) {
        String sql = "UPDATE Doctores SET activo = 0 WHERE id_doctor = ?";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDoctor);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al desactivar doctor: " + e.getMessage());
            return false;
        }
    }

    // Método para buscar un doctor por ID
    public Doctor buscarDoctorPorId(int idDoctor) {
        String sql = "SELECT * FROM Doctores WHERE id_doctor = ?";
        Doctor doctor = null;

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDoctor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    doctor = new Doctor();
                    doctor.setIdDoctor(rs.getInt("id_doctor"));
                    doctor.setNombre(rs.getString("nombre"));
                    doctor.setEspecialidad(rs.getString("especialidad"));
                    doctor.setTelefono(rs.getString("telefono"));
                    doctor.setActivo(rs.getBoolean("activo"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar doctor por ID: " + e.getMessage());
        }
        return doctor;
    }
}
