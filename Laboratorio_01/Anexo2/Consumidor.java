public class Consumidor extends Thread{
	private CubbyHole cubbyhole;
	private int numero; //Número que identifica al consumidor}

    //Constructor que recibe un objeto cubbyhole y el numero del consumidor
	public Consumidor(CubbyHole c, int numero) {
		cubbyhole = c;
		this.numero = numero;
	}

	public void run() {
		int value = 0;
        //Ciclo para consumir datos
		for (int i = 0; i < 10; i++) {
			value = cubbyhole.get();
			System.out.println("Consumidor # "+this.numero+" obtiene: "+value);
		}
	}
}
