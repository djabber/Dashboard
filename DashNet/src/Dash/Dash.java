package Dash;

import SnmpManager.SnmpGetBulk;
import SnmpManager.SnmpWalker;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dash {

    public static void main(String[] args) {
    
        try {
            long start = System.currentTimeMillis();
            int sleepCnt = 0;
            
            
            
            SnmpWalker walk = new SnmpWalker(".1.3.6.1.2.1.1.1.0");
            walk.snmpWalk(".1.3.6.1.2.1.1.1.0");
            walk.printList();
            System.out.println(" ****** End of walk list ****** \n\n");
            
            SnmpGetBulk sgb = new SnmpGetBulk();
            
            sgb.getBulk2(".1.3.6.1.2.1.1.1.0");
            sgb.printList();
            
            long end = System.currentTimeMillis();
            long runtime = end - start;
            System.out.println("\nRuntime:\n\t" + runtime + " milli-seconds\n\t" + (runtime / 10) + " centi-seconds\n\t" + (runtime / 100) + " deci-seconds\n\t" + (runtime / 1000) + " seconds\n\t");
                    
                    
                    
                    /*long start = System.currentTimeMillis();
                    int sleepCnt = 0;
                    
                    try {
                    
                    //ExecutorService execSvc = Executors.newFixedThreadPool(8);
                    SnmpGetBulk sgb = new SnmpGetBulk();
                    
                    
                    //for(int i = 0; i < 100; i++){ execSvc.execute(sgb); }
                    
                    //execSvc.shutdown();
                    //while(!execSvc.isTerminated()){ Thread.sleep(1000); }
                    
                    sgb.getBulk2(".1.3.6.1.2.1.1.1.0");
                    //sgb.getBulk(".1.3.6.1.2.1.1.1.0");
                    sgb.sortMap();
                    //sgb.printList();
                    
                    //sgb.getBulk(".1.3.6.1.2.1.1.1.0");
                    //sgb.printList();
                    
                    long end = System.currentTimeMillis();
                    long runtime = end - start;
                    System.out.println("\nRuntime:\n\t" + runtime + " milli-seconds\n\t" + (runtime / 10) + " centi-seconds\n\t" + (runtime / 100) + " deci-seconds\n\t" + (runtime / 1000) + " seconds\n\t");
                    
                    } catch (IOException ex) {
                    Logger.getLogger(Dash.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    */
                    /*
                    OID[] oids = new OID[999999];
                    *
                    oids[0] = new OID(".1.3.6.1.2.1.1.5.0"); // System Name
                    oids[1] = new OID(".1.3.6.1.2.1.1.1.0"); // System Description
                    oids[2] = new OID(".1.3.6.1.2.1.1.3.0"); // System up-time
                    oids[3] = new OID(".1.3.6.1.4.1.674.10890.1.1.2.1.5"); // System Temperature
                    *
                    SnmpRequest client = new SnmpRequest("udp:127.0.0.1/161");
                    List<OID> list = client.buildOidList(".1.3.6.1.2.1.1.5.0", 2);
                    client.walk(list);
                    client.getBulk();
                    *
                    System.out.println("SYSTEM_NAME: " + client.getAsString(name));
                    System.out.println("SYSTEM_DESCRIPTION: " + client.getAsString(desc));
                    System.out.println("UP_TIME: " + client.getAsString(up));
                    System.out.println("TEMP: " + client.getAsString(temp));
                    */
                    
                    /*
                    OID[] oids = new OID[999999];
                    *
                    oids[0] = new OID(".1.3.6.1.2.1.1.5.0"); // System Name
                    oids[1] = new OID(".1.3.6.1.2.1.1.1.0"); // System Description
                    oids[2] = new OID(".1.3.6.1.2.1.1.3.0"); // System up-time
                    oids[3] = new OID(".1.3.6.1.4.1.674.10890.1.1.2.1.5"); // System Temperature
                    *
                    
                    SnmpRequest client = new SnmpRequest("udp:127.0.0.1/161");
                    List<OID> list = client.buildOidList(".1.3.6.1.2.1.1.5.0", 2);
                    
                    client.walk(list);
                    
                    client.getBulk();
                    *
                    System.out.println("SYSTEM_NAME: " + client.getAsString(name));
                    System.out.println("SYSTEM_DESCRIPTION: " + client.getAsString(desc));
                    System.out.println("UP_TIME: " + client.getAsString(up));
                    System.out.println("TEMP: " + client.getAsString(temp));
                    */
        } catch (IOException ex) {
            Logger.getLogger(Dash.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /*
     public static void main(String[] args) {

     try {
            
     OID[] oids = new OID[4];
     oids[0] = new OID(".1.3.6.1.2.1.1.5.0"); // System Name
     oids[1] = new OID(".1.3.6.1.2.1.1.1.0"); // System Description
     oids[2] = new OID(".1.3.6.1.2.1.1.3.0"); // System up-time
     oids[3] = new OID(".1.3.6.1.4.1.674.10890.1.1.2.1.5"); // System Temperature
            
     Manager manager = new Manager("udp:127.0.0.1/161"); //147.26.195.92/161"); 
     manager.start();
        
     OID[] oids = manager.buildOidList(".1.3.6.1.2.1.1.1", 2);
     manager.walk(oids);
        
     for(OID oid : oids){
     System.out.println(manager.);
     }
     //PDU myPdu = manager.getResponse(oids);
     //manager.printResponse(myPdu);
            
     } catch (IOException ex) {
     Logger.getLogger(Dash.class.getName()).log(Level.SEVERE, null, ex);
     } catch (Exception ex) {
     Logger.getLogger(Dash.class.getName()).log(Level.SEVERE, null, ex);
     }
     }
     */
}
