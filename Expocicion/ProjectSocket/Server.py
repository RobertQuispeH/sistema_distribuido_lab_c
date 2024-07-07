import socket
import threading

# Configuración del servidor
HOST = "127.0.0.1"  # Dirección IP del servidor
PORT = 12345  # Puerto del servidor
MAX_CLIENTS = 10  # Máximo de clientes permitidos

# Lista para almacenar los sockets de clientes activos y sus nombres de usuario
client_sockets = []
client_nicknames = []


# Función para enviar mensaje a todos los clientes excepto al que lo envía
def broadcast_message(message, sender_socket):
    try:
        sender_index = client_sockets.index(sender_socket)
        sender_name = client_nicknames[sender_index]
        message_to_broadcast = f"{sender_name}: {message}"

        for client_socket in client_sockets:
            if client_socket != sender_socket:
                try:
                    client_socket.send(message_to_broadcast.encode())
                except socket.error:
                    continue  # Puede ocurrir un error si el socket está cerrado

    except ValueError:
        pass  # El socket del remitente ya no está en la lista


# Función para manejar a un cliente
def handle_client(client_socket, client_address):
    print(f"Nuevo cliente conectado desde {client_address}")

    try:
        # Solicitar y almacenar el nickname del cliente
        client_socket.send("NICK".encode())
        nickname = client_socket.recv(1024).decode().strip()

        # Verificar si el nickname ya está en uso
        while nickname in client_nicknames:
            client_socket.send("NICK_IN_USE".encode())
            nickname = client_socket.recv(1024).decode().strip()

        client_nicknames.append(nickname)
        client_sockets.append(client_socket)

        # Notificar a todos los clientes sobre la nueva conexión
        broadcast_message(f"{nickname} se ha unido al chat.", client_socket)

        while True:
            message = client_socket.recv(1024).decode()
            if message:
                # Enviar el mensaje a todos los clientes, excepto al cliente actual
                broadcast_message(message, client_socket)
            else:
                remove_client(client_socket)
                break

    except ConnectionResetError:
        remove_client(client_socket)
    except ValueError:
        remove_client(client_socket)


# Función para eliminar a un cliente desconectado
def remove_client(client_socket):
    if client_socket in client_sockets:
        index = client_sockets.index(client_socket)
        nickname = client_nicknames[index]

        # Enviar mensaje de desconexión a todos los clientes
        broadcast_message(f"{nickname} ha dejado el chat.", client_socket)

        # Remover el cliente de las listas
        client_sockets.remove(client_socket)
        client_nicknames.remove(nickname)

        # Cerrar el socket del cliente
        client_socket.close()


# Configurar el socket del servidor
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind((HOST, PORT))

# Escuchar conexiones entrantes
server_socket.listen(MAX_CLIENTS)
print(f"Servidor de chat escuchando en {HOST}:{PORT}...")

# Ciclo para manejar múltiples clientes
while True:
    client_socket, client_address = server_socket.accept()
    # Iniciar un hilo para manejar el cliente
    thread = threading.Thread(
        target=handle_client, args=(client_socket, client_address)
    )
    thread.start()
