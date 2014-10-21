package SnmpAgent;

import java.util.logging.Level;
import java.util.logging.Logger;




public class SnmpAgent {


    public static void main(String[] args) {
    
        try{  
            String targetIp = "127.0.0.1";
            String param = "localhost";
          
            System.out.println("GET_REQUEST: " + udpc.getRequest(param, targetIp));
        } catch (Exception ex) {
            Logger.getLogger(SnmpAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}