/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servise_web_jdbc;

/**
 *
 * @author ROBERT
 */
public class Departamento {

    private int idDpto;

    private String nombre;

    private String telefono;
    
    private String fax;

    // Constructor vacío (necesario para JPA)
    public Departamento() {
    }

    // Getters y setters

    public int getIdDpto() {
        return idDpto;
    }

    public void setIdDpto(int idDpto) {
        this.idDpto = idDpto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    // Método toString para propósitos de depuración
    @Override
    public String toString() {
        return "Departamento{" +
                "idDpto=" + idDpto +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fax='" + fax + '\'' +
                '}';
    }
}
