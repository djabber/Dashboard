package Snmp;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.snmp4j.smi.OID;

public class Snmp extends AbstractGet{

    public Snmp(){

        try{
            RequestBuilder builder = new RequestBuilder.Builder().address("147.26.195.63/161")
                    .community("txssc")
                    .build();

            OID[] oids = oidBuilder();

            print(" ****** GET BULK ****** ");
            GetBulk bulk = new GetBulk(new OID());
            bulk.print();

            System.out.println("\n\n ****** GET Multiple ****** ");
            GetMultiple mult = new GetMultiple(".1.3.6.1.2.1.1.1.0");
            mult.printList();

            System.out.println("\n\n ****** GET Next - Walk ****** ");
            GetNext next = new GetNext();
            next.walk(oids);
            
            System.out.println("\n\n ****** GET AS STRING ****** ");
            for(OID oid : oids){
                System.out.println(RequestBuilder.getAsString(oid));
            }

        } catch(IOException ex){
            Logger.getLogger(Snmp.class.getName()).log(Level.SEVERE, null, ex);
        } catch(Exception ex){
            Logger.getLogger(Snmp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static OID[] oidBuilder(){

        // System Name = ".1.3.6.1.2.1.1.5.0"
        // System Description = ".1.3.6.1.2.1.1.1.0"
        // System up-time = ".1.3.6.1.2.1.1.3.0"
        // System Temperature = ".1.3.6.1.4.1.674.10890.1.1.2.1.5"
        OID[] oids = new OID[4];

        OID name = new OID(".1.3.6.1.2.1.1.5.0");
        OID desc = new OID(".1.3.6.1.2.1.1.1.0");
        OID uptime = new OID(".1.3.6.1.2.1.1.3.0");
        OID temp = new OID(".1.3.6.1.4.1.674.10890.1.1.2.1.5");

        oids[0] = name;
        oids[1] = desc;
        oids[2] = uptime;
        oids[3] = temp;

        return oids;
    }
}
