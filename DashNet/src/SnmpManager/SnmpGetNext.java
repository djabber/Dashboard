package SnmpManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.CommunityTarget;


public class SnmpGetNext extends Thread {

    private static final String END = "endofmibview";
    private static int hashCnt;
    private static int hitCnt;
    private static int keyCollisionCnt;
    private static int valueCollisionCnt;
    private static HashMap hashMap;
    private static Thread t1;
    private static Snmp snmp;
    private static CommunityTarget  target;
    private static PDU pdu, response;

    public SnmpGetNext() throws IOException{
        
        hitCnt = 0;
        hashCnt = 0;
        keyCollisionCnt = 0;
        valueCollisionCnt = 0;
        hashMap = new HashMap();
    }

    private void init(String oid) throws IOException{
    
        getTarget();
        getSnmp();
        getPDU(oid);
    }
    
    private void getTarget(){
    
        target = new CommunityTarget();
        target.setCommunity(new OctetString("txssc"));
        target.setVersion(SnmpConstants.version2c);
        target.setAddress(new UdpAddress("147.26.195.92/161"));
        target.setTimeout(5000);    
        target.setRetries(3);
    }
    
    private void getSnmp() throws IOException{
        
        snmp = new Snmp(new DefaultUdpTransportMapping());
        snmp.listen();            
    }
    
    private void getPDU(String oid){
        
        pdu = new PDU();
        pdu.setType(PDU.GETNEXT);
        pdu.setMaxRepetitions(2000);
        pdu.setNonRepeaters(0);
        pdu.add(new VariableBinding(new OID(oid)));            
    }
    
    public boolean getNext(String oid) throws IOException{

        init(oid);
        ResponseEvent responseEvent = snmp.getNext(pdu, target);
        
        response = responseEvent.getResponse();
        
        if(response == null){
            System.out.println("Error: GetNextResponse PDU is null");
        }else{
            if (response.getErrorStatus() == PDU.noError){
                Vector<? extends VariableBinding> vbs = response.getVariableBindings();
                for (VariableBinding vb : vbs) {
                    String v = vb + " ," + vb.getVariable().getSyntaxString();
                    String k = vb.getOid().toString();
                    if(! hashMap.containsKey(k)){
                        if(!hashMap.containsValue(v)){
                            System.out.println(v);
                            hashMap.put(k, v);
                            hashCnt++;
                            return true;
                        }else{
                            valueCollisionCnt++;
                        }
                    }else{
                        keyCollisionCnt++;
                    }
                    hitCnt++;
                }
            }else{
                System.out.println("Error:" + response.getErrorStatusText());
            }
        }
        return false;
    }
}
