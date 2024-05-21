package Anexo1;

import java.rmi.Remote;
import java.util.ArrayList;
import java.util.*;

//métodos remotos para la gestión del inventario de medicamentos.
public interface StockInterface extends Remote {
	public HashMap<String, MedicineInterface> getStockProducts() throws Exception;//HasMap de los productos
	public void addMedicine( String name, float price, int stock) throws Exception;//Añadir medicamente
	public MedicineInterface buyMedicine(String name, int amount) throws Exception;//Comprar medicamento
}
