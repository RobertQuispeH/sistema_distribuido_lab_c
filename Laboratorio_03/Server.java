import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
// El servidor se ejecuta por consola
public class Server {
  // Un unico ID para la conexion
	private static int uniqueId;
	//ArrayList para listar clientes
	private ArrayList<ClientThread> al;
	//Para mostrar el tiempo actual
	private SimpleDateFormat sdf;
	// Nuemro de puerto
	private int port;
	// Verificar union con els ervidor
	private boolean keepGoing;
	// para notificar
	private String notif = " *** ";
	//constructor que recibe el puerto para escuchar conexiones como parámetro
	public Server(int port) {
		// puerto
		this.port = port;
		// formato del tiempo
		sdf = new SimpleDateFormat("HH:mm:ss");
		// un ArrayList para mantener la lista de clientes
		al = new ArrayList<ClientThread>();
	}
	//Iniciamos el servidor
	public void start() {
		keepGoing = true;
		crear socket servidor y esperar solicitudes de conexión
		try
		{
			//Socket usado por el servidor
			ServerSocket serverSocket = new ServerSocket(port);
			//bucle infinito para esperar conexiones (hasta que el servidor esté activo)
			while(keepGoing)
			{
				display("Server waiting for Clients on port " + port + ".");
				// Aceptar la conexion en caso sea correcto
				Socket socket = serverSocket.accept();
				// De lo contrario salir del server
				if(!keepGoing)
				break;
				//si el cliente está conectado, crear su hilo
				ClientThread t = new ClientThread(socket);
				//Añadirlo a la lista
				al.add(t);
				t.start();
			}
			//Intenta detener el server
			try {
				serverSocket.close();
				for(int i = 0; i < al.size(); ++i) {
					ClientThread tc = al.get(i);
					try {
						// cerrar todos los flujos de datos y el socket
						tc.sInput.close();
						tc.sOutput.close();
						tc.socket.close();
					}
					catch(IOException ioE) {
					}
				}
			}
			catch(Exception e) {
				display("Exception closing the server and clients: " + e);
			}
		}
		catch (IOException e) {
		
			String msg = sdf.format(new Date()) + " Exception on new ServerSocket: " + e + "\n";
			
			display(msg);
		}
	}
	// detenemos el servidor
	protected void stop() {
		keepGoing = false;
		try {
			new Socket("localhost", port);
		}
		catch(Exception e) {
		}
	}
	//Mostrar un evento en la consola
	private void display(String msg) {
		String time = sdf.format(new Date()) + " " + msg;
		System.out.println(time);
	}
	
	// para transmitir un mensaje a todos los clientes
	private synchronized boolean broadcast(String message) {
		// añadir marca de tiempo al mensaje
		String time = sdf.format(new Date());
		//para comprobar si el mensaje es privado, es decir, mensaje de cliente a cliente
		String[] w = message.split(" ",3);
		boolean isPrivate = false;
		if(w[1].charAt(0)=='@')
			isPrivate=true;
		// si es un mensaje privado, enviar el mensaje solo al nombre de usuario mencionado
		if(isPrivate==true)
		{
			String tocheck=w[1].substring(1, w[1].length());
			message=w[0]+w[2];
			String messageLf = time + " " + message + "\n";
			boolean found=false;
			// bucle en orden inverso para encontrar el nombre de usuario mencionado
			for(int y=al.size(); --y>=0;)
			{
				ClientThread ct1=al.get(y);
				String check=ct1.getUsername();
				if(check.equals(tocheck))
				{
					//intentar escribir en el cliente, si falla, eliminarlo de la lista
					if(!ct1.writeMsg(messageLf)) {
						al.remove(y);
						display("Disconnected Client " + ct1.username + " removed from list.");
					
					}
					//nombre de usuario encontrado y entregado el mensaje
					found=true;
					break;
				}
			
			}
			//nombre de usuario mencionado no encontrado, devolver false
			if(found!=true)
			{
				return false;
			}
		}
		//si el mensaje es un mensaje de difusión
		else
		{
			String messageLf = time + " " + message + "\n";
			//mostrar mensaje
			System.out.print(messageLf);
			//bucle en orden inverso en caso de que tengamos que eliminar un Cliente
          		//porque se ha desconectado
			for(int i = al.size(); --i >= 0;) {
				ClientThread ct = al.get(i);
				//intentar escribir en el cliente, si falla, eliminarlo de la lista
				if(!ct.writeMsg(messageLf)) {
					al.remove(i);
					display("Disconnected Client " + ct.username + " removed from list.");
				}
			}
		}
		return true;
	}
  "......"
}
