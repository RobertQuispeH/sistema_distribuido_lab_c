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
  // if client sent LOGOUT message to exit
	synchronized void remove(int id) {
		String disconnectedClient = "";
		// scan the array list until we found the Id
		for (int i = 0; i < al.size(); ++i) {
			ClientThread ct = al.get(i);
			// if found remove it
			if (ct.id == id) {
				disconnectedClient = ct.getUsername();
				al.remove(i);
				break;
			}
		}
		broadcast(notif + disconnectedClient + " has left the chat room." + notif);
	}

	public static void main(String[] args) {
		// start server on port 1500 unless a PortNumber is specified
		int portNumber = 1500;
		switch (args.length) {
		case 1:
			try {
				portNumber = Integer.parseInt(args[0]);
			} catch (Exception e) {
				System.out.println("Invalid port number.");
				System.out.println("Usage is: > java Server [portNumber]");
				return;
			}
		case 0:
			break;
		default:
			System.out.println("Usage is: > java Server [portNumber]");
			return;
		}
		// create a server object and start it
		Server server = new Server(portNumber);
		server.start();
	}

	// One instance of this thread will run for each client
	class ClientThread extends Thread {
		// the socket to get messages from client
		Socket socket;
		ObjectInputStream sInput;
		ObjectOutputStream sOutput;
		// my unique id (easier for deconnection)
		int id;
		// the Username of the Client
		String username;
		// message object to recieve message and its type
		ChatMessage cm;
		// timestamp
		String date;

		// Constructor
		ClientThread(Socket socket) {
			// a unique id
			id = ++uniqueId;
			this.socket = socket;
			// Creating both Data Stream
			System.out.println("Thread trying to create Object Input/Output Streams");
			try {
				sOutput = new ObjectOutputStream(socket.getOutputStream());
				sInput = new ObjectInputStream(socket.getInputStream());
				// read the username
				username = (String) sInput.readObject();
				broadcast(notif + username + " has joined the chat room." + notif);
			} catch (IOException e) {
				display("Exception creating new Input/output Streams: " + e);
				return;
			} catch (ClassNotFoundException e) {
			}
			date = new Date().toString() + "\n";
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}
	
		public void run() {
			// to loop until LOGOUT
			boolean keepGoing = true;
			while (keepGoing) {
				// read a String (which is an object)
				try {
					cm = (ChatMessage) sInput.readObject();
				} catch (IOException e) {
					display(username + " Exception reading Streams: " + e);
					break;
				} catch (ClassNotFoundException e2) {
					break;
				}
				// get the message from the ChatMessage object received
				String message = cm.getMessage();
				// different actions based on type message
				switch (cm.getType()) {
				case ChatMessage.MESSAGE:
					boolean confirmation = broadcast(username + ": " + message);
					if (confirmation == false) {
						String msg = notif + "Sorry. No such user exists." + notif;
						writeMsg(msg);
					}
					break;
				case ChatMessage.LOGOUT:
					display(username + " disconnected with a LOGOUT message.");
					keepGoing = false;
					break;
				case ChatMessage.WHOISIN:
					writeMsg("List of the users connected at " + sdf.format(new Date()) + "\n");
					// send list of active clients
					for (int i = 0; i < al.size(); ++i) {
						ClientThread ct = al.get(i);
						writeMsg((i + 1) + ") " + ct.username + " since " + ct.date);
					}
					break;
				}
			}
			// if out of the loop then disconnected and remove from client list
			remove(id);
			close();
		}
		// close everything
		private void close() {
			try {
				if (sOutput != null)
					sOutput.close();
			} catch (Exception e) {
			}
			try {
				if (sInput != null)
					sInput.close();
			} catch (Exception e) {
			}
			;
			try {
				if (socket != null)
					socket.close();
			} catch (Exception e) {
			}
		}

		// write a String to the Client output stream
		private boolean writeMsg(String msg) {
			// if Client is still connected send the message to it
			if (!socket.isConnected()) {
				close();
				return false;
			}
			// write the message to the stream
			try {
				sOutput.writeObject(msg);
			}
			// if an error occurs, do not abort just inform the user
			catch (IOException e) {
				display(notif + "Error sending message to " + username + notif);
				display(e.toString());
			}
			return true;
		}
	}
}
