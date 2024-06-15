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
    
    @WebMethod(operationName = "addDepartamento")
    public boolean addDepartamento(Departamento departamento) {
        return q.departamentoAdd(departamento);
    }

    @WebMethod(operationName = "updateDepartamento")
    public boolean updateDepartamento(Departamento departamento) {
        return q.departamentoUpdate(departamento);
    }

    @WebMethod(operationName = "deleteDepartamento")
    public boolean deleteDepartamento(int idDpto) {
        return q.departamentoDelete(idDpto);
    }

    @WebMethod(operationName = "departamentoSearchByName")
    public int departamentoSearchByName(String name) {
        return q.departamentoSearchByName(name);
    }
}
