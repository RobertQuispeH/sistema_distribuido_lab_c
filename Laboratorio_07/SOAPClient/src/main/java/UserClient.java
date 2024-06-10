/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author play_
 */

import java.util.Arrays;
import es.rosamarfil.soap.SOAPI;
import es.rosamarfil.soap.SOAPImplService;
import es.rosamarfil.soap.User;

public class UserClient {
    public static void main(String[] args) {
        SOAPImplService service = new SOAPImplService();
        SOAPI userService = service.getSOAPImplPort();

        // Mostrar lista de usuarios
        System.out.println("Lista de usuarios: \n" + Arrays.toString(userService.getUsers().toArray()));

        // Añadir nuevo usuario
        User newUser = new User();
        newUser.setName("Pablo");
        newUser.setUsername("Ruiz");
        userService.addUser(newUser);
        
        // Mostrar lista de usuarios
        System.out.println("Lista de usuarios: \n" + Arrays.toString(userService.getUsers().toArray()));
    }
}
