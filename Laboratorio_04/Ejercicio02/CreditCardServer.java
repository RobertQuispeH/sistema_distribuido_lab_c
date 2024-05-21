import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class CreditCardServer {
    public static void main(String[] args) {
        try {
            CreditCardSystemImpl cardSystem = new CreditCardSystemImpl();
            LocateRegistry.createRegistry(2099);
            Naming.rebind("CreditCardSystem", cardSystem);
            System.out.println("CreditCardSystem is ready.");
        } catch (Exception e) {
            System.err.println("CreditCardServer exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
