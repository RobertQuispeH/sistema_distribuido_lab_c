/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servise_web_jdbc;

/**
 *
 * @author ROBERT
 */
import java.util.Date;

public class Proyecto {
    private int idProy;
    private String nombre;
    private Date fecInicio;
    private Date fecTermino;
    private int idDpto;
    
    public Proyecto() {
    }

    public int getIdProy() {
        return idProy;
    }

    public void setIdProy(int idProy) {
        this.idProy = idProy;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecInicio() {
        return fecInicio;
    }

    public void setFecInicio(Date fecInicio) {
        this.fecInicio = fecInicio;
    }

    public Date getFecTermino() {
        return fecTermino;
    }

    public void setFecTermino(Date fecTermino) {
        this.fecTermino = fecTermino;
    }

    public int getIdDpto() {
        return idDpto;
    }

    public void setIdDpto(int idDpto) {
        this.idDpto = idDpto;
    }

    @Override
    public String toString() {
        return "Proyecto{" +
                "idProy=" + idProy +
                ", nombre='" + nombre + '\'' +
                ", fecInicio=" + fecInicio +
                ", fecTermino=" + fecTermino +
                ", idDpto=" + idDpto +
                '}';
    }
}

