package Lab04Anx;

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
  
}
