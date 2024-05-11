public class main {
	public static void main(String[] args) {

		//Creamos dos computadoras,condiferente tiempo
		Maquina comp1 = new Maquina("Comp1", 10);
       	 	Maquina comp2 = new Maquina("Comp2", 20);

		//Llamamos al servidor
		 Servidor servidorTiempo = new Servidor();
		//Incluimos al servidor las computadoras creadas
		 servidorTiempo.agregarComputadora(comp1);
       		 servidorTiempo.agregarComputadora(comp2);

		 // Llamamos al metodo sincronizar para ejecutar Berkeley
	        servidorTiempo.sincronizarTiempo();
	
	        // Imprimimos el tiempo sincronizado.
	        servidorTiempo.imprimirTiempos();
    
	}
}
