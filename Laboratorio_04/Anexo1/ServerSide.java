package Anexo1;

import java.rmi.Naming;

//Se configura y ejecuta el servidor RMI.
 
public class ServerSide {
    public static void main(String[] args) throws Exception {
        Stock pharmacy = new Stock(); // Creamos una instancia en el Stock
        // Añadimos elementos a nuestor inventario
        pharmacy.addMedicine("Paracetamol", 3.2f, 10);
        pharmacy.addMedicine("Mejoral", 2.0f, 20);
        pharmacy.addMedicine("Amoxilina", 1.0f, 30);
        pharmacy.addMedicine("Aspirina", 5.0f, 40);
        // Registra el objeto Stock en el registro RMI con el nombre "PHARMACY"
        Naming.rebind("PHARMACY", pharmacy);
        System.out.println("Server ready"); // Imprime un mensaje indicando que el servidor está listo
    }
}
