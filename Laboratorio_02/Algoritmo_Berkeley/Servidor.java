import java.util.ArrayList;
import java.util.List;

//Clase que representa el servidor de con elalgoritmo de Berkeley
public class Servidor {
 private List<Maquina> computadoras;

 public Servidor() {
     computadoras = new ArrayList<>();
 }

 // Método para agregar una computadora al servidor
 public void agregarComputadora(Maquina computadora) {
     computadoras.add(computadora);
 }

 // Método para calcular la desviación y ajustar el tiempo de las computadoras
 public void sincronizarTiempo() {
     int sumaDiferencias = 0;

     // Calcular la suma de las diferencias de tiempo
     for (Maquina computadora : computadoras) {
         sumaDiferencias += computadora.getTiempo();
     }

     // Calcular el tiempo promedio
     int tiempoPromedio = sumaDiferencias / computadoras.size();

     // Ajustar el tiempo de cada computadora
     for (Maquina computadora : computadoras) {
         computadora.ajustarTiempo(tiempoPromedio - computadora.getTiempo());
     }
 }

 // Método para imprimir el tiempo de cada computadora después de la sincronización
 public void imprimirTiempos() {
     System.out.println("Tiempo de las computadoras después de la sincronización:");
     for (Maquina computadora : computadoras) {
         System.out.println(computadora.getNombre() + ": " + computadora.getTiempo());
     }
 }
}
