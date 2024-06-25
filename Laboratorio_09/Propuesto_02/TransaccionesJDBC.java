import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class TransaccionesJDBC {

    public static void main(String[] args) {
        Connection conexion = null;

        try {
            // Obtener la conexión a la base de datos
            conexion = DatabaseConnection.getConnection();
            // Desactivar el auto-commit para manejar la transacción manualmente
            conexion.setAutoCommit(false);
            System.out.println("Auto-commit desactivado. Iniciando transacción.");

            // Consultas SQL para las operaciones
            String sqlInsertarDepartamento = "CALL insertarDepartamento(?, ?, ?, ?)";
            String sqlInsertarIngeniero = "CALL insertarIngeniero(?, ?, ?, ?, ?)";
            String sqlInsertarProyecto = "CALL insertarProyecto(?, ?, ?, ?, ?)";

            // Crear PreparedStatements
            PreparedStatement stmtInsertarDepartamento = conexion.prepareStatement(sqlInsertarDepartamento);
            PreparedStatement stmtInsertarIngeniero = conexion.prepareStatement(sqlInsertarIngeniero);
            PreparedStatement stmtInsertarProyecto = conexion.prepareStatement(sqlInsertarProyecto);

           

        } catch (SQLIntegrityConstraintViolationException e) {
            // Manejo de clave primaria duplicada u otra restricción de integridad
            System.err.println("Error: Los datos ingresados ya existen en la base de datos.");
            if (conexion != null) {
                try {
                    conexion.rollback();
                    System.out.println("Transacción revertida debido a un error.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException e) {
            // Manejo de otras excepciones SQL
            if (conexion != null) {
                try {
                    conexion.rollback();
                    System.out.println("Transacción revertida debido a un error.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            // Cerrar los recursos
            if (conexion != null) {
                try {
                    conexion.setAutoCommit(true);
                    DatabaseConnection.closeConnection(conexion);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
