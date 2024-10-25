package MODELO;

import CONEXION.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {
// Método para agregar una nueva cita

    public boolean agregarCita(Cita cita) {
        String sql = "INSERT INTO Citas (id_doctor, id_paciente, fecha, hora, descripcion, estado) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cita.getIdDoctor());
            stmt.setInt(2, cita.getIdPaciente());
            stmt.setDate(3, Date.valueOf(cita.getFecha()));  // convertir LocalDate a java.sql.Date
            stmt.setTime(4, Time.valueOf(cita.getHora()));    // convertir LocalTime a java.sql.Time
            stmt.setString(5, cita.getDescripcion());

            // Aquí se decide si se envía null o el estado de la cita
            if (cita.getEstado() == null) {
                stmt.setNull(6, java.sql.Types.VARCHAR);
            } else {
                stmt.setString(6, cita.getEstado());
            }

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al agregar cita: " + e.getMessage());
            return false;
        }
    }

    // Método para listar todas las citas
    public List<Cita> listarCitas() {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT * FROM Citas";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cita cita = new Cita();
                cita.setIdCita(rs.getInt("id_cita"));
                cita.setIdDoctor(rs.getInt("id_doctor"));
                cita.setIdPaciente(rs.getInt("id_paciente"));
                cita.setFecha(rs.getDate("fecha").toLocalDate());  // convertir java.sql.Date a LocalDate
                cita.setHora(rs.getTime("hora").toLocalTime());  // convertir java.sql.Time a LocalTime
                cita.setDescripcion(rs.getString("descripcion"));
                cita.setEstado(rs.getString("estado"));

                lista.add(cita);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar citas: " + e.getMessage());
        }
        return lista;
    }

    // Método para listar citas por doctor
    public List<Cita> listarCitasPorDoctor(int idDoctor) {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT * FROM Citas WHERE id_doctor = ?";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDoctor);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cita cita = new Cita();
                    cita.setIdCita(rs.getInt("id_cita"));
                    cita.setIdDoctor(rs.getInt("id_doctor"));
                    cita.setIdPaciente(rs.getInt("id_paciente"));
                    cita.setFecha(rs.getDate("fecha").toLocalDate());
                    cita.setHora(rs.getTime("hora").toLocalTime());
                    cita.setDescripcion(rs.getString("descripcion"));
                    cita.setEstado(rs.getString("estado"));

                    lista.add(cita);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar citas por doctor: " + e.getMessage());
        }
        return lista;
    }

    // Método para listar citas por paciente
    public List<Cita> listarCitasPorPaciente(int idPaciente) {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT * FROM Citas WHERE id_paciente = ?";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPaciente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cita cita = new Cita();
                    cita.setIdCita(rs.getInt("id_cita"));
                    cita.setIdDoctor(rs.getInt("id_doctor"));
                    cita.setIdPaciente(rs.getInt("id_paciente"));
                    cita.setFecha(rs.getDate("fecha").toLocalDate());
                    cita.setHora(rs.getTime("hora").toLocalTime());
                    cita.setDescripcion(rs.getString("descripcion"));
                    cita.setEstado(rs.getString("estado"));

                    lista.add(cita);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar citas por paciente: " + e.getMessage());
        }
        return lista;
    }

    // Método para actualizar el estado de una cita (por ejemplo, de 'Pendiente' a 'Finalizada')
    public boolean actualizarEstadoCita(int idCita, String nuevoEstado) {
        String sql = "UPDATE Citas SET estado = ? WHERE id_cita = ?";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, idCita);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar el estado de la cita: " + e.getMessage());
            return false;
        }
    }

    // Método para eliminar (soft delete) una cita
    public boolean eliminarCita(int idCita) {
        String sql = "DELETE FROM Citas WHERE id_cita = ?";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCita);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar cita: " + e.getMessage());
            return false;
        }
    }

    // Método para buscar una cita por ID
    public Cita buscarCitaPorId(int idCita) {
        String sql = "SELECT * FROM Citas WHERE id_cita = ?";
        Cita cita = null;

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCita);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cita = new Cita();
                    cita.setIdCita(rs.getInt("id_cita"));
                    cita.setIdDoctor(rs.getInt("id_doctor"));
                    cita.setIdPaciente(rs.getInt("id_paciente"));
                    cita.setFecha(rs.getDate("fecha").toLocalDate());
                    cita.setHora(rs.getTime("hora").toLocalTime());
                    cita.setDescripcion(rs.getString("descripcion"));
                    cita.setEstado(rs.getString("estado"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cita por ID: " + e.getMessage());
        }
        return cita;
    }

    // Método para buscar una cita por ID
    public Cita obtenerCitaPorId(int idCita) {
        String sql = "SELECT * FROM Citas WHERE id_cita = ?";
        Cita cita = null;

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCita);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cita = new Cita();
                    cita.setIdCita(rs.getInt("id_cita"));
                    cita.setIdDoctor(rs.getInt("id_doctor"));
                    cita.setIdPaciente(rs.getInt("id_paciente"));
                    cita.setFecha(rs.getDate("fecha").toLocalDate());
                    cita.setHora(rs.getTime("hora").toLocalTime());
                    cita.setDescripcion(rs.getString("descripcion"));
                    cita.setEstado(rs.getString("estado"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cita por ID: " + e.getMessage());
        }
        return cita;
    }

    // Método para actualizar una cita
    public boolean actualizarCita(Cita cita) {
        String sql = "UPDATE Citas SET id_doctor = ?, id_paciente = ?, fecha = ?, hora = ?, descripcion = ?, estado = ? WHERE id_cita = ?";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cita.getIdDoctor());
            stmt.setInt(2, cita.getIdPaciente());
            stmt.setDate(3, Date.valueOf(cita.getFecha()));  // convertir LocalDate a java.sql.Date
            stmt.setTime(4, Time.valueOf(cita.getHora()));  // convertir LocalTime a java.sql.Time
            stmt.setString(5, cita.getDescripcion());
            stmt.setString(6, cita.getEstado());
            stmt.setInt(7, cita.getIdCita());  // Especificar cuál cita actualizar

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar cita: " + e.getMessage());
            return false;
        }
    }
}
