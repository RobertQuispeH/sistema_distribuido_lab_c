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
        
    }
}
