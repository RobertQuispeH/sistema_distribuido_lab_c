//Lo que se busca es representar cada computador y sincrinizar sus tiempos con Berkeley
public class Maquina {
    private String nombre;
    private int tiempo;

    //Constructor
    public Maquina(String nombre, int tiempo) {
        this.nombre = nombre;
        this.tiempo = tiempo;
    }
    //Get and Set de Maquina
    public String getNombre() {
        return nombre;
    }

    public int getTiempo() {
        return tiempo;
    }

    // Método para ajustar el tiempo de la computadora
    public void ajustarTiempo(int ajuste) {
        this.tiempo += ajuste;
    }
}
