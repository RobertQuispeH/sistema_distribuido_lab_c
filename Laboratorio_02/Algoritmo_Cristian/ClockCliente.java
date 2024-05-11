import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;

public class ClockCliente {
	public static void main(String []args)throws IOException{
        String port, hostName; // Nombre del puerto y nombre del host
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Ingresar el numero del puerto");
        port = stdIn.readLine();

        int portNumber = Integer.parseInt(port);
        System.out.println("Ingrese nombre del host");
        hostName = stdIn.readLine();
        try(
            Socket echoSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        )
        {
            String userInput;
            System.out.println("Cliente iniciado");   
            System.out.println("Enter exit to stop");

            long TiempoCero;
            long Tiempo_Servidor;
            long TiempoUno;
            long Tiempo_final;
           
            out.println(TiempoCero = System.currentTimeMillis());
            Tiempo_Servidor = Long.parseLong(in.readLine());

            TiempoUno = System.currentTimeMillis();
            Tiempo_final = (Tiempo_Servidor + (TiempoUno - TiempoCero)/2);
            DateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");

            System.out.println("Tiempo del cliente:" + formatter.format(new Date(TiempoUno)));
            System.out.println("Tiempo del Servido: " + formatter.format(new Date(Tiempo_Servidor)));
            System.out.println("Tiempo del Cliente despues del reinicio " + formatter.format((new Date(Tiempo_final))));
            out.println("Salida");
        } catch(Exception e){
            System.out.println("Tiempo Agotado");
        }
    }
}