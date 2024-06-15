/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servise_web_jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    //Proyecto
    public ArrayList<Proyecto> proyectoAll() {
        ArrayList<Proyecto> all = new ArrayList<>();
        Connection accesDB = conecctionDB.conectar();
        Proyecto proyecto = null;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            PreparedStatement ps = accesDB.prepareStatement("SELECT * FROM proyecto");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                proyecto = new Proyecto();
                proyecto.setIdProy(rs.getInt("IDProy"));
                proyecto.setNombre(rs.getString("Nombre"));
                proyecto.setFecInicio(rs.getDate("Fec_Inicio"));
                proyecto.setFecTermino(rs.getDate("Fec_Termino"));
                proyecto.setIdDpto(rs.getInt("IDDpto"));
                all.add(proyecto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return all;
    }

    public boolean proyectoAdd(Proyecto proyecto) {
        boolean added = false;
        Connection accesDB = conecctionDB.conectar();
        try {
            PreparedStatement ps = accesDB.prepareStatement("INSERT INTO proyecto (IDProy, Nombre, Fec_Inicio, Fec_Termino, IDDpto) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, proyecto.getIdProy());
            ps.setString(2, proyecto.getNombre());
            ps.setDate(3, new java.sql.Date(proyecto.getFecInicio().getTime()));
            ps.setDate(4, new java.sql.Date(proyecto.getFecTermino().getTime()));
            ps.setInt(5, proyecto.getIdDpto());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                added = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return added;
    }

    public boolean proyectoUpdate(Proyecto proyecto) {
        boolean updated = false;
        Connection accesDB = conecctionDB.conectar();
        try {
            PreparedStatement ps = accesDB.prepareStatement("UPDATE proyecto SET Nombre = ?, Fec_Inicio = ?, Fec_Termino = ?, IDDpto = ? WHERE IDProy = ?");
            ps.setString(1, proyecto.getNombre());
            ps.setDate(2, new java.sql.Date(proyecto.getFecInicio().getTime()));
            ps.setDate(3, new java.sql.Date(proyecto.getFecTermino().getTime()));
            ps.setInt(4, proyecto.getIdDpto());
            ps.setInt(5, proyecto.getIdProy());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                updated = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updated;
    }

    public boolean proyectoDelete(int idProy) {
        boolean deleted = false;
        Connection accesDB = conecctionDB.conectar();
        try {
            PreparedStatement ps = accesDB.prepareStatement("DELETE FROM proyecto WHERE IDProy = ?");
            ps.setInt(1, idProy);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                deleted = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deleted;
    }
    //ingeniero
    public boolean ingenieroAdd(Ingeniero ingeniero) {
        boolean added = false;
        Connection accesDB = conecctionDB.conectar();
        try {
            PreparedStatement ps = accesDB.prepareStatement("INSERT INTO ingeniero (IDIng, Nombre, Especialidad, Cargo, IDProy) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, ingeniero.getIdIng());
            ps.setString(2, ingeniero.getNombre());
            ps.setString(3, ingeniero.getEspecialidad());
            ps.setString(4, ingeniero.getCargo());
            ps.setInt(5, ingeniero.getIdProy());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                added = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return added;
    }

    public boolean ingenieroUpdate(Ingeniero ingeniero) {
        boolean updated = false;
        Connection accesDB = conecctionDB.conectar();
        try {
            PreparedStatement ps = accesDB.prepareStatement("UPDATE ingeniero SET Nombre = ?, Especialidad = ?, Cargo = ?, IDProy = ? WHERE IDIng = ?");
            ps.setString(1, ingeniero.getNombre());
            ps.setString(2, ingeniero.getEspecialidad());
            ps.setString(3, ingeniero.getCargo());
            ps.setInt(4, ingeniero.getIdProy());
            ps.setInt(5, ingeniero.getIdIng());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                updated = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updated;
    }

    public boolean ingenieroDelete(int IDIng) {
        boolean deleted = false;
        Connection accesDB = conecctionDB.conectar();
        try {
            PreparedStatement ps = accesDB.prepareStatement("DELETE FROM ingeniero WHERE IDIng = ?");
            ps.setInt(1, IDIng);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                deleted = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deleted;
    }

    public ArrayList<Ingeniero> ingenieroAll() {
        ArrayList<Ingeniero> all = new ArrayList<>();
        Connection accesDB = conecctionDB.conectar();
        Ingeniero ingeniero = null;
        try {
            PreparedStatement ps = accesDB.prepareStatement("SELECT * FROM ingeniero");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ingeniero = new Ingeniero();
                ingeniero.setIdIng(rs.getInt("IDIng"));
                ingeniero.setNombre(rs.getString("Nombre"));
                ingeniero.setEspecialidad(rs.getString("Especialidad"));
                ingeniero.setCargo(rs.getString("Cargo"));
                ingeniero.setIdProy(rs.getInt("IDProy"));
                all.add(ingeniero);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return all;
    }
}
