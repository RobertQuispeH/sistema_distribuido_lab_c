package Anexo1;

import java.rmi.Naming;
import java.util.HashMap;
import java.util.Scanner;

// Esta clase actúa como cliente RMI para interactuar con el servidor.

public class ClienteSide {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in); // Se inicializan las entradas para el usuario
        StockInterface pharmacy = (StockInterface) Naming.lookup("PHARMACY"); // Busca el objeto remoto "PHARMACY"
        System.out.println("Ingrese la opción\n1: Listar productos\n2: Comprar Producto\n"); // Muestra opciones al usuario

        int selection = sc.nextInt(); // Ingresa y lee la opcion ingresada por consola
         if (selection == 1) {
            // Si es 1lista los medicamentos que estan el HashMap
            HashMap<String, MedicineInterface> products = pharmacy.getStockProducts(); // Los obtiene del hashmap
            for (String key : products.keySet()) { // Pero debemos iterar sobre los procutos
                MedicineInterface medicine = products.get(key);
                System.out.println(medicine.print()); // Una vez recorrido todo, imprime la informacion
                System.out.println("*--------------*");
            }
        } 
        } else {
            System.out.println("Seleccione una opción válida"); // Mensaje en caso de opción inválida
        }
        sc.close(); // Cierra el ingreso por consola
       
    }
}
