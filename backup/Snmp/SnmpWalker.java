package Snmp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;


public class SnmpWalker {

    private static final int MAX_REPETITIONS = 2500;
    private static final int MAX_SIZE_REQUEST_PDU = 65535;
    private String startOID;
    private Snmp snmp;
    HashMap<String, String> hashMap;
    private CommunityTarget target;
    
    
    public SnmpWalker(String startOID) throws IOException{
        
        this.startOID = startOID;
        hashMap = new HashMap<>();
        init();
    }
    
    private void init() throws IOException{
    
        getTarget();
        getSnmp();
    }

    public void snmpWalk(String start) throws IOException {
    
        String oid = start;

        while(oid.startsWith(start)){
            
            PDU pduResponse = getVariableBinding(new OID(oid));
            Vector<? extends VariableBinding> vbs = pduResponse.getVariableBindings();
             
            if(pduResponse == null){ throw new RuntimeException("PDU response is null!"); } 
            if(pduResponse.size() == 0){ throw new RuntimeException("PDU response is empty!"); }
            
            for(int i = 0; i < pduResponse.size(); i++){
           
                VariableBinding vb = pduResponse.get(i);
                
                if(vb == null){ throw new RuntimeException("Variable Binding is null!"); }

                oid = vb.getOid().toString();
            
                String v = vbs.get(i).getVariable().toString();
                //String v = myVB.getVariable().toString(); //vb.getVariable().getSyntaxString()
                System.out.println("oid = " + oid + "\n\tvb.getVar.toStr = " + vb.getVariable().toString() + "\n\tv = " + v);
                
                hashMap.put(oid, vb.getVariable().toString());
            }
        }
    }

    public PDU getVariableBinding(OID oid) throws IOException {
        
        ResponseEvent event = get(new OID[]{oid});

        if(event == null){ throw new RuntimeException("Event is null"); }
        if(event.getResponse() == null){ throw new RuntimeException("The getResponse method of Event returned null"); }

        return event.getResponse();
    }

    public ResponseEvent get(OID oids[]) throws IOException {
        
        PDU pdu = new PDU();
        
        for (OID oid : oids) {
            
            pdu.add(new VariableBinding(oid));
        }
        
        pdu.setType(PDU.GETBULK);
        pdu.setMaxRepetitions(MAX_REPETITIONS);

        ResponseEvent event = snmp.send(pdu, target);
        
        if (event != null) { return event; }
        throw new RuntimeException("GET timed out");
    }
    
    private void getTarget() {
        
        target = new CommunityTarget();
        target.setCommunity(new OctetString("public"));
        target.setAddress(new UdpAddress("127.0.0.1/161"));
        target.setRetries(2);
        target.setTimeout(3000);
        target.setVersion(SnmpConstants.version2c);
        target.setMaxSizeRequestPDU(MAX_SIZE_REQUEST_PDU);
    }

    private void getSnmp() throws IOException {

        snmp = new Snmp(new DefaultUdpTransportMapping());
        snmp.listen();
    }
    
     public void printList(){
              
        int c = 0;
        Iterator<String> keySetIterator = hashMap.keySet().iterator();

        while(keySetIterator.hasNext()){
            String key = keySetIterator.next();
            System.out.println(c++ + ": " + hashMap.get(key));
        }
    }
}
