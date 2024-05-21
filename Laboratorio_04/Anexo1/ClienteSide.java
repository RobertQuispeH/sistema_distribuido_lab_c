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
        } else if (selection == 2) {
            // Pero si es la opcion 2, es para comprar el producto
            System.out.println("Ingrese nombre de la medicina:");
            String medicineName = sc.next(); // Ingresa y lee por consola el nombre e la medicina a comprar
            System.out.println("Ingrese cantidad a comprar:");
            int amount = sc.nextInt(); // Luego ponemos la cantida que queremoscomprar
            MedicineInterface medicine = pharmacy.buyMedicine(medicineName, amount); // Se realiza la compra 
            System.out.println("Usted acaba de comprar:");
            System.out.println(medicine.print()); // Imprimimos un resumen de lo que adquirimos
        } else {
            System.out.println("Seleccione una opción válida"); // Mensaje en caso de opción inválida
        }
        sc.close(); // Cierra el ingreso por consola
       
    }
}
