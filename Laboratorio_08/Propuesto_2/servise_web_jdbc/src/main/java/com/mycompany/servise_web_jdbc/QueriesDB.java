/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servise_web_jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author ROBERT
 */
public class QueriesDB {
    ConecctionDB conecctionDB;

    public QueriesDB() {
        conecctionDB = new ConecctionDB();
    }
    public int departamentoSearchByName(String name){
        int idDep = 0;
        Connection accesDB = conecctionDB.conectar();
        try {
            PreparedStatement ps = accesDB.prepareStatement("SELECT IDDpto FROM departamento WHERE Nombre = ?");
            ps.setString(1, name);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                idDep = rs.getInt(1);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return idDep;
    }
    
    public ArrayList<Departamento> departamentoAll(){
        ArrayList<Departamento> all = new ArrayList<>();
        Connection accesDB = conecctionDB.conectar();
        Departamento departamento = null;
        try {
            PreparedStatement ps = accesDB.prepareStatement("SELECT * FROM departamento");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                departamento = new Departamento();
                departamento.setIdDpto(rs.getInt("IDDpto"));
                departamento.setNombre(rs.getString("Nombre"));
                departamento.setFax(rs.getString("Fax"));
                departamento.setTelefono(rs.getString("Telefono"));
                all.add(departamento);
            }
        } catch (Exception e) {
        }
        return  all;
    }
    public boolean departamentoAdd(Departamento departamento) {
        boolean added = false;
        Connection accesDB = conecctionDB.conectar();
        try {
            PreparedStatement ps = accesDB.prepareStatement("INSERT INTO departamento (IDDpto,Nombre, Telefono, Fax) VALUES (?,?, ?,?)");
            ps.setInt(1, departamento.getIdDpto());
            ps.setString(2, departamento.getNombre());
            ps.setString(3, departamento.getTelefono());
            ps.setString(4, departamento.getFax());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                added = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return added;
    }
    public boolean departamentoUpdate(Departamento departamento) {
        boolean updated = false;
        Connection accesDB = conecctionDB.conectar();
        try {
            PreparedStatement ps = accesDB.prepareStatement("UPDATE departamento SET Nombre = ?, Telefono = ?, Fax = ? WHERE IDDpto = ?");
            ps.setString(1, departamento.getNombre());
            ps.setString(2, departamento.getTelefono());
            ps.setString(3, departamento.getFax());
            ps.setInt(4, departamento.getIdDpto());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                updated = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updated;
    }
    public boolean departamentoDelete(int idDpto) {
        boolean deleted = false;
        Connection accesDB = conecctionDB.conectar();
        try {
            PreparedStatement ps = accesDB.prepareStatement("DELETE FROM departamento WHERE IDDpto = ?");
            ps.setInt(1, idDpto);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                deleted = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deleted;
    }
    
}
