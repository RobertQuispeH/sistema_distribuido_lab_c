import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class CreditCardSystemImpl extends UnicastRemoteObject implements CreditCardSystem {
    private Map<String, Double> cardDatabase;

    protected CreditCardSystemImpl() throws RemoteException {
        super();
        cardDatabase = new HashMap<>();
        cardDatabase.put("1234-5678-9101-1121", 1000.0);
        cardDatabase.put("4321-8765-1011-1211", 500.0);
        cardDatabase.put("4321-8765-1011-1211", 500.0);
        cardDatabase.put("5678-1234-1111-2222", 750.0);
        cardDatabase.put("8765-4321-3333-4444", 300.0);
        cardDatabase.put("1111-2222-3333-4444", 1200.0);
        cardDatabase.put("2222-3333-4444-5555", 450.0);
        cardDatabase.put("3333-4444-5555-6666", 800.0);
        cardDatabase.put("4444-5555-6666-7777", 600.0);
    }

    public boolean validateCard(String cardNumber) throws RemoteException {
        return cardDatabase.containsKey(cardNumber);
    }

    public double getBalance(String cardNumber) throws RemoteException {
        if (validateCard(cardNumber)) {
            return cardDatabase.get(cardNumber);
        }
        return -1;
    }

    public boolean charge(String cardNumber, double amount) throws RemoteException {
        if (validateCard(cardNumber)) {
            double balance = cardDatabase.get(cardNumber);
            if (balance >= amount) {
                cardDatabase.put(cardNumber, balance - amount);
                return true;
            }
        }
        return false;
    }
}
