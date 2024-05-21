import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CreditCardSystem extends Remote {
    //verifica si la tarjeta ingresada como parametro es valida
    boolean validateCard(String cardNumber) throws RemoteException;

    //devuelve el balance o la cantidad con la que se cuenta en la tarjeta
    double getBalance(String cardNumber) throws RemoteException;

    //devuelve true en caso el cargo se haya realizado correctamente
    boolean charge(String cardNumber, double amount) throws RemoteException;
}
