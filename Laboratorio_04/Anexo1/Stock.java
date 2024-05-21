package Anexo1;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

/**
 * Clase Stock que maneja el inventario de medicinas.
 */
public class Stock extends UnicastRemoteObject implements StockInterface {
    private HashMap<String, MedicineInterface> medicines = new HashMap<>(); // Has map para almacenar las medicinas creadas

    // Constructor para serializacion con RMI
    public Stock() throws RemoteException {
        super();
    }
    // Metodo para añadir las medicinas creadas
    @Override
    public void addMedicine(String name, float price, int stock) throws RemoteException {
        medicines.put(name, new Medicine(name, price, stock)); // añadelo al HasMap
    }

    
}
