import tkinter as tk
from tkinter import messagebox
from zeep import Client

wsdl = 'http://localhost:8080/servise_web_jdbc/SQLServices?WSDL'

client = Client(wsdl=wsdl)

def obtener_departamentos():
    try:
        response = client.service.departamentoAll()
        listbox_departamentos.delete(0, tk.END)
        for departamento in response:
            listbox_departamentos.insert(tk.END, f"ID: {departamento.idDpto}, Nombre: {departamento.nombre}")
    except Exception as e:
        messagebox.showerror("Error", f"Ocurrió un error al obtener los departamentos: {e}")
root = tk.Tk()
root.title("Lista de Departamentos")
btn_obtener = tk.Button(root, text="Obtener Departamentos", command=obtener_departamentos)
btn_obtener.pack(pady=10)
listbox_departamentos = tk.Listbox(root, width=50, height=20)
listbox_departamentos.pack(padx=10, pady=10)

root.mainloop()