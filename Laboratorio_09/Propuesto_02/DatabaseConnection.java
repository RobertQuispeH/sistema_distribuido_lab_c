import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3310/propuesto_1";
    private static final String USUARIO = "root";
    private static final String CONTRASEÑA = "";

    public static Connection getConnection() throws SQLException {
        System.out.println("Intentando conectar a la base de datos...");
        Connection connection = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        System.out.println("Conexión a la base de datos establecida.");
        return connection;
    }

   
}
