package Ejercicio02;

import java.rmi.Naming;

public class CreditCardClient {
    public static void main(String[] args) {
        try {
            CreditCardSystem cardSystem = (CreditCardSystem) Naming.lookup("//localhost/CreditCardSystem");

            String cardNumber = args[0];
            int cant = Integer.parseInt(args[1]);

            // Validar tarjeta
            if (cardSystem.validateCard(cardNumber)) {
                System.out.println("Card is valid.");
                
                // Obtener balance
                double balance = cardSystem.getBalance(cardNumber);
                System.out.println("Balance: " + balance);
                
                // Realizar un cargo
                if (cardSystem.charge(cardNumber, cant)) {
                    System.out.println("Charge successful. New balance: " + cardSystem.getBalance(cardNumber));
                } else {
                    System.out.println("Charge failed. Insufficient funds.");
                }
            } else {
                System.out.println("Invalid card.");
            }
        } catch (Exception e) {
            System.err.println("CreditCardClient exception: " + e.toString());
            e.printStackTrace();
        }
    }
    
}