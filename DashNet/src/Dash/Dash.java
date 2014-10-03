package Dash;

import SnmpManager.Manager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.snmp4j.PDU;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;


public class Dash{

    public static void main(String[] args){
        
        OID[] oids = new OID[4];
        oids[0] = new OID(".1.3.6.1.2.1.1.5.0"); // System Name
        oids[1] = new OID(".1.3.6.1.2.1.1.1.0"); // System Description
        oids[2] = new OID(".1.3.6.1.2.1.1.3.0"); // System up-time
        //oids[] = new OID(".1.3.6.1.2.1.1.1.0"); // System Description
        //oids[] = new OID(".1.3.6.1.2.1.1.1.0"); // System Description
        oids[3] = new OID(".1.3.6.1.4.1.674.10890.1.1.2.1.5"); // System Temperature
        
        try {
            int i = 0;
            Manager manager = new Manager("udp:147.26.195.92/161"); //127.0.0.1/161");
            manager.start();
            ResponseEvent event = manager.get(oids);
            PDU pdu = event.getResponse();
            VariableBinding[] vbArr = pdu.toArray();
            for(VariableBinding vb : vbArr){
                System.out.println("Response" + i++ + ": " + vb.toString());
            }
           
            /*
            for(OID oid : oids){
                
            }
                    
            System.out.println("System Name: " + manager.getAsString( ));
            System.out.println("System Description: " + manager.getAsString( ));
            System.out.println("Temperature: " + manager.getAsString( ));
                    */
            
        }catch (IOException ex){
            Logger.getLogger(Dash.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}