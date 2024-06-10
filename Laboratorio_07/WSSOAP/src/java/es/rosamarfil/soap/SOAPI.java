/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.rosamarfil.soap;
 
import java.util.List;
 
import javax.jws.WebMethod;
import javax.jws.WebService;
 
import es.rosamarfil.model.User;
 
@WebService
public interface SOAPI {
     
    @WebMethod
    public List<User> getUsers();
     
    @WebMethod
    public void addUser(User user);
 
}
