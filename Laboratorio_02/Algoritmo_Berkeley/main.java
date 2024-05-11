public class main {
	public static void main(String[] args) {
		// Creamos varias computadoras con diferentes tiempos
        Maquina comp1 = new Maquina("Comp1", 10);
        Maquina comp2 = new Maquina("Comp2", 20);
        Maquina comp3 = new Maquina("Comp3", 30);
        Maquina comp4 = new Maquina("Comp4", 40);

        // Creamos el servidor de tiempo
        Servidor servidorTiempo = new Servidor();

        // Agregamos las computadoras al servidor
        servidorTiempo.agregarComputadora(comp1);
        servidorTiempo.agregarComputadora(comp2);
        servidorTiempo.agregarComputadora(comp3);
        servidorTiempo.agregarComputadora(comp4);

        // Sincronizamos el tiempo de las computadoras usando el algoritmo de Berkeley
        servidorTiempo.sincronizarTiempo();

        // Imprimimos el tiempo sincronizado de cada computadora
        servidorTiempo.imprimirTiempos();
    
	}
}
