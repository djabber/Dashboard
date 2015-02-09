package Snmp;

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
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;


public class Manager {                     

    Snmp snmp = null;
    String address = null;
    PDU pdu = null;
    OID[] oids = new OID[999999];
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
        if(event == null){ System.out.println("Event is null!"); }
        PDU p = new PDU();
        p = event.getResponse();
        if(p == null){ System.out.println("PDU p is null!"); }
        VariableBinding vb = p.get(0);
        if(vb == null){ System.out.println("VariableBinding vb is null!"); }
        Variable v = vb.getVariable();
        if(v == null){ System.out.println("Variable v is null!"); }
        String s = event.getResponse().get(0).getVariable().toString();
        System.out.println("GET_AS_STRING: " +  s);
        return s; //event.getResponse().get(0).getVariable().toString();
    }
    
    public ResponseEvent get(OID oids[]) throws IOException {
        
        pdu = new PDU();
        
        for (OID oid : oids){
            if(oid != null){
                System.out.println("oid = " + oid);
                pdu.add(new VariableBinding(oid));
            }else{
                break;
            }
        }
        
        pdu.setType(PDU.GET);
        ResponseEvent event = snmp.send(pdu, getTarget()); //, null);
        
        if(event != null){
            return event;
        }
        throw new RuntimeException("GET timed out");
    }
    
    public void walk(OID[] oids) throws IOException, Exception{
        
        PDU myPdu = getResponse(oids);
        
        if(myPdu != null){
            printResponse(myPdu);
        }
        System.out.println("Walk PDU is null!");        
    }
    public OID[] buildOidList(String start, int d) throws IOException, Exception{
       
        depth = d;
        buildOidListHelper(start, -1);
        return oids;
    }
    
    private void buildOidListHelper(String str, int i){
                
        if(depth >= 0){
            // Get end number from string
            String s = getNumString(str, "");
            
            // Remove end number from string
            str = str.substring(0, (str.length() - s.length()));
            i = Integer.parseInt(s);
      
            if(i >= 9){    
                str = str.concat((i + "."));
                str = str.concat(("" + 0));
                depth--;
                buildOidListHelper(str, 1);
            }else{
                str = str.concat(String.valueOf(++i));
                oids[oidCnt++] = new OID(str);
                buildOidListHelper(str, i);
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
        
        if(event == null){

            System.out.println("GetResponse event is null!");
        }
        return event.getResponse();
    }
    
    public void printResponse(PDU pdu) throws Exception{
        
        if(pdu != null){
            int i = 0;
            VariableBinding[] vbArr = pdu.toArray();
        
            for (VariableBinding vb : vbArr) {
                System.out.println("Response" + i++ + ": " + vb.toString());
            }
        }else{
            System.out.println("PDU is null!");
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