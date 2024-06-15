import tkinter as tk
from tkinter import messagebox
from zeep import Client
from zeep.exceptions import Fault
from zeep import xsd

# URL del servicio SOAP para departamentos
url_servicio = 'http://localhost:8080/servise_web_jdbc/SQLServices?WSDL'

# Crear un cliente para el servicio SOAP
client = Client(url_servicio)

class DepartamentoGUI:
    def __init__(self, root):
        self.root = root
        self.root.title("Gestión de Departamentos")

        # Etiqueta y lista de departamentos
        self.label_departamentos = tk.Label(root, text="Departamentos", font=("Arial", 16, "bold"))
        self.label_departamentos.grid(row=0, column=0, padx=10, pady=10, columnspan=2)

        self.departamentos_listbox = tk.Listbox(root, width=120, height=10)
        self.departamentos_listbox.grid(row=1, column=0, padx=10, pady=10, rowspan=6, columnspan=3)
        self.departamentos_listbox.bind('<<ListboxSelect>>', self.cargar_datos_seleccionados)

        # Botones para operaciones CRUD
        self.btn_mostrar = tk.Button(root, text="Mostrar Departamentos", command=self.mostrar_departamentos, width=20)
        self.btn_mostrar.grid(row=1, column=3, padx=10, pady=10)

        self.btn_agregar = tk.Button(root, text="Agregar Departamento", command=self.mostrar_formulario_agregar, width=20)
        self.btn_agregar.grid(row=2, column=3, padx=10, pady=10)

        self.btn_actualizar = tk.Button(root, text="Actualizar Departamento", command=self.mostrar_formulario_actualizar, width=20, state=tk.DISABLED)
        self.btn_actualizar.grid(row=3, column=3, padx=10, pady=10)

        self.btn_eliminar = tk.Button(root, text="Eliminar Departamento", command=self.eliminar_departamento_confirmacion, width=20)
        self.btn_eliminar.grid(row=4, column=3, padx=10, pady=10)

        # Frame para el formulario
        self.frame_formulario = tk.Frame(root, padx=20, pady=10)

        self.label_accion = tk.Label(self.frame_formulario, text="", font=("Arial", 12, "bold"))
        self.label_accion.grid(row=0, column=0, columnspan=2, pady=10)

        self.id_departamento_label = tk.Label(self.frame_formulario, text="ID Departamento", font=("Arial", 12))
        self.id_departamento_label.grid(row=1, column=0, sticky="e", padx=10, pady=5)

        self.id_departamento = tk.Entry(self.frame_formulario, width=40, font=("Arial", 12))
        self.id_departamento.grid(row=1, column=1, padx=10, pady=5)

        self.nombre_departamento_label = tk.Label(self.frame_formulario, text="Nombre", font=("Arial", 12))
        self.nombre_departamento_label.grid(row=2, column=0, sticky="e", padx=10, pady=5)

        self.nombre_departamento = tk.Entry(self.frame_formulario, width=40, font=("Arial", 12))
        self.nombre_departamento.grid(row=2, column=1, padx=10, pady=5)

        self.telefono_departamento_label = tk.Label(self.frame_formulario, text="Teléfono", font=("Arial", 12))
        self.telefono_departamento_label.grid(row=3, column=0, sticky="e", padx=10, pady=5)

        self.telefono_departamento = tk.Entry(self.frame_formulario, width=40, font=("Arial", 12))
        self.telefono_departamento.grid(row=3, column=1, padx=10, pady=5)

        self.fax_departamento_label = tk.Label(self.frame_formulario, text="Fax", font=("Arial", 12))
        self.fax_departamento_label.grid(row=4, column=0, sticky="e", padx=10, pady=5)

        self.fax_departamento = tk.Entry(self.frame_formulario, width=40, font=("Arial", 12))
        self.fax_departamento.grid(row=4, column=1, padx=10, pady=5)

        self.btn_aceptar = tk.Button(self.frame_formulario, text="Aceptar", command=self.aceptar_formulario)
        self.btn_aceptar.grid(row=5, column=0, padx=5, pady=10)

        self.btn_cancelar = tk.Button(self.frame_formulario, text="Cancelar", command=self.cancelar_formulario)
        self.btn_cancelar.grid(row=5, column=1, padx=5, pady=10)

        self.frame_formulario.grid_forget()  # Ocultar el formulario al inicio

        self.mostrar_departamentos()

    def mostrar_formulario_agregar(self):
        self.limpiar_campos()
        self.departamentos_listbox.selection_clear(0, tk.END)  # Desactivar la selección en la lista
        self.label_accion.config(text="Agregar Departamento", fg="blue")
        self.frame_formulario.grid(row=10, column=0, columnspan=3, padx=10, pady=10)
        self.btn_aceptar.config(command=self.agregar_departamento)
        self.btn_actualizar.config(state=tk.DISABLED)
        self.btn_eliminar.config(state=tk.DISABLED)

    def mostrar_formulario_actualizar(self):
        index = self.departamentos_listbox.curselection()
        if index:
            departamento_seleccionado = self.departamentos_listbox.get(index)
            datos = departamento_seleccionado.split('|')
            id_departamento = datos[0].split(':')[1].strip()
            nombre = datos[1].split(':')[1].strip()
            telefono = datos[2].split(':')[1].strip()
            fax = datos[3].split(':')[1].strip()

            self.id_departamento.delete(0, tk.END)
            self.id_departamento.insert(tk.END, id_departamento)
            self.nombre_departamento.delete(0, tk.END)
            self.nombre_departamento.insert(tk.END, nombre)
            self.telefono_departamento.delete(0, tk.END)
            self.telefono_departamento.insert(tk.END, telefono)
            self.fax_departamento.delete(0, tk.END)
            self.fax_departamento.insert(tk.END, fax)

            self.label_accion.config(text="Actualizar Departamento", fg="green")
            self.frame_formulario.grid(row=10, column=0, columnspan=3, padx=10, pady=10)
            self.btn_aceptar.config(command=self.actualizar_departamento)
            self.btn_actualizar.config(state=tk.DISABLED)
            self.btn_eliminar.config(state=tk.DISABLED)

    def aceptar_formulario(self):
        if self.validar_campos():
            self.agregar_departamento()  # Llamar a la función para agregar o actualizar según el contexto
            self.cancelar_formulario()

    def cancelar_formulario(self):
        self.frame_formulario.grid_forget()  # Ocultar el formulario
        self.limpiar_campos()

    def eliminar_departamento_confirmacion(self):
        id_seleccionado = self.obtener_id_seleccionado()
        if id_seleccionado:
            respuesta = messagebox.askyesno("Confirmar", "¿Estás seguro que quieres eliminar este departamento?")
            if respuesta:
                self.eliminar_departamento()

    def mostrar_departamentos(self):
        try:
            departamentos = client.service.departamentoAll()
            self.departamentos_listbox.delete(0, tk.END)  # Limpiar la lista actual
            for departamento in departamentos:
                id_departamento = departamento.idDpto
                nombre = departamento.nombre
                telefono = departamento.telefono
                fax = departamento.fax
                self.departamentos_listbox.insert(tk.END, f"ID: {id_departamento} | Nombre: {nombre} | Teléfono: {telefono} | Fax: {fax}")
        except Fault as e:
            messagebox.showerror("Error", f"Error al obtener departamentos:\n{e}")

    def agregar_departamento(self):
        id_departamento = self.id_departamento.get()
        nombre = self.nombre_departamento.get()
        telefono = self.telefono_departamento.get()
        fax = self.fax_departamento.get()

        try:
            if nombre and telefono and fax:
                departamento = {
                    "idDpto": int(id_departamento),
                    "nombre": nombre,
                    "telefono": telefono,
                    "fax": fax
                }

                if client.service.departamentoAdd(departamento):
                    messagebox.showinfo("Éxito", "Departamento agregado correctamente.")
                    self.mostrar_departamentos()
                    self.cancelar_formulario()
                else:
                    messagebox.showerror("Error", "No se pudo agregar el departamento.")
            else:
                messagebox.showerror("Error", "Por favor completa todos los campos.")
        except Fault as e:
            messagebox.showerror("Error", f"Error al agregar departamento:\n{e}")

    def actualizar_departamento(self):
        id_departamento = self.id_departamento.get()
        nombre = self.nombre_departamento.get()
        telefono = self.telefono_departamento.get()
        fax = self.fax_departamento.get()

        try:
            if id_departamento and nombre and telefono and fax:
                departamento = {
                    "idDpto": int(id_departamento),
                    "nombre": nombre,
                    "telefono": telefono,
                    "fax": fax
                }

                if client.service.departamentoUpdate(departamento):
                    messagebox.showinfo("Éxito", "Departamento actualizado correctamente.")
                    self.mostrar_departamentos()
                    self.cancelar_formulario()
                else:
                    messagebox.showerror("Error", "No se pudo actualizar el departamento.")
            else:
                messagebox.showerror("Error", "Por favor completa todos los campos.")
        except Fault as e:
            messagebox.showerror("Error", f"Error al actualizar departamento:\n{e}")

    def eliminar_departamento(self):
        id_departamento = self.obtener_id_seleccionado()
        if id_departamento:
            try:
                if client.service.departamentoDelete(int(id_departamento)):
                    messagebox.showinfo("Éxito", "Departamento eliminado correctamente.")
                    self.mostrar_departamentos()
                else:
                    messagebox.showerror("Error", "No se pudo eliminar el departamento.")
            except Fault as e:
                messagebox.showerror("Error", f"Error al eliminar departamento:\n{e}")

    def cargar_datos_seleccionados(self, event):
        #self.limpiar_campos()
        index = self.departamentos_listbox.curselection()
        if index:
            departamento_seleccionado = self.departamentos_listbox.get(index)
            datos = departamento_seleccionado.split('|')
            id_departamento = datos[0].split(':')[1].strip()
            nombre = datos[1].split(':')[1].strip()
            telefono = datos[2].split(':')[1].strip()
            fax = datos[3].split(':')[1].strip()

            self.id_departamento.insert(tk.END, id_departamento)
            self.nombre_departamento.insert(tk.END, nombre)
            self.telefono_departamento.insert(tk.END, telefono)
            self.fax_departamento.insert(tk.END, fax)

            # Habilitar el botón de actualizar y eliminar
            self.btn_actualizar.config(state=tk.NORMAL)
            self.btn_eliminar.config(state=tk.NORMAL)

    def obtener_id_seleccionado(self):
        try:
            index = self.departamentos_listbox.curselection()
            if index:
                departamento_seleccionado = self.departamentos_listbox.get(index)
                id_departamento = departamento_seleccionado.split()[1]  # Obtener el ID del departamento seleccionado
                return id_departamento
            else:
                return None
        except IndexError:
            return None

    def validar_campos(self):
        nombre = self.nombre_departamento.get()
        telefono = self.telefono_departamento.get()
        fax = self.fax_departamento.get()

        if nombre and telefono and fax:
            return True
        else:
            messagebox.showerror("Error", "Por favor completa todos los campos.")
            return False

    def limpiar_campos(self):
        self.id_departamento.delete(0, tk.END)
        self.nombre_departamento.delete(0, tk.END)
        self.telefono_departamento.delete(0, tk.END)
        self.fax_departamento.delete(0, tk.END)
        self.btn_actualizar.config(state=tk.DISABLED)
        self.btn_eliminar.config(state=tk.DISABLED)

    def run(self):
        self.root.mainloop()

if __name__ == "__main__":
    root = tk.Tk()
    gui = DepartamentoGUI(root)
    gui.run()
