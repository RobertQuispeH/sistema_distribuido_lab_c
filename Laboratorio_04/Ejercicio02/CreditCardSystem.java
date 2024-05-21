import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CreditCardSystem extends Remote {
    
    boolean validateCard(String cardNumber) throws RemoteException;

    double getBalance(String cardNumber) throws RemoteException;

    boolean charge(String cardNumber, double amount) throws RemoteException;
}
