
public class Main {
    public static void main(String[] args) {
        
        cajera cajera1 = new cajera("Cajera 1");
        cajera cajera2 = new cajera("Cajera 2");

    
        Cliente cliente1 = new Cliente("Cliente 1", new int[]{5, 2, 3, 4});
        Cliente cliente2 = new Cliente("Cliente 2", new int[]{2, 1, 5, 3});

        
        long tiempoInicial = System.currentTimeMillis();

        
        cajera1.procesarCompra(cliente1, tiempoInicial);

        cajera2.procesarCompra(cliente2, tiempoInicial);
    }
}

