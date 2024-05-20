package Anexo1;

import java.rmi.Remote;
//Metodos parainvocar en Medicine.java
public interface MedicineInterface extends Remote {
	public Medicine getMedicine( int amount ) throws Exception;//Nombre de medicina
	public int getStock() throws Exception;//Stock de medicina
	public String print() throws Exception;//Imprimir informacion
}
