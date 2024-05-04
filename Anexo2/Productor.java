public class Productor extends Thread {
	private CubbyHole cubbyhole; //referencia al objeto Cubbyhole en el que el productor colocorá los datos
	private int numero; //Identificador del producto

	//Constructor que recibe el objeto cubbyhole y el numero del producto.
    public Productor(CubbyHole c, int numero) {
		cubbyhole = c;
		this.numero = numero;
	}
	public void run() {
        //Ciclo para producir datos
		for (int i = 0; i < 10; i++) {
            cubbyhole.put(i);
		    System.out.println("Productor #" + this.numero + "pone:" + i);
		    try {
			    sleep((int)(Math.random() * 100));
		    }
		catch (InterruptedException e) { }
		}
	}
}