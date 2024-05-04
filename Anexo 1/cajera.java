public class cajera {

	//Atributo que alamcena el nombre de la cajera
	private String nombre;
	//Constructor por deafult
	public cajera() {
	}
	//Constructor que inicaliza los atributos
	public cajera(String nombre) {
		this.nombre = nombre;
	}

	//Get y Set del atributo nombre
	public String getNombre() {
		return nombre;
	}
		
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	//Este metodo simula el proceso de compra de un cliente por parte de la cajera
	public void procesarCompra(Cliente cliente, long timeStamp) {
		System.out.println("La cajera " + this.nombre +

		" COMIENZA A PROCESAR LA COMPRA DEL CLIENTE " + cliente.getNombre() +
		" EN EL TIEMPO: " + (System.currentTimeMillis() - timeStamp) / 1000 +
		"seg");
		//Itera en los productos que tiene el cliente
		for (int i = 0; i < cliente.getCarroCompra().length; i++) {
			// Procesa cada producto e imprime el tiempo que tarda en hacerlo.
			this.esperarXsegundos(cliente.getCarroCompra()[i]);
			System.out.println("Procesado el producto " + (i + 1) +
		
			" ->Tiempo: " + (System.currentTimeMillis() - timeStamp) / 1000 +
			"seg");
		}
		System.out.println("La cajera " + this.nombre + " HA TERMINADO DE PROCESAR " + cliente.getNombre() + " EN EL TIEMPO: " + (System.currentTimeMillis() - timeStamp) / 1000 + "seg");
	}
	// Método privado para simular una espera en una cantidad de segundos especifica.
	private void esperarXsegundos(int segundos) {
		try {
			Thread.sleep(segundos * 1000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

}
