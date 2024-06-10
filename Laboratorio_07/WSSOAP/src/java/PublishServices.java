/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author play_
 */

import javax.xml.ws.Endpoint;
 
import es.rosamarfil.soap.SOAPImpl;
 
public class PublishServices {
 
    public static void main(String[] args) {
             
        /*
         *  Se publican los servicios a través de un servidor virtual. 
            El puerto puede ser cualquiera.
            Un vez ejecutada la aplicación, se publica el contrato WSDL
          */
          
        Endpoint.publish("http://localhost:1516/WS/Users", new SOAPImpl());
 
    }
}
