package CONEXION;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    // URL de conexión a SQL Server en Azure
    private static final String URL = "jdbc:sqlserver://serviceservicioprueba.database.windows.net:1433;databaseName=SQLRegistroHospital"; // Reemplaza HospitalDB con el nombre de tu base de datos
    private static final String USUARIO = "gustavo";  // Reemplaza con tu usuario de SQL Server en Azure
    private static final String CONTRASENA = "Origami3dporsiempre+";  // Reemplaza con tu contraseña
    private static Connection conexion = null;

    // Método para obtener una conexión
    public static Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            try {
                // Cargar el driver de SQL Server
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                // Establecer la conexión
                conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
                System.out.println("Conexión establecida con éxito.");
            } catch (ClassNotFoundException e) {
                System.err.println("No se encontró el driver de SQL Server: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("Error al conectar a la base de datos: " + e.getMessage());
                throw e; // Propagar la excepción
            }
        }
        return conexion; // Retornar la conexión
    }

    // Método para cerrar la conexión
    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close(); // Cerrar la conexión
                System.out.println("Conexión cerrada correctamente.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
