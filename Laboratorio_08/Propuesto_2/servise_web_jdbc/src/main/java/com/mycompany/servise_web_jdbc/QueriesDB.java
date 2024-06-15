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
    
}
