package Anexo1;

import java.rmi.server.UnicastRemoteObject;
/**
* Este es la claes Medicina para este proyecto solo se puede comprar y
* consultar la lista de medicinas.
*
* @author rventurar
*
*/
public class Medicine extends UnicastRemoteObject implements MedicineInterface {
	private String name; //Nombre
	private float unitPrice; // Precio por unidad
	private int stock; //Unidades disponibles

  //constructor para la serializacion del RMI
	public Medicine() throws Exception {
		super();
	}

  //Constructor para incializar atributos de Medicine
	public Medicine(String name, float price, int stock) throws Exception {
		super();
		this.name = name;
		unitPrice = price;
		this.stock = stock;
	}
	//Método para obtener una cantidad específica de medicina
	@Override
	public Medicine getMedicine(int amount) throws Exception {
   		//Verificamos si hay stock
		if (this.stock <= 0)
			throw new StockException("Stock empty"); // excepcion si esta vacio
		if (this.stock - amount < 0)
			throw new StockException("Stock not amount of medicine");//excepcion si no hay suficiente stock
		 //Reduce el stock cuando se compra la medicina.
		this.stock -= amount;
		Medicine aux = new Medicine(name, unitPrice * amount, stock);
		return aux;
	}
	 // Obtener el estado actual del stock de la medicina
	@Override
	public int getStock() throws RemoteException {
		return this.stock;
	}
	
	// Imprimir la informacion de la medicina
	@Override
	public String print() throws RemoteException {
		return this.name + "\nPrice: " + this.unitPrice + "\nStock: " + this.stock;
	}
	
  
}
