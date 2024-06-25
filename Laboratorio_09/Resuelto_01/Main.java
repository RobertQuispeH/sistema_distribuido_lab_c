package app;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conn = Database.getConnection();
        if (conn != null) {
            System.out.println("Conexión establecida.");
        } else {
            System.out.println("Conexión fallida.");
        }
    }
}
