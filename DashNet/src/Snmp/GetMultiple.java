package Snmp;

import static Snmp.AbstractGet.getPDU;
import static Snmp.AbstractGet.getResponse;
import java.io.IOException;
import java.util.Vector;
import org.snmp4j.PDU;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;


public class GetMultiple extends AbstractGet{

    private static final String CLASSNAME = "GetMultiple";
        
    public GetMultiple(String start) throws IOException {
        
        String oid = start;
        init();
        getPDU(new OID(oid), PDU.GETBULK);
        getResponse();    
        Vector<? extends VariableBinding> vbs = response.getVariableBindings();
        
        while(oid.startsWith(start)){
            
            for(int i = 0; i < response.size(); i++){
           
                VariableBinding vb = response.get(i);
                if(vb != null){ 
                    oid = vb.getOid().toString();            
                    String v = vbs.get(i).getVariable().toString();
                    hashMap.put(oid, v);
                }
            }
        }
    }
}