public class Cliente {
	//Atributo nombre para guardar al cliente
	private String nombre;
	//Arreglo para almacenar productos en el carrito
	private int[] carroCompra;
	
	//constructor por default
	public Cliente() {
	}
	
	//Inicializador de los atributos
	public Cliente(String nombre, int[] carroCompra) {
		this.nombre = nombre;
		this.carroCompra = carroCompra;
	}
	
	//Get's and Set's de los atributos
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int[] getCarroCompra() {
		return carroCompra;
	}
	
	public void setCarroCompra(int[] carroCompra) {
		this.carroCompra = carroCompra;
	}
}
