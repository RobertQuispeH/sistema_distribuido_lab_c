/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servise_web_jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ROBERT
 */
public class ConecctionDB {
    String db="propuesto_1";
    String url = "jdbc:mysql://localhost:3306/";
    String user = "root";
    String password = "admin";
    String driver = "com.mysql.cj.jdbc.Driver";
    Connection cx;

    public ConecctionDB() {}
    public Connection conectar(){
        try {
            Class.forName(driver);
            cx = DriverManager.getConnection(url+db+"?useTimeZone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false",user,password);
            System.out.println("se conecto");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("no se conecto");
            Logger.getLogger(ConecctionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cx;
    }
    
    public void desconectar(){
        try {
            cx.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConecctionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}
