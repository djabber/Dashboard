package Dash;

import SnmpManager.Manager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.snmp4j.smi.OID;


public class Dash{

    public static void main(String[] args){
        
        try {
            Manager manager = new Manager("udp:147.26.195.92/161"); //127.0.0.1/161");
            manager.start();
       
            System.out.println("System Description: " + manager.getAsString(new OID(".1.3.6.1.2.1.1.1.0")));
            System.out.println("System Name: " + manager.getAsString(new OID(".1.3.6.1.2.1.1.5.0")));
            System.out.println("Temperature: " + manager.getAsString(new OID(".1.3.6.1.4.1.674.10890.1.1.2.1.5")));
            
        }catch (IOException ex){
            Logger.getLogger(Dash.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}