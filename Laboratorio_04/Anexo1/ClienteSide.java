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

        int selection = sc.nextInt(); // Lee la opción seleccionada por el usuario
       
    }
}
