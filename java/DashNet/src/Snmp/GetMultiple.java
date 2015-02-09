package Snmp;

import java.io.IOException;
import java.util.Vector;
import org.snmp4j.PDU;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;

public class GetMultiple extends AbstractGet{

    private final String CLASSNAME = "GetMultiple";

    public GetMultiple(String start) throws IOException{

        String oid = start;
        init();
        getPDU(new OID(oid), PDU.GETBULK);
        getResponse();
        Vector<? extends VariableBinding> vbs = response.getVariableBindings();

        while(oid.startsWith(start)){

            for(int i = 0; i < response.size(); i++){

                VariableBinding vb = response.get(i);
                
                
                OID tmpOID = vb.getOid();
                Variable tmpV = vb.getVariable();
                //String tmpStr = vb.toString();
                String tmpSyntaxStr = tmpV.getSyntaxString();

               AbstractPrint.print("OID:");
                if(tmpOID != null){AbstractPrint.print("OID = " + tmpOID); }
                if(tmpV != null){AbstractPrint.print("Variable = " + tmpV); }
                if(tmpSyntaxStr != null){AbstractPrint.print("Syntax String = " + tmpSyntaxStr); }
               AbstractPrint.print("\n");
                
                String trStr = response.toString();
                String trSynStr = response.get(0).getVariable().getSyntaxString();
                                
                AbstractPrint.print("Response:");
                //if(trStr != null){AbstractPrint.print("toString = " + trStr); }
                if(trSynStr != null){AbstractPrint.print("Variable getSyntaxString = " + trSynStr); }
               AbstractPrint.print("\n");
                

                if(vb != null){
                    oid = vb.getOid().toString();
                    String v = vbs.get(i).getVariable().toString();
                    hashMap.put(oid, v);
                }
            }
        }
        
       AbstractPrint.print(" ****** RESPONSE TOSTRING ****** ");
        String myStr = "";
        
        for(char c : response.toString().toCharArray()){
            if(c == ',' || c == ';'){
                myStr += "\n";
            }else{
                myStr += c;
            }
        }
       AbstractPrint.print(myStr);
       AbstractPrint.print(" ****** END OF RESPONSE TOSTRING ****** \n\n");
    }
}
