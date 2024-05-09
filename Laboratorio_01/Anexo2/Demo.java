public class Demo {
	public static void main(String[] args) {
		CubbyHole cub = new CubbyHole(); //Se esta creando un CubbyHole
		Consumidor cons = new Consumidor(cub,1); //Se esta creando un Consumidor
		Productor prod = new Productor(cub,1); //Se esta creando un Productor
		prod.start(); // Se inicializa el hilo de productor
		cons.start(); // Se inicializa el hilo de consumidor
	}
}