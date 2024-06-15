import tkinter as tk
from tkinter import messagebox
from zeep import Client

# URL del WSDL del servicio web
wsdl = 'http://localhost:8080/servise_web_jdbc/SQLServices?WSDL'

# Crear el cliente SOAP
client = Client(wsdl=wsdl)
Departamento = client.get_type('ns0:departamento')

# Función para obtener todos los departamentos
def obtener_departamentos():
    try:
        response = client.service.departamentoAll()
        listbox_departamentos.delete(0, tk.END)
        for departamento in response:
            listbox_departamentos.insert(tk.END, f"ID: {departamento.idDpto}, Nombre: {departamento.nombre}, Fax: {departamento.fax}")
    except Exception as e:
        messagebox.showerror("Error", f"Ocurrió un error al obtener los departamentos: {e}")

# Función para agregar un departamento
def agregar_departamento():
    id_dpto = int(entry_id.get())
    nombre = entry_nombre.get()
    telefono = entry_telefono.get()
    fax = entry_fax.get()

    try:
        # Crear un objeto Departamento
        departamento = Departamento(idDpto=id_dpto,nombre=nombre, telefono=telefono, fax=fax)

        # Llamar al método del servicio web para agregar departamento
        if client.service.addDepartamento(departamento):
            messagebox.showinfo("Éxito", "Departamento agregado correctamente.")
            obtener_departamentos()
        else:
            messagebox.showwarning("Advertencia", "No se pudo agregar el departamento.")
    except Exception as e:
        messagebox.showerror("Error", f"Ocurrió un error al agregar el departamento: {e}")

# Función para actualizar un departamento
def actualizar_departamento():
    id_seleccionado = obtener_id_seleccionado()
    if id_seleccionado is not None:
        nombre = entry_nombre.get()
        telefono = entry_telefono.get()
        fax = entry_fax.get()

        try:
            # Crear un objeto Departamento
            departamento = Departamento(idDpto=id_seleccionado, nombre=nombre, telefono=telefono, fax=fax)

            # Llamar al método del servicio web para actualizar departamento
            if client.service.updateDepartamento(departamento):
                messagebox.showinfo("Éxito", "Departamento actualizado correctamente.")
                obtener_departamentos()
            else:
                messagebox.showwarning("Advertencia", "No se pudo actualizar el departamento.")
        except Exception as e:
            messagebox.showerror("Error", f"Ocurrió un error al actualizar el departamento: {e}")
    else:
        messagebox.showwarning("Advertencia", "Seleccione un departamento para actualizar.")

# Función para eliminar un departamento
def eliminar_departamento():
    id_seleccionado = obtener_id_seleccionado()
    if id_seleccionado is not None:
        try:
            # Llamar al método del servicio web para eliminar departamento
            if client.service.deleteDepartamento(id_seleccionado):
                messagebox.showinfo("Éxito", "Departamento eliminado correctamente.")
                obtener_departamentos()
            else:
                messagebox.showwarning("Advertencia", "No se pudo eliminar el departamento.")
        except Exception as e:
            messagebox.showerror("Error", f"Ocurrió un error al eliminar el departamento: {e}")
    else:
        messagebox.showwarning("Advertencia", "Seleccione un departamento para eliminar.")

# Función auxiliar para obtener el ID del departamento seleccionado en la lista
def obtener_id_seleccionado():
    try:
        # Obtener el elemento seleccionado en la lista
        selected_item = listbox_departamentos.get(listbox_departamentos.curselection())
        
        # Extraer el ID del departamento de la cadena seleccionada
        id_seleccionado = int(selected_item.split(',')[0].split(':')[1].strip())
        
        return id_seleccionado
    except IndexError:
        return None

# Configuración de la interfaz gráfica
root = tk.Tk()
root.title("Gestión de Departamentos")

# Frame para entrada de datos y botones de acciones
frame_datos = tk.Frame(root)
frame_datos.pack(pady=10)

# Etiquetas y campos de entrada
label_id = tk.Label(frame_datos, text="ID:")
label_id.grid(row=0, column=0, padx=5, pady=5, sticky="w")
entry_id = tk.Entry(frame_datos, width=30)
entry_id.grid(row=0, column=1, padx=5, pady=5)

label_nombre = tk.Label(frame_datos, text="Nombre:")
label_nombre.grid(row=1, column=0, padx=5, pady=5, sticky="w")
entry_nombre = tk.Entry(frame_datos, width=30)
entry_nombre.grid(row=1, column=1, padx=5, pady=5)

label_telefono = tk.Label(frame_datos, text="Teléfono:")
label_telefono.grid(row=2, column=0, padx=5, pady=5, sticky="w")
entry_telefono = tk.Entry(frame_datos, width=30)
entry_telefono.grid(row=2, column=1, padx=5, pady=5)

label_fax = tk.Label(frame_datos, text="Fax:")
label_fax.grid(row=3, column=0, padx=5, pady=5, sticky="w")
entry_fax = tk.Entry(frame_datos, width=30)
entry_fax.grid(row=3, column=1, padx=5, pady=5)

# Botones de acciones
btn_agregar = tk.Button(frame_datos, text="Agregar Departamento", command=agregar_departamento)
btn_agregar.grid(row=4, column=0, columnspan=2, pady=5)

btn_actualizar = tk.Button(frame_datos, text="Actualizar Departamento", command=actualizar_departamento)
btn_actualizar.grid(row=5, column=0, columnspan=2, pady=5)

btn_eliminar = tk.Button(frame_datos, text="Eliminar Departamento", command=eliminar_departamento)
btn_eliminar.grid(row=6, column=0, columnspan=2, pady=5)

# Lista de departamentos
listbox_departamentos = tk.Listbox(root, width=50, height=20)
listbox_departamentos.pack(padx=10, pady=10)

# Botón para obtener todos los departamentos
btn_obtener = tk.Button(root, text="Obtener Departamentos", command=obtener_departamentos)
btn_obtener.pack(pady=10)

# Ejecutar la aplicación
root.mainloop()