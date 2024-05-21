import java.net.*;
import java.io.*;
import java.util.*;

// Cliente de chat que se puede ejecutar desde la consola
public class Client {
    // Notificación
    private String notif = " *** ";
    // Para entrada/salida
    private ObjectInputStream sInput; // para leer desde el socket
    private ObjectOutputStream sOutput; // para escribir en el socket
    private Socket socket; // objeto de socket
    private String server, username; // servidor y nombre de usuario
    private int port; // puerto

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /*
     * Constructor para establecer las siguientes cosas
     * servidor: la dirección del servidor
     * puerto: el número de puerto
     * nombre de usuario: el nombre de usuario
     */
    Client(String server, int port, String username) {
        this.server = server;
        this.port = port;
        this.username = username;
    }

    /*
     * Para iniciar el chat
     */
    public boolean start() {
        // intentar conectar al servidor
        try {
            socket = new Socket(server, port);
        }
        // manejador de excepciones si falla
        catch (Exception ec) {
            display("Error al conectar al servidor: " + ec);
            return false;
        }
        String msg = "Conexión aceptada " + socket.getInetAddress() + ":" + socket.getPort();
        display(msg);
        /* Crear ambos flujos de datos */
        try {
            sInput = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException eIO) {
            display("Excepción al crear nuevos flujos de entrada/salida: " + eIO);
            return false;
        }
        // crea el hilo para escuchar desde el servidor
        new ListenFromServer().start();
        // Envía nuestro nombre de usuario al servidor, este es el único mensaje que enviaremos como String. Todos los demás mensajes serán objetos ChatMessage
        try {
            sOutput.writeObject(username);
        } catch (IOException eIO) {
            display("Excepción al iniciar sesión: " + eIO);
            disconnect();
            return false;
        }
        // éxito, informamos al llamador que funcionó
        return true;
    }

    /*
     * Para enviar un mensaje a la consola
     */
    private void display(String msg) {
        System.out.println(msg);
    }

    /*
     * Para enviar un mensaje al servidor
     */
    void sendMessage(ChatMessage msg) {
        try {
            sOutput.writeObject(msg);
        } catch (IOException e) {
            display("Excepción al escribir al servidor: " + e);
        }
    }

    /*
     * Cuando algo sale mal
     * Cierra los flujos de entrada/salida y se desconecta
     */
    private void disconnect() {
        try {
            if (sInput != null)
                sInput.close();
        } catch (Exception e) {
        }
        try {
            if (sOutput != null)
                sOutput.close();
        } catch (Exception e) {
        }

        try {

            if (socket != null)
                socket.close();
        } catch (Exception e) {
        }
    }

    /*
     * Para iniciar el Cliente en modo consola, use uno de los siguientes comandos
     * > java Client
     * > java Client username
     * > java Client username portNumber
     * > java Client username portNumber serverAddress
     * en el indicador de consola
     * Si el número de puerto no se especifica, se usa 1500
     * Si la dirección del servidor no se especifica, se usa "localhost"
     * Si el nombre de usuario no se especifica, se usa "Anónimo"
     */
    public static void main(String[] args) {
        // valores predeterminados si no se ingresan
        int portNumber = 1500;
        String serverAddress = "localhost";
        String userName = "Anónimo";
        Scanner scan = new Scanner(System.in);
        System.out.println("Ingrese el nombre de usuario: ");
        userName = scan.nextLine();
        // caso diferente según la longitud de los argumentos.
        switch (args.length) {
            case 3:
                // para > javac Client username portNumber serverAddr
                serverAddress = args[2];
            case 2:
                // para > javac Client username portNumber
                try {
                    portNumber = Integer.parseInt(args[1]);
                } catch (Exception e) {
                    System.out.println("Número de puerto no válido.");
                    System.out.println("El uso es: > java Client [username] [portNumber] [serverAddress]");
                    return;
                }
            case 1:
                // para > javac Client username
                userName = args[0];
            case 0:
                // para > java Client
                break;
            // si el número de argumentos es inválido
            default:
                System.out.println("El uso es: > java Client [username] [portNumber] [serverAddress]");
            return;
        }
        // crea el objeto Cliente
        Client client = new Client(serverAddress, portNumber, userName);
        // intenta conectarse al servidor y devuelve si no está conectado
        if (!client.start())
            return;
        System.out.println("\n¡Hola.! Bienvenido al chat.");
        System.out.println("Instrucciones:");
        System.out.println("1. Simplemente escriba el mensaje para enviarlo a todos los clientes activos");
        System.out.println("2. Escriba '@nombredeusuario<espacio>su mensaje' sin comillas para enviar el mensaje a un cliente deseado");
        System.out.println("3. Escriba 'WHOISIN' sin comillas para ver la lista de clientes activos");
        System.out.println("4. Escriba 'LOGOUT' sin comillas para desconectarse del servidor");
        // bucle infinito para obtener la entrada del usuario
        while (true) {
            System.out.print("> ");
            // leer mensaje del usuario
            String msg = scan.nextLine();
            // cerrar sesión si el mensaje es LOGOUT
            if (msg.equalsIgnoreCase("LOGOUT")) {
                client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, ""));
                break;
            }
            // mensaje para verificar quién está presente en la sala de chat
            else if (msg.equalsIgnoreCase("WHOISIN")) {
                client.sendMessage(new ChatMessage(ChatMessage.WHOISIN, ""));
            }
            // mensaje de texto regular
            else {
                client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, msg));
            }
        }
        // cerrar recursos
        scan.close();
        // el cliente completó su trabajo. desconectar cliente.
        client.disconnect();
    }

    /*
     * una clase que espera el mensaje del servidor
     */
    class ListenFromServer extends Thread {
        public void run() {
            while (true) {
                try {
                    // leer el mensaje del flujo de datos de entrada
                    String msg = (String) sInput.readObject();
                    // imprimir el mensaje
                    System.out.println(msg);
                    System.out.print("> ");
                } catch (IOException e) {
                    display(notif + "El servidor ha cerrado la conexión: " + e + notif);
                    break;
                } catch (ClassNotFoundException e2) {
                }
            }
        }
    }
}
