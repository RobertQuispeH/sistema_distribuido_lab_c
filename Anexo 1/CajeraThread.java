public class CajeraThread extends Thread{
	
	// Atributos que almacenan el nombre de la cajera, el cliente y el tiempo inicial
	private String nombre;
	private Cliente cliente;
	private long initialTime;
	//constructor por default
	public CajeraThread() {
	}
	
	//Constructor que inicializa los atributos
	public CajeraThread(String nombre, Cliente cliente, long initialTime) {
		this.nombre = nombre;
		this.cliente = cliente;
		this.initialTime = initialTime;
	}

	//Get's y Set's de los atributos
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public long getInitialTime() {
		return initialTime;
	}
	
	public void setInitialTime(long initialTime) {
		this.initialTime = initialTime;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	//Metodo run para inicilizar los hilos
	@Override
	public void run() {
		
		System.out.println("La cajera " + this.nombre + " COMIENZA A PROCESAR LA COMPRA DEL CLIENTE "
		+ this.cliente.getNombre() + " EN EL TIEMPO: "
		+ (System.currentTimeMillis() - this.initialTime) / 1000
		+ "seg");

		 // Itera sobre los productos que estan en el carrito
		for (int i = 0; i < this.cliente.getCarroCompra().length; i++) {
			// Se procesa el pedido en X segundos
			this.esperarXsegundos(cliente.getCarroCompra()[i]);
			System.out.println("Procesado el producto " + (i + 1)
		
			+ " del cliente " + this.cliente.getNombre() + "->Tiempo: "
			+ (System.currentTimeMillis() - this.initialTime) / 1000
			+ "seg");
		}
	
		System.out.println("La cajera " + this.nombre + " HA TERMINADO DE PROCESAR "
			+ this.cliente.getNombre() + " EN EL TIEMPO: "
			+ (System.currentTimeMillis() - this.initialTime) / 1000
			+ "seg");

	}
	
	//Mismo metodo que cajera, establecer un tiempo de espera.
	private void esperarXsegundos(int segundos) {
		try {
			Thread.sleep(segundos * 1000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
	
}
