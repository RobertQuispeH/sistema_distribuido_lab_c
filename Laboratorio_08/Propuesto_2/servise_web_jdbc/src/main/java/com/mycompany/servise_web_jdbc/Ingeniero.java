/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servise_web_jdbc;

/**
 *
 * @author ROBERT
 */
public class Ingeniero {
    private int idIng;
    private String nombre;
    private String especialidad;
    private String cargo;
    private int idProy;

    public Ingeniero() {
    }

    public Ingeniero(int idIng, String nombre, String especialidad, String cargo, int idProy) {
        this.idIng = idIng;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.cargo = cargo;
        this.idProy = idProy;
    }

    public int getIdIng() {
        return idIng;
    }

    public void setIdIng(int idIng) {
        this.idIng = idIng;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public int getIdProy() {
        return idProy;
    }

    public void setIdProy(int idProy) {
        this.idProy = idProy;
    }

    @Override
    public String toString() {
        return "Ingeniero{" +
                "idIng=" + idIng +
                ", nombre='" + nombre + '\'' +
                ", especialidad='" + especialidad + '\'' +
                ", cargo='" + cargo + '\'' +
                ", idProy=" + idProy +
                '}';
    }
}