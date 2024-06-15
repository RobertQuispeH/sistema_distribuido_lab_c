import tkinter as tk
from tkinter import messagebox
from tkinter import simpledialog
from tkinter import ttk  # Importar ttk para el Combobox
from zeep import Client
from zeep.exceptions import Fault
from datetime import datetime

# URL del servicio SOAP
url_servicio = 'http://localhost:8080/servise_web_jdbc/SQLServices?WSDL'

# Crear un cliente para el servicio SOAP
client = Client(url_servicio)

class ProyectoGUI:
    def __init__(self, root):
        self.root = root
        self.root.title("Gestión de Proyectos")

        # Obtener lista de departamentos
        self.departamentos = self.obtener_departamentos()

        # Combobox para filtrar por departamento
        self.label_filtrar = tk.Label(root, text="Filtrar por Departamento:", font=("Arial", 12))
        self.label_filtrar.grid(row=0, column=0, padx=10, pady=10)

        self.departamento_seleccionado = tk.StringVar(root)
        self.departamento_seleccionado.set("Todos")  # Opción inicial
        self.combo_departamentos = ttk.Combobox(root, width=30, textvariable=self.departamento_seleccionado)
        self.combo_departamentos['values'] = ["Todos"] + self.departamentos
        self.combo_departamentos.grid(row=0, column=1, padx=10, pady=10)
        self.combo_departamentos.bind("<<ComboboxSelected>>", self.filtrar_proyectos)

        # Etiqueta y lista de proyectos
        self.label_proyectos = tk.Label(root, text="Proyectos", font=("Arial", 16, "bold"))
        self.label_proyectos.grid(row=1, column=0, padx=10, pady=10, columnspan=2)

        self.proyectos_listbox = tk.Listbox(root, width=120, height=10)
        self.proyectos_listbox.grid(row=2, column=0, padx=10, pady=10, rowspan=6, columnspan=3)
        self.proyectos_listbox.bind('<<ListboxSelect>>', self.cargar_datos_seleccionados)

        # Botones para operaciones CRUD
        self.btn_mostrar = tk.Button(root, text="Mostrar Proyectos", command=self.mostrar_proyectos, width=20)
        self.btn_mostrar.grid(row=2, column=3, padx=10, pady=10)

        self.btn_agregar = tk.Button(root, text="Agregar Proyecto", command=self.mostrar_formulario_agregar, width=20)
        self.btn_agregar.grid(row=3, column=3, padx=10, pady=10)

        self.btn_actualizar = tk.Button(root, text="Actualizar Proyecto", command=self.mostrar_formulario_actualizar, width=20, state=tk.DISABLED)
        self.btn_actualizar.grid(row=4, column=3, padx=10, pady=10)

        self.btn_eliminar = tk.Button(root, text="Eliminar Proyecto", command=self.eliminar_proyecto_confirmacion, width=20)
        self.btn_eliminar.grid(row=5, column=3, padx=10, pady=10)

        # Frame para el formulario
        self.frame_formulario = tk.Frame(root, padx=20, pady=10)

        self.label_accion = tk.Label(self.frame_formulario, text="", font=("Arial", 12, "bold"))
        self.label_accion.grid(row=0, column=0, columnspan=2, pady=10)

        self.id_proyecto_label = tk.Label(self.frame_formulario, text="ID Proyecto", font=("Arial", 12))
        self.id_proyecto_label.grid(row=1, column=0, sticky="e", padx=10, pady=5)

        self.id_proyecto = tk.Entry(self.frame_formulario, width=40, font=("Arial", 12))
        self.id_proyecto.grid(row=1, column=1, padx=10, pady=5)

        self.nombre_proyecto_label = tk.Label(self.frame_formulario, text="Nombre", font=("Arial", 12))
        self.nombre_proyecto_label.grid(row=2, column=0, sticky="e", padx=10, pady=5)

        self.nombre_proyecto = tk.Entry(self.frame_formulario, width=40, font=("Arial", 12))
        self.nombre_proyecto.grid(row=2, column=1, padx=10, pady=5)

        self.fec_inicio_proyecto_label = tk.Label(self.frame_formulario, text="Fecha Inicio (YYYY-MM-DD)", font=("Arial", 12))
        self.fec_inicio_proyecto_label.grid(row=3, column=0, sticky="e", padx=10, pady=5)

        self.fec_inicio_proyecto = tk.Entry(self.frame_formulario, width=40, font=("Arial", 12))
        self.fec_inicio_proyecto.grid(row=3, column=1, padx=10, pady=5)

        self.fec_termino_proyecto_label = tk.Label(self.frame_formulario, text="Fecha Término (YYYY-MM-DD)", font=("Arial", 12))
        self.fec_termino_proyecto_label.grid(row=4, column=0, sticky="e", padx=10, pady=5)

        self.fec_termino_proyecto = tk.Entry(self.frame_formulario, width=40, font=("Arial", 12))
        self.fec_termino_proyecto.grid(row=4, column=1, padx=10, pady=5)

        self.nombre_dpto_proyecto_label = tk.Label(self.frame_formulario, text="Nombre Departamento", font=("Arial", 12))
        self.nombre_dpto_proyecto_label.grid(row=5, column=0, sticky="e", padx=10, pady=5)

        # Crear caja de selección para departamentos
        self.nombre_dpto_proyecto = tk.StringVar(root)
        self.nombre_dpto_proyecto.set("")  # Valor inicial
        self.dpto_dropdown = tk.OptionMenu(self.frame_formulario, self.nombre_dpto_proyecto, *self.departamentos, command=self.actualizar_id_departamento)
        self.dpto_dropdown.config(width=37, font=("Arial", 12))
        self.dpto_dropdown.grid(row=5, column=1, padx=10, pady=5)

        self.id_dpto_proyecto_label = tk.Label(self.frame_formulario, text="ID Departamento", font=("Arial", 12))
        self.id_dpto_proyecto_label.grid(row=6, column=0, sticky="e", padx=10, pady=5)

        self.id_dpto_proyecto = tk.Entry(self.frame_formulario, width=40, font=("Arial", 12), state=tk.DISABLED)
        self.id_dpto_proyecto.grid(row=6, column=1, padx=10, pady=5)

        self.btn_aceptar = tk.Button(self.frame_formulario, text="Aceptar", command=self.aceptar_formulario)
        self.btn_aceptar.grid(row=7, column=0, padx=5, pady=10)

        self.btn_cancelar = tk.Button(self.frame_formulario, text="Cancelar", command=self.cancelar_formulario)
        self.btn_cancelar.grid(row=7, column=1, padx=5, pady=10)

        self.frame_formulario.grid_forget()  # Ocultar el formulario al inicio

        self.mostrar_proyectos()

    def mostrar_formulario_agregar(self):
        self.limpiar_campos()
        self.proyectos_listbox.selection_clear(0, tk.END)  # Desactivar la selección en la lista
        self.label_accion.config(text="Agregar Proyecto", fg="blue")
        self.frame_formulario.grid(row=10, column=0, columnspan=3, padx=10, pady=10)
        self.btn_aceptar.config(command=self.agregar_proyecto)
        self.btn_actualizar.config(state=tk.DISABLED)
        self.btn_eliminar.config(state=tk.DISABLED)

    def mostrar_formulario_actualizar(self):
        index = self.proyectos_listbox.curselection()
        if index:
            proyecto_seleccionado = self.proyectos_listbox.get(index)
            datos = proyecto_seleccionado.split('|')
            id_proyecto = datos[0].split(':')[1].strip()
            nombre = datos[1].split(':')[1].strip()
            fec_inicio = datos[2].split(':')[1].strip()
            fec_termino = datos[3].split(':')[1].strip()
            nombre_dpto = datos[4].split(':')[1].strip()

            self.id_proyecto.delete(0, tk.END)
            self.id_proyecto.insert(tk.END, id_proyecto)
            self.nombre_proyecto.delete(0, tk.END)
            self.nombre_proyecto.insert(tk.END, nombre)
            self.fec_inicio_proyecto.delete(0, tk.END)
            self.fec_inicio_proyecto.insert(tk.END, fec_inicio)
            self.fec_termino_proyecto.delete(0, tk.END)
            self.fec_termino_proyecto.insert(tk.END, fec_termino)
            self.nombre_dpto_proyecto.set(nombre_dpto)

            self.label_accion.config(text="Actualizar Proyecto", fg="green")
            self.frame_formulario.grid(row=10, column=0, columnspan=3, padx=10, pady=10)
            self.btn_aceptar.config(command=self.actualizar_proyecto)
            self.btn_actualizar.config(state=tk.DISABLED)
            self.btn_eliminar.config(state=tk.DISABLED)

    def aceptar_formulario(self):
        if self.validar_campos():
            self.agregar_proyecto()  # Llamar a la función para agregar o actualizar según el estado del formulario

    def cancelar_formulario(self):
        self.limpiar_campos()
        self.frame_formulario.grid_forget()
        self.label_accion.config(text="")
        self.btn_aceptar.config(command=self.aceptar_formulario)

    def mostrar_proyectos(self):
        self.proyectos_listbox.delete(0, tk.END)
        try:
            proyectos = client.service.proyectoAll()

            # Obtener el departamento seleccionado del Combobox
            departamento_seleccionado = self.departamento_seleccionado.get()

            for proyecto in proyectos:
                id_proy = proyecto['idProy']
                nombre = proyecto['nombre']
                fec_inicio = proyecto['fecInicio'].strftime("%Y-%m-%d")
                fec_termino = proyecto['fecTermino'].strftime("%Y-%m-%d")
                id_dpto = proyecto['idDpto']
                nombre_dpto = self.obtener_nombre_departamento(id_dpto)

                # Aplicar filtro por departamento si está seleccionado
                if departamento_seleccionado == "Todos" or nombre_dpto == departamento_seleccionado:
                    self.proyectos_listbox.insert(tk.END, f"ID: {id_proy} | Nombre: {nombre} | Fec. Inicio: {fec_inicio} | Fec. Termino: {fec_termino} | Nombre Dpto: {nombre_dpto}")
        except Fault as e:
            messagebox.showerror("Error", f"Error al obtener proyectos:\n{e}")

    def filtrar_proyectos(self, event):
        # Al seleccionar un departamento en el Combobox, se actualiza el listado de proyectos
        self.mostrar_proyectos()

    def agregar_proyecto(self):
        id_proy = self.id_proyecto.get()
        nombre = self.nombre_proyecto.get()
        fec_inicio = self.fec_inicio_proyecto.get()
        fec_termino = self.fec_termino_proyecto.get()
        id_dpto = self.obtener_id_departamento(self.nombre_dpto_proyecto.get())

        try:
            if nombre and fec_inicio and fec_termino and id_dpto:
                fec_inicio_date = datetime.strptime(fec_inicio, "%Y-%m-%d").date()
                fec_termino_date = datetime.strptime(fec_termino, "%Y-%m-%d").date()

                proyecto = {
                    "idProy": int(id_proy),
                    "nombre": nombre,
                    "fecInicio": fec_inicio_date,
                    "fecTermino": fec_termino_date,
                    "idDpto": int(id_dpto)
                }

                if client.service.proyectoAdd(proyecto):
                    messagebox.showinfo("Éxito", "Proyecto agregado correctamente.")
                    self.mostrar_proyectos()
                    self.cancelar_formulario()
                else:
                    messagebox.showerror("Error", "No se pudo agregar el proyecto.")
            else:
                messagebox.showerror("Error", "Por favor completa todos los campos.")
        except ValueError:
            messagebox.showerror("Error", "Formato de fecha incorrecto (debe ser YYYY-MM-DD).")
        except Fault as e:
            messagebox.showerror("Error", f"Error al agregar proyecto:\n{e}")

    def actualizar_proyecto(self):
        id_proy = self.id_proyecto.get()
        nombre = self.nombre_proyecto.get()
        fec_inicio = self.fec_inicio_proyecto.get()
        fec_termino = self.fec_termino_proyecto.get()
        id_dpto = self.obtener_id_departamento(self.nombre_dpto_proyecto.get())

        try:
            if id_proy and nombre and fec_inicio and fec_termino and id_dpto:
                fec_inicio_date = datetime.strptime(fec_inicio, "%Y-%m-%d").date()
                fec_termino_date = datetime.strptime(fec_termino, "%Y-%m-%d").date()

                proyecto = {
                    "idProy": int(id_proy),
                    "nombre": nombre,
                    "fecInicio": fec_inicio_date,
                    "fecTermino": fec_termino_date,
                    "idDpto": int(id_dpto)
                }

                if client.service.proyectoUpdate(proyecto):
                    messagebox.showinfo("Éxito", "Proyecto actualizado correctamente.")
                    self.mostrar_proyectos()
                    self.cancelar_formulario()
                else:
                    messagebox.showerror("Error", "No se pudo actualizar el proyecto.")
            else:
                messagebox.showerror("Error", "Por favor completa todos los campos.")
        except ValueError:
            messagebox.showerror("Error", "Formato de fecha incorrecto (debe ser YYYY-MM-DD).")
        except Fault as e:
            messagebox.showerror("Error", f"Error al actualizar proyecto:\n{e}")

    def eliminar_proyecto_confirmacion(self):
        id_proy = self.obtener_id_seleccionado()

        try:
            if id_proy:
                if messagebox.askyesno("Confirmación", "¿Estás seguro que deseas eliminar este proyecto?"):
                    if client.service.proyectoDelete(int(id_proy)):
                        messagebox.showinfo("Éxito", "Proyecto eliminado correctamente.")
                        self.mostrar_proyectos()
                        self.cancelar_formulario()
                    else:
                        messagebox.showerror("Error", "No se pudo eliminar el proyecto.")
            else:
                messagebox.showerror("Error", "Por favor ingresa el ID del proyecto a eliminar.")
        except ValueError:
            messagebox.showerror("Error", "El ID del proyecto debe ser un número.")
        except Fault as e:
            messagebox.showerror("Error", f"Error al eliminar proyecto:\n{e}")

    def cargar_datos_seleccionados(self, event):
        index = self.proyectos_listbox.curselection()
        if index:
            proyecto_seleccionado = self.proyectos_listbox.get(index)
            datos = proyecto_seleccionado.split('|')
            id_proyecto = datos[0].split(':')[1].strip()
            self.id_proyecto.delete(0, tk.END)
            self.id_proyecto.insert(tk.END, id_proyecto)
            nombre = datos[1].split(':')[1].strip()
            fec_inicio = datos[2].split(':')[1].strip()
            fec_termino = datos[3].split(':')[1].strip()
            nombre_dpto = datos[4].split(':')[1].strip()

            self.nombre_proyecto.delete(0, tk.END)
            self.nombre_proyecto.insert(tk.END, nombre)

            self.fec_inicio_proyecto.delete(0, tk.END)
            self.fec_inicio_proyecto.insert(tk.END, fec_inicio)

            self.fec_termino_proyecto.delete(0, tk.END)
            self.fec_termino_proyecto.insert(tk.END, fec_termino)

            self.nombre_dpto_proyecto.set(nombre_dpto)

            # Habilitar el botón de actualizar y eliminar
            self.btn_actualizar.config(state=tk.NORMAL)
            self.btn_eliminar.config(state=tk.NORMAL)

    def obtener_id_seleccionado(self):
        try:
            index = self.proyectos_listbox.curselection()
            if index:
                proyecto_seleccionado = self.proyectos_listbox.get(index)
                id_proyecto = proyecto_seleccionado.split()[1]  # Obtener el ID del proyecto seleccionado
                return id_proyecto
            else:
                return None
        except IndexError:
            return None

    def obtener_departamentos(self):
        try:
            departamentos = client.service.departamentoAll()
            nombres_departamentos = [departamento['nombre'] for departamento in departamentos]
            return nombres_departamentos
        except Fault as e:
            messagebox.showerror("Error", f"Error al obtener departamentos:\n{e}")
            return []

    def obtener_id_departamento(self, nombre_departamento):
        try:
            departamentos = client.service.departamentoAll()
            for departamento in departamentos:
                if departamento['nombre'] == nombre_departamento:
                    return departamento['idDpto']
            return None
        except Fault as e:
            messagebox.showerror("Error", f"Error al obtener ID del departamento:\n{e}")
            return None

    def obtener_nombre_departamento(self, id_departamento):
        try:
            departamentos = client.service.departamentoAll()
            for departamento in departamentos:
                if departamento['idDpto'] == id_departamento:
                    return departamento['nombre']
            return None
        except Fault as e:
            messagebox.showerror("Error", f"Error al obtener nombre del departamento:\n{e}")
            return None

    def actualizar_id_departamento(self, nombre_departamento):
        id_departamento = self.obtener_id_departamento(nombre_departamento)
        if id_departamento is not None:
            self.id_dpto_proyecto.config(state=tk.NORMAL)
            self.id_dpto_proyecto.delete(0, tk.END)
            self.id_dpto_proyecto.insert(tk.END, id_departamento)
            self.id_dpto_proyecto.config(state=tk.DISABLED)
        else:
            messagebox.showerror("Error", f"No se encontró el ID del departamento '{nombre_departamento}'.")

    def validar_campos(self):
        nombre = self.nombre_proyecto.get()
        fec_inicio = self.fec_inicio_proyecto.get()
        fec_termino = self.fec_termino_proyecto.get()
        id_dpto = self.obtener_id_departamento(self.nombre_dpto_proyecto.get())

        if nombre and fec_inicio and fec_termino and id_dpto:
            return True
        else:
            messagebox.showerror("Error", "Por favor completa todos los campos.")
            return False

    def limpiar_campos(self):
        self.id_proyecto.delete(0, tk.END)
        self.nombre_proyecto.delete(0, tk.END)
        self.fec_inicio_proyecto.delete(0, tk.END)
        self.fec_termino_proyecto.delete(0, tk.END)
        self.nombre_dpto_proyecto.set("")  # Limpiar la selección de departamento
        self.btn_actualizar.config(state=tk.DISABLED)
        self.btn_eliminar.config(state=tk.DISABLED)

    def run(self):
        self.root.mainloop()

if __name__ == "__main__":
    root = tk.Tk()
    gui = ProyectoGUI(root)
    gui.run()
