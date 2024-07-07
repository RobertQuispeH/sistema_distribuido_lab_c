import socket
import threading
import tkinter as tk
from tkinter import scrolledtext, Entry, Button, END

# Configuración del cliente
HOST = "127.0.0.1"  # Dirección IP del servidor
PORT = 12345  # Puerto del servidor

# Variables globales para el nombre de usuario y estado de conexión
nickname = ""
connected = False


# Función para enviar mensajes al servidor
def send_message(chat_text):
    def inner_send():
        global connected
        message = message_entry.get().strip()

        if not connected:
            # Enviar el nombre de usuario al servidor al presionar Enviar por primera vez
            if message:
                client_socket.send(message.encode())
                chat_text.insert(tk.END, f"Te has unido como {message}\n")
                chat_text.tag_config("system", justify="center")
                chat_text.see(tk.END)  # Hacer scroll hasta el final del texto
                message_entry.delete(0, tk.END)
                connected = True
        else:
            # Enviar mensajes normales al chat
            if message:
                client_socket.send(message.encode())
                chat_text.tag_config("right", justify="right")
                chat_text.insert(tk.END, "Yo: " + message + "\n", "right")
                chat_text.see(tk.END)  # Hacer scroll hasta el final del texto
                message_entry.delete(0, tk.END)

    return inner_send


# Función para manejar la recepción de mensajes del servidor
def receive_messages():
    global connected
    while True:
        try:
            message = client_socket.recv(1024).decode()
            if message:
                if "Te has unido como" in message:
                    connected = True
                chat_text.insert(tk.END, message + "\n")
                chat_text.see(tk.END)  # Hacer scroll hasta el final del texto
        except ConnectionAbortedError:
            break
        except:
            print("Error de conexión con el servidor.")
            break


# Configurar el socket del cliente
client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

try:
    # Conectar al servidor
    client_socket.connect((HOST, PORT))
    print(f"Conectado al servidor {HOST}:{PORT}")

    # Configurar la interfaz gráfica
    root = tk.Tk()
    root.title("Cliente de Chat")

    chat_text = scrolledtext.ScrolledText(root, width=50, height=20, wrap=tk.WORD)
    chat_text.grid(row=0, column=0, padx=10, pady=10, columnspan=2)

    message_entry = Entry(root, width=40)
    message_entry.grid(row=1, column=0, padx=10, pady=10)

    send_button = Button(root, text="Enviar", width=10, command=send_message(chat_text))
    send_button.grid(row=1, column=1, padx=10, pady=10)

    # Iniciar hilo para recibir mensajes del servidor
    receive_thread = threading.Thread(target=receive_messages)
    receive_thread.start()

    # Función para manejar el cierre de la ventana
    def on_closing():
        client_socket.close()
        root.destroy()

    root.protocol("WM_DELETE_WINDOW", on_closing)
    root.mainloop()

except socket.error as e:
    print(f"Error al conectar con el servidor: {e}")
