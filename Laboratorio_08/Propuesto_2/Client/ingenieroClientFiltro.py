import tkinter as tk
from tkinter import messagebox, ttk
from zeep import Client
from zeep.exceptions import Fault

# URL del servicio SOAP
url_servicio = 'http://localhost:8080/servise_web_jdbc/SQLServices?WSDL'

# Crear un cliente para el servicio SOAP
client = Client(url_servicio)

class IngenieroGUI:
    def __init__(self, root):
        self.root = root
        self.root.title("Gestión de Ingenieros")
        
        self.proyectos = self.obtener_proyectos()

        # Etiqueta y lista de ingenieros
        self.label_ingenieros = tk.Label(root, text="Ingenieros", font=("Arial", 16, "bold"))
        self.label_ingenieros.grid(row=0, column=0, padx=10, pady=10, columnspan=2)

        self.ingenieros_listbox = tk.Listbox(root, width=120, height=10)
        self.ingenieros_listbox.grid(row=2, column=0, padx=10, pady=10, rowspan=6, columnspan=3)
        self.ingenieros_listbox.bind('<<ListboxSelect>>', self.cargar_datos_seleccionados)

        # Combobox para filtrar por proyecto
        self.label_filtro = tk.Label(root, text="Filtrar por Proyecto", font=("Arial", 12))
        self.label_filtro.grid(row=1, column=0, padx=10, pady=10, sticky="e")

        self.filtro_proyecto = tk.StringVar(root)
        self.filtro_proyecto.set("Todos") 
        self.proyectos_dropdown = ttk.Combobox(root, textvariable=self.filtro_proyecto, state="readonly")
        self.proyectos_dropdown['values'] = ["Todos"] + self.proyectos
        self.proyectos_dropdown.grid(row=1, column=1, padx=10, pady=10)
        self.proyectos_dropdown.bind("<<ComboboxSelected>>", self.filtrar_ingenieros)

        # Botones para operaciones CRUD
        self.btn_mostrar = tk.Button(root, text="Mostrar Ingenieros", command=self.mostrar_ingenieros, width=20)
        self.btn_mostrar.grid(row=1, column=3, padx=10, pady=10)

        self.btn_agregar = tk.Button(root, text="Agregar Ingeniero", command=self.mostrar_formulario_agregar, width=20)
        self.btn_agregar.grid(row=2, column=3, padx=10, pady=10)

        self.btn_actualizar = tk.Button(root, text="Actualizar Ingeniero", command=self.mostrar_formulario_actualizar, width=20, state=tk.DISABLED)
        self.btn_actualizar.grid(row=3, column=3, padx=10, pady=10)

        self.btn_eliminar = tk.Button(root, text="Eliminar Ingeniero", command=self.eliminar_ingeniero_confirmacion, width=20)
        self.btn_eliminar.grid(row=4, column=3, padx=10, pady=10)

        # Frame para el formulario
        self.frame_formulario = tk.Frame(root, padx=20, pady=10)

        self.label_accion = tk.Label(self.frame_formulario, text="", font=("Arial", 12, "bold"))
        self.label_accion.grid(row=0, column=0, columnspan=2, pady=10)

        self.id_ingeniero_label = tk.Label(self.frame_formulario, text="ID Ingeniero", font=("Arial", 12))
        self.id_ingeniero_label.grid(row=1, column=0, sticky="e", padx=10, pady=5)

        self.id_ingeniero = tk.Entry(self.frame_formulario, width=40, font=("Arial", 12))
        self.id_ingeniero.grid(row=1, column=1, padx=10, pady=5)

        self.nombre_ingeniero_label = tk.Label(self.frame_formulario, text="Nombre", font=("Arial", 12))
        self.nombre_ingeniero_label.grid(row=2, column=0, sticky="e", padx=10, pady=5)

        self.nombre_ingeniero = tk.Entry(self.frame_formulario, width=40, font=("Arial", 12))
        self.nombre_ingeniero.grid(row=2, column=1, padx=10, pady=5)

        self.especialidad_ingeniero_label = tk.Label(self.frame_formulario, text="Especialidad", font=("Arial", 12))
        self.especialidad_ingeniero_label.grid(row=3, column=0, sticky="e", padx=10, pady=5)

        self.especialidad_ingeniero = tk.Entry(self.frame_formulario, width=40, font=("Arial", 12))
        self.especialidad_ingeniero.grid(row=3, column=1, padx=10, pady=5)

        self.cargo_ingeniero_label = tk.Label(self.frame_formulario, text="Cargo", font=("Arial", 12))
        self.cargo_ingeniero_label.grid(row=4, column=0, sticky="e", padx=10, pady=5)

        self.cargo_ingeniero = tk.Entry(self.frame_formulario, width=40, font=("Arial", 12))
        self.cargo_ingeniero.grid(row=4, column=1, padx=10, pady=5)

        self.nombre_proyecto_label = tk.Label(self.frame_formulario, text="Nombre Proyecto", font=("Arial", 12))
        self.nombre_proyecto_label.grid(row=5, column=0, sticky="e", padx=10, pady=5)

        # Crear caja de selección para proyectos
        self.nombre_proyecto = tk.StringVar(root)
        self.proyecto_dropdown = tk.OptionMenu(self.frame_formulario, self.nombre_proyecto, *self.proyectos, command=self.actualizar_id_proyecto)
        self.proyecto_dropdown.config(width=37, font=("Arial", 12))
        self.proyecto_dropdown.grid(row=5, column=1, padx=10, pady=5)

        self.id_proy_ingeniero_label = tk.Label(self.frame_formulario, text="ID Proyecto", font=("Arial", 12))
        self.id_proy_ingeniero_label.grid(row=6, column=0, sticky="e", padx=10, pady=5)

        self.id_proy_ingeniero = tk.Entry(self.frame_formulario, width=40, font=("Arial", 12), state=tk.DISABLED)
        self.id_proy_ingeniero.grid(row=6, column=1, padx=10, pady=5)

        self.btn_aceptar = tk.Button(self.frame_formulario, text="Aceptar", command=self.aceptar_formulario)
        self.btn_aceptar.grid(row=7, column=0, padx=5, pady=10)

        self.btn_cancelar = tk.Button(self.frame_formulario, text="Cancelar", command=self.cancelar_formulario)
        self.btn_cancelar.grid(row=7, column=1, padx=5, pady=10)

        self.frame_formulario.grid_forget()  # Ocultar el formulario al inicio

        self.mostrar_ingenieros()

    def obtener_proyectos(self):
        try:
            proyectos = client.service.proyectoAll()  # Asumiendo que tienes un método para obtener los proyectos
            return [proyecto['nombre'] for proyecto in proyectos]
        
        except Fault as e:
            messagebox.showerror("Error", f"Error al obtener la lista de proyectos:\n{e}")
            return []

    def filtrar_ingenieros(self, event=None):
        filtro = self.filtro_proyecto.get()
        self.mostrar_ingenieros(filtro)

    def mostrar_ingenieros(self, filtro=None):
        self.ingenieros_listbox.delete(0, tk.END)  # Limpiar la lista actual
        try:
            ingenieros = client.service.ingenieroAll()
            filt = self.filtro_proyecto.get()
            for ingeniero in ingenieros:
                id_ing = ingeniero['idIng']
                nombre = ingeniero['nombre']
                especialidad = ingeniero['especialidad']
                cargo = ingeniero['cargo']
                id_proy = ingeniero['idProy']
                nombre_proy = self.obtener_nombre_proyecto(id_proy)
                if filt == "Todos" or nombre_proy == filt:
                    self.ingenieros_listbox.insert(tk.END, f"ID: {id_ing} | Nombre: {nombre} | Especialidad: {especialidad} | Cargo: {cargo} | Nombre Proy: {nombre_proy}")

              
        except Fault as e:
            messagebox.showerror("Error", f"Error al obtener ingenieros:\n{e}")

    def mostrar_formulario_agregar(self):
        self.limpiar_campos()
        self.ingenieros_listbox.selection_clear(0, tk.END)  # Desactivar la selección en la lista
        self.label_accion.config(text="Agregar Ingeniero", fg="blue")
        self.frame_formulario.grid(row=10, column=0, columnspan=3, padx=10, pady=10)
        self.btn_aceptar.config(command=self.agregar_ingeniero)
        self.btn_actualizar.config(state=tk.DISABLED)
        self.btn_eliminar.config(state=tk.DISABLED)

    def mostrar_formulario_actualizar(self):
        index = self.ingenieros_listbox.curselection()
        if index:
            seleccion = self.ingenieros_listbox.get(index)
            datos = seleccion.split(" | ")
            id_ing = datos[0].split(": ")[1]
            nombre = datos[1].split(": ")[1]
            especialidad = datos[2].split(": ")[1]
            cargo = datos[3].split(": ")[1]
            nombre_proy = datos[4].split(": ")[1]

            self.id_ingeniero.delete(0, tk.END)
            self.id_ingeniero.insert(0, id_ing)
            self.nombre_ingeniero.delete(0, tk.END)
            self.nombre_ingeniero.insert(0, nombre)
            self.especialidad_ingeniero.delete(0, tk.END)
            self.especialidad_ingeniero.insert(0, especialidad)
            self.cargo_ingeniero.delete(0, tk.END)
            self.cargo_ingeniero.insert(0, cargo)
            self.nombre_proyecto.set(nombre_proy)
            self.id_proy_ingeniero.config(state=tk.NORMAL)
            self.id_proy_ingeniero.delete(0, tk.END)
            self.id_proy_ingeniero.insert(0, self.obtener_id_proyecto(nombre_proy))
            self.id_proy_ingeniero.config(state=tk.DISABLED)

            self.label_accion.config(text="Actualizar Ingeniero", fg="blue")
            self.frame_formulario.grid(row=10, column=0, columnspan=3, padx=10, pady=10)
            self.btn_aceptar.config(command=self.actualizar_ingeniero)
            self.btn_actualizar.config(state=tk.DISABLED)
            self.btn_eliminar.config(state=tk.DISABLED)

    def agregar_ingeniero(self):
        id_ing = self.id_ingeniero.get()
        nombre = self.nombre_ingeniero.get()
        especialidad = self.especialidad_ingeniero.get()
        cargo = self.cargo_ingeniero.get()
        id_proy = self.obtener_id_proyecto(self.nombre_proyecto.get())

        try:
            if nombre and especialidad and cargo and id_proy:
                ingeniero = {
                    "idIng": int(id_ing),
                    "nombre": nombre,
                    "especialidad": especialidad,
                    "cargo": cargo,
                    "idProy": int(id_proy)
                }

                if client.service.ingenieroAdd(ingeniero):
                    messagebox.showinfo("Éxito", "Ingeniero agregado correctamente.")
                    self.mostrar_ingenieros()
                    self.cancelar_formulario()
                else:
                    messagebox.showerror("Error", "No se pudo agregar el ingeniero.")
            else:
                messagebox.showerror("Error", "Por favor completa todos los campos.")
        except ValueError:
            messagebox.showerror("Error", "El ID del ingeniero y el ID del proyecto deben ser números.")
        except Fault as e:
            messagebox.showerror("Error", f"Error al agregar ingeniero:\n{e}")

    def actualizar_ingeniero(self):
        id_ing = self.id_ingeniero.get()
        nombre = self.nombre_ingeniero.get()
        especialidad = self.especialidad_ingeniero.get()
        cargo = self.cargo_ingeniero.get()
        id_proy = self.obtener_id_proyecto(self.nombre_proyecto.get())

        try:
            if id_ing and nombre and especialidad and cargo and id_proy:
                ingeniero = {
                    "idIng": int(id_ing),
                    "nombre": nombre,
                    "especialidad": especialidad,
                    "cargo": cargo,
                    "idProy": int(id_proy)
                }

                if client.service.ingenieroUpdate(ingeniero):
                    messagebox.showinfo("Éxito", "Ingeniero actualizado correctamente.")
                    self.mostrar_ingenieros()
                    self.cancelar_formulario()
                else:
                    messagebox.showerror("Error", "No se pudo actualizar el ingeniero.")
            else:
                messagebox.showerror("Error", "Por favor completa todos los campos.")
        except ValueError:
            messagebox.showerror("Error", "El ID del ingeniero y el ID del proyecto deben ser números.")
        except Fault as e:
            messagebox.showerror("Error", f"Error al actualizar ingeniero:\n{e}")

    def eliminar_ingeniero_confirmacion(self):
        id_ing = self.obtener_id_seleccionado()

        try:
            if id_ing:
                if messagebox.askyesno("Confirmación", "¿Estás seguro que deseas eliminar este ingeniero?"):
                    if client.service.ingenieroDelete(int(id_ing)):
                        messagebox.showinfo("Éxito", "Ingeniero eliminado correctamente.")
                        self.mostrar_ingenieros()
                        self.cancelar_formulario()
                    else:
                        messagebox.showerror("Error", "No se pudo eliminar el ingeniero.")
            else:
                messagebox.showerror("Error", "Por favor ingresa el ID del ingeniero a eliminar.")
        except ValueError:
            messagebox.showerror("Error", "El ID del ingeniero debe ser un número.")
        except Fault as e:
            messagebox.showerror("Error", f"Error al eliminar ingeniero:\n{e}")


    def obtener_id_seleccionado(self):
        try:
            index = self.ingenieros_listbox.curselection()
            if index:
                ingeniero_seleccionado = self.ingenieros_listbox.get(index)
                id_ingeniero = ingeniero_seleccionado.split()[1]  # Obtener el ID del ingeniero seleccionado
                return id_ingeniero
            else:
                return None
        except IndexError:
            return None

    def aceptar_formulario(self):
        pass

    def cancelar_formulario(self):
        self.frame_formulario.grid_forget()
        self.btn_actualizar.config(state=tk.NORMAL)
        self.btn_eliminar.config(state=tk.NORMAL)

    def limpiar_campos(self):
        self.id_ingeniero.delete(0, tk.END)
        self.nombre_ingeniero.delete(0, tk.END)
        self.especialidad_ingeniero.delete(0, tk.END)
        self.cargo_ingeniero.delete(0, tk.END)
        self.nombre_proyecto.set("")
        self.id_proy_ingeniero.config(state=tk.NORMAL)
        self.id_proy_ingeniero.delete(0, tk.END)
        self.id_proy_ingeniero.config(state=tk.DISABLED)

    def cargar_datos_seleccionados(self, event):
        index = self.ingenieros_listbox.curselection()
        if index:
            self.btn_actualizar.config(state=tk.NORMAL)
            self.btn_eliminar.config(state=tk.NORMAL)
        else:
            self.btn_actualizar.config(state=tk.DISABLED)
            self.btn_eliminar.config(state=tk.DISABLED)

    def obtener_nombre_proyecto(self, id_proyecto):
        try:
            proyectos = client.service.proyectoAll()
            for proyecto in proyectos:
                if proyecto['idProy'] == id_proyecto:
                    
                    return proyecto['nombre']
            return None
        except Fault as e:
            messagebox.showerror("Error", f"Error al obtener nombre del proyecto:\n{e}")
            return None


    def obtener_id_proyecto(self, nombre_proy):
        try:
            proyectos = client.service.proyectoAll()
            for proyecto in proyectos:
                if proyecto['nombre'] == nombre_proy:
                    return proyecto['idProy']
            return ""
        except Fault as e:
            messagebox.showerror("Error", f"Error al obtener el ID del proyecto:\n{e}")
            return ""

    def actualizar_id_proyecto(self, nombre_proy):
        self.id_proy_ingeniero.config(state=tk.NORMAL)
        self.id_proy_ingeniero.delete(0, tk.END)
        self.id_proy_ingeniero.insert(0, self.obtener_id_proyecto(nombre_proy))
        self.id_proy_ingeniero.config(state=tk.DISABLED)

if __name__ == "__main__":
    root = tk.Tk()
    app = IngenieroGUI(root)
    root.mainloop()
