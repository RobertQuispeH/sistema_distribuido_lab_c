public class MainThread {
    public static void main(String[] args) {
        
        Cliente cliente1 = new Cliente("Cliente 1", new int[]{5, 2, 3, 4});
        Cliente cliente2 = new Cliente("Cliente 2", new int[]{2, 1, 5, 3});

        
        long tiempoInicial = System.currentTimeMillis();

        
        CajeraThread cajera1 = new CajeraThread("Cajera 1", cliente1, tiempoInicial);
        CajeraThread cajera2 = new CajeraThread("Cajera 2", cliente2, tiempoInicial);

        cajera1.start();
        cajera2.start();
        
    }
}