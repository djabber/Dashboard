package Snmp;

import java.io.IOException;
import org.snmp4j.PDU;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;


public class GetNext extends AbstractGet{
    
    private final String CLASSNAME = "GetNext";
    
    public GetNext() throws IOException{
        
        init();
    }
    
    public void walk(OID[] oids) throws IOException, Exception{
            
        for(OID oid : oids){
            
            getPDU(oid, PDU.GETNEXT);
            print(getResponse(oid).toString());
        }   
    }
}