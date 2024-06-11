package labsd;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Consumer {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost:1516/WS/Products?wsdl");
        QName qname = new QName("http://labsd/", "SOAPImplService");

        Service service = Service.create(url, qname);
        SOAP soap = service.getPort(SOAP.class);

        Scanner scanner = new Scanner(System.in);
        List<Product> purchasedProducts = new ArrayList<>();
        List<Integer> purchasedQuantities = new ArrayList<>();
        
        while (true) {
            System.out.println("Available products:");
            List<Product> products = soap.getProducts();
            for (int i = 0; i < products.size(); i++) {
                System.out.println((i + 1) + ". " + products.get(i));
            }
            System.out.println("0. Finish and print receipt");

            System.out.print("Enter the number of the product to purchase: ");
            int productNumber = scanner.nextInt();

            if (productNumber == 0) {
                break;
            } else if (productNumber > 0 && productNumber <= products.size()) {
                System.out.print("Enter the quantity to purchase: ");
                int quantity = scanner.nextInt();
                Product selectedProduct = products.get(productNumber - 1);

                String purchaseResponse = soap.purchaseProduct(selectedProduct.name, quantity);
                System.out.println(purchaseResponse);

                if (purchaseResponse.startsWith("Purchase successful")) {
                    purchasedProducts.add(selectedProduct);
                    purchasedQuantities.add(quantity);
                }
            } else {
                System.out.println("Invalid product number.");
            }
        }
        scanner.close();

        printReceipt(purchasedProducts, purchasedQuantities);
    }

    private static void printReceipt(List<Product> purchasedProducts, List<Integer> purchasedQuantities) {
        double totalCost = 0;
        System.out.println("\nReceipt:");
        System.out.println("--------------------------------------------------");
        System.out.printf("%-20s %-10s %-10s %-10s%n", "Product", "Quantity", "Unit Price", "Total Price");
        System.out.println("--------------------------------------------------");

        for (int i = 0; i < purchasedProducts.size(); i++) {
            Product product = purchasedProducts.get(i);
            int quantity = purchasedQuantities.get(i);
            double totalPrice = product.price * quantity;
            totalCost += totalPrice;

            System.out.printf("%-20s %-10d %-10.2f %-10.2f%n", product.name, quantity, product.price, totalPrice);
        }
        System.out.println("--------------------------------------------------");
        System.out.printf("%-40s %-10.2f%n", "Total Cost:", totalCost);
    }
}
