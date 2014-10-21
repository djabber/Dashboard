package Dash;

import Snmp.Manager;
import Snmp.SnmpGetBulk;
import Snmp.SnmpRequest;
import Snmp.SnmpWalker;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;

public class Dash {

    public static void main(String[] args) {

        try {
 
            
            
            
 /*
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
*/
        } catch (IOException ex) {
            Logger.getLogger(Dash.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
