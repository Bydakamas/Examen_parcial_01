package MODELO;

import CONEXION.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {
// Método para listar todos los pacientes

    public List<Paciente> listarPacientes() {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM Pacientes";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setIdPaciente(rs.getInt("id_paciente"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setDni(rs.getString("dni"));
                paciente.setTelefono(rs.getString("telefono"));
                lista.add(paciente);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar pacientes: " + e.getMessage());
        }
        return lista;
    }

    // Método para agregar un nuevo paciente
    public boolean agregarPaciente(Paciente paciente) {
        String sql = "INSERT INTO Pacientes (nombre, dni, telefono) VALUES (?, ?, ?)";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paciente.getNombre());
            stmt.setString(2, paciente.getDni());
            stmt.setString(3, paciente.getTelefono());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al agregar paciente: " + e.getMessage());
            return false;
        }
    }

    // Método para actualizar un paciente existente
    public boolean actualizarPaciente(Paciente paciente) {
        String sql = "UPDATE Pacientes SET nombre = ?, dni = ?, telefono = ? WHERE id_paciente = ?";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paciente.getNombre());
            stmt.setString(2, paciente.getDni());
            stmt.setString(3, paciente.getTelefono());
            stmt.setInt(4, paciente.getIdPaciente());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar paciente: " + e.getMessage());
            return false;
        }
    }

    // Método para eliminar un paciente
    public boolean eliminarPaciente(int idPaciente) {
        String sql = "DELETE FROM Pacientes WHERE id_paciente = ?";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPaciente);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar paciente: " + e.getMessage());
            return false;
        }
    }

    // Método para buscar un paciente por ID
    public Paciente buscarPacientePorId(int idPaciente) {
        String sql = "SELECT * FROM Pacientes WHERE id_paciente = ?";
        Paciente paciente = null;

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPaciente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    paciente = new Paciente();
                    paciente.setIdPaciente(rs.getInt("id_paciente"));
                    paciente.setNombre(rs.getString("nombre"));
                    paciente.setDni(rs.getString("dni"));
                    paciente.setTelefono(rs.getString("telefono"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar paciente por ID: " + e.getMessage());
        }
        return paciente;
    }

    // Método para obtener el ID del paciente por su DNI
    public int obtenerIdPacientePorDni(String dni) {
        String sql = "SELECT id_paciente FROM Pacientes WHERE dni = ?";
        int idPaciente = -1; // Valor predeterminado si no se encuentra

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    idPaciente = rs.getInt("id_paciente"); // Obtener el ID
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar paciente por DNI: " + e.getMessage());
        }

        return idPaciente;
    }
}
