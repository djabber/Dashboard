package Snmp;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import org.snmp4j.PDU;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;

public class GetBulk extends AbstractGet{

    private final String CLASSNAME = "GetBulk";

    private int keyCollisionCnt = 0;
    private int valueCollisionCnt = 0;
    private int hitCnt = 0;
    private OID[] keys;
    private String[] values;
    
    public GetBulk(OID oid) throws IOException{

        init();
        getPDU(oid, PDU.GETBULK);
        getResponse();

        if(response == null){
            System.out.println("TimeOut...");
        } else{
            if(response.getErrorStatus() == PDU.noError){
                Vector<? extends VariableBinding> pduVBs = pdu.getVariableBindings();
                Vector<? extends VariableBinding> responseVBs = response.getVariableBindings();
                for(VariableBinding vb : responseVBs){
                   
                    OID tmpOID = vb.getOid();
                    Variable tmpV = vb.getVariable();
                    String tmpStr = vb.toString();
                    String tmpValStr = vb.toValueString();
                    String tmpSyntaxStr = tmpV.getSyntaxString();
                    
                    print("OID = " + tmpOID);
                    print("Variable = " + tmpV);
                    print("String = " + tmpStr);
                    print("Value String = " + tmpValStr);
                    print("Syntax String = " + tmpSyntaxStr + "\n");
                    
                    
                    if(!isNum(vb.getVariable().toString())){
                        String v = vb + " ," + vb.getVariable().getSyntaxString();
                    
                        String k = vb.getOid().toString();
                        if(!hashMap.containsKey(k)){
                            if(!hashMap.containsValue(v)){
                                hashMap.put(k, v);
                                hashCnt++;
                            } else{
                                valueCollisionCnt++;
                            }
                        } else{
                            keyCollisionCnt++;
                        }
                        hitCnt++;
                    }
                }
            } else{
                System.out.println("Error:" + response.getErrorStatusText());
            }
        }
    }
     
    private boolean isNum(String s){

        for(char c : s.toCharArray()){
            if(!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    public void print(){

        int c = 0;
        Map sortedMap = new TreeMap<>(hashMap);
        Iterator<String> keySetIterator = sortedMap.keySet().iterator();

        while(keySetIterator.hasNext()){
            String key = keySetIterator.next();
            System.out.println(c++ + ": " + sortedMap.get(key));
        }
        printStats();
    }

    public void printStats(){

        int totalCollisionCnt = keyCollisionCnt + valueCollisionCnt;
        int totalCnt = hashCnt + totalCollisionCnt;
        int diff = (hitCnt - totalCnt);
        System.out.println("\n\nHash Count = " + hashCnt + "\n\nTotals: " + "\n\tTotal Collision Count = " + totalCollisionCnt + "\n\tHash " + hashCnt + " + " + " Collision Count " + totalCollisionCnt + " = " + totalCnt + "\n\tTotal Hit Count = " + hitCnt + "\n\tDifference = " + diff);
    }
}