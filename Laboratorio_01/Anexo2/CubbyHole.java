public class CubbyHole {
    private int contents; //Almacena el contenido del contenedor
	private boolean available = false; //Variable que indica si el contenido esta disponible
	
    //se obtiene el contenido del contenedor
	public synchronized int get() {

        //si el contenido no esta disponible, el hilo espera
		while (available == false) {
			try {
				wait();
			} catch (InterruptedException e) { }
		}
		available = false;
		notifyAll(); // Retorna el contenido
		return contents;
	}

    //Coloca un nuevo contenido en el contenedor
	public synchronized void put(int value) {
        //si el contenido esta disponible, el hilo espera
		while (available == true) { 
			try {
				wait();
			} catch (InterruptedException e) { }
		}
		contents = value;
		available = true;
		notifyAll(); //Retorna el contenido
	}
}
