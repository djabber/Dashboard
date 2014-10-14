package SnmpManager;

import java.io.IOException;
import static java.lang.System.exit;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;


public class Manager {                     

    Snmp snmp = null;
    String address = null;
    PDU pdu = null;
    OID oids[] = new OID[999999];
    int oidCnt = 0;
    int depth = 0; 
    
    public Manager(String add){

        address = add;
    }

    public void start() throws IOException {

        TransportMapping transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);
        transport.listen();
    }
   
    public String getAsString(OID oid) throws IOException {

        ResponseEvent event = get(new OID[] { oid });
        return event.getResponse().get(0).getVariable().toString();
    }
    
    public ResponseEvent get(OID oids[]) throws IOException {
        
        pdu = new PDU();
        
        for (OID oid : oids){
            pdu.add(new VariableBinding(oid));
        }
        pdu.setType(PDU.GET);
        ResponseEvent event = snmp.send(pdu, getTarget(), null);
        
        if(event != null){
            return event;
        }
        throw new RuntimeException("GET timed out");
    }
    
    public void walk(String start, int d){
       
        depth = d;
        walkHelper(start, -1);
        
        System.out.println("***** OIDS *****");
        for(int i = 0; i < oidCnt; i++){
            System.out.println(oids[i].toString());
        }   
    }
    
    private void walkHelper(String str, int i){
                
        System.out.println("depth = " + depth);
        if(depth >= 0){
            // Get end number from string
            String s = getNumString(str, "");
            System.out.println("1: s=" + s);
        
            // Remove end number from string
            str = str.substring(0, (str.length() - s.length()));
            i = Integer.parseInt(s);
      
            System.out.println("2: str=" + str + ", i = " + i);
       
            if(i >= 9){    
                str = str.concat((i + "."));
                str = str.concat(("" + 0));
                System.out.println("3: str=" + str);
                depth--;
                walkHelper(str, 1);
            }else{
                str = str.concat(String.valueOf(++i));
                oids[oidCnt++] = new OID(str);
                walkHelper(str, i);
            }
        }
    }
    
    private String getNumString(String str, String hold){
        
        // Get last character
        String c = Character.toString(str.charAt(str.length()-1));
        
        // Remove character from end
        str = str.substring(0, str.length()-1);
            
        if(c.equals(".")){
            return hold;
        }else{
            return getNumString(str, (c + hold));
        }
    }
    
    public PDU getResponse(OID[] oids) throws IOException{
       
        ResponseEvent event = get(oids);
        return event.getResponse();
    }
    
    public void printResponse(PDU pdu){
        
        int i = 0;
        VariableBinding[] vbArr = pdu.toArray();
        
        for (VariableBinding vb : vbArr) {
            System.out.println("Response" + i++ + ": " + vb.toString());
        }
    }
    
    private Target getTarget() {
        
        Address targetAddress = GenericAddress.parse(address);
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("txssc"));
        target.setAddress(targetAddress);
        target.setRetries(5);
        target.setTimeout(3000);
        target.setVersion(SnmpConstants.version2c);
        
        return target;
    }
    
    class WalkCounts{
        public int requests;
        public int objects;
    }
}