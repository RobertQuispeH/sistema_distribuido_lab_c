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
    
    //Metodo que es para solicitar medicina y comprarla
    @Override
    public MedicineInterface buyMedicine(String name, int amount) throws RemoteException, StockException {
        MedicineInterface medicine = medicines.get(name); //Indicamos que se debe buscar por su nombre
        if (medicine == null) {
            throw new StockException("Unable to find " + name); // Si no hay nada entonces lanza la exepcion
        }
        return medicine.getMedicine(amount); //Pero si esta registrada la medicina, es posible comprarla
    }
    //Onteenr el stock de las medicinas
    @Override
    public HashMap<String, MedicineInterface> getStockProducts() throws RemoteException {
        return this.medicines; //Siumplemente que devuelva la informacion del HashMap
    } 
}
