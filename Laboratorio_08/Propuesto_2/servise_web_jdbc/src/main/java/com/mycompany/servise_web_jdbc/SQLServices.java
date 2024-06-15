/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package com.mycompany.servise_web_jdbc;

import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author ROBERT
 */
@WebService(serviceName = "SQLServices")
public class SQLServices {
    QueriesDB q = new QueriesDB();
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "departamentoAll")
    public List<Departamento> departamentoAll() {
        return q.departamentoAll();
    }
    
    @WebMethod(operationName = "departamentoAdd")
    public boolean departamentoAdd(Departamento departamento) {
        return q.departamentoAdd(departamento);
    }

    @WebMethod(operationName = "departamentoUpdate")
    public boolean departamentoUpdate(Departamento departamento) {
        return q.departamentoUpdate(departamento);
    }

    @WebMethod(operationName = "departamentoDelete")
    public boolean departamentoDelete(int idDpto) {
        return q.departamentoDelete(idDpto);
    }
    //proyecto
    @WebMethod(operationName = "proyectoAll")
    public List<Proyecto> proyectoAll() {
        return q.proyectoAll();
    }
    
    @WebMethod(operationName = "proyectoAdd")
    public boolean proyectoAdd(Proyecto proyecto) {
        return q.proyectoAdd(proyecto);
    }

    @WebMethod(operationName = "proyectoUpdate")
    public boolean proyectoUpdate(Proyecto proyecto) {
        return q.proyectoUpdate(proyecto);
    }

    @WebMethod(operationName = "proyectoDelete")
    public boolean proyectoDelete(int idProy) {
        return q.proyectoDelete(idProy);
    }
  //Ingeniero
   @WebMethod(operationName = "ingenieroAll")
    public List<Ingeniero> ingenieroAll() {
        return q.ingenieroAll();
    }

    @WebMethod(operationName = "ingenieroAdd")
    public boolean ingenieroAdd(Ingeniero ingeniero) {
        return q.ingenieroAdd(ingeniero);
    }

    @WebMethod(operationName = "ingenieroUpdate")
    public boolean ingenieroUpdate(Ingeniero ingeniero) {
        return q.ingenieroUpdate(ingeniero);
    }

    @WebMethod(operationName = "ingenieroDelete")
    public boolean ingenieroDelete(int IDIng) {
        return q.ingenieroDelete(IDIng);
    }
}
