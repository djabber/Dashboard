package Snmp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.ScopedPDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public abstract class AbstractGet{   
        
    private static final String CLASSNAME = "AbstractGet";
    
    public static int hashCnt = 0;
    public static int retries;
    public static int timeout;
    public static int nonRepeaters;
    public static int maxRepetitions;
    public static int maxSizeRequestPDU;
    public static int version;
    public static String community;
    public static String address;
    public static Snmp snmp; 
    public static PDU pdu, response;
    public static ScopedPDU scopedPDU;
    public static ResponseEvent event;
    public static CommunityTarget target;
    public static HashMap<String, String> hashMap;
    
    public static PDU getPDU(){ return pdu; }
    public static PDU getScopedPDU(){ return scopedPDU; }
    public static int getAsInt(OID oid) throws IOException{ return getResponse(oid).toInt(); }
    public static void stop() throws IOException{ snmp.close(); }
    public static void print(String msg){ System.out.println(msg); }
    
    public static void init() throws IOException{

        hashMap = new HashMap<>();
        getTarget();
        getSnmp();
    }

    public static String getAsString(OID oid) throws IOException{ 
        
        getPDU(oid, PDU.GET);
        return getResponse(oid).toString(); 
    }
    
    private static void getTarget(){

        target = new CommunityTarget();
        target.setCommunity(new OctetString(community));
        target.setVersion(version);
        target.setAddress(new UdpAddress(address));
        target.setTimeout(timeout);
        target.setRetries(retries);
        target.setMaxSizeRequestPDU(maxSizeRequestPDU);
    }

    private static void getSnmp() throws IOException{

        snmp = new Snmp(new DefaultUdpTransportMapping());
        snmp.listen();
    }

    public static void getPDU(OID oid, int type){

        pdu = new PDU();
        pdu.setType(type);
        pdu.setMaxRepetitions(maxRepetitions);
        pdu.setNonRepeaters(nonRepeaters);
        pdu.add(new VariableBinding(oid));
    }

    public static void getScopedPDU(OID[] oids, int type){
        
        scopedPDU = new ScopedPDU();
        
        for(OID oid : oids){
            scopedPDU.add(new VariableBinding(oid));
        }
        scopedPDU.setType(type);
        scopedPDU.setMaxRepetitions(maxRepetitions);
        scopedPDU.setNonRepeaters(nonRepeaters);
    }
    
    public static void getResponse() throws IOException {

        if(pdu == null){  myException(CLASSNAME, "n",  "getResponse", "PDU is null!"); }
        if(target == null){  myException(CLASSNAME, "n",  "getResponse", "Target is null!"); }
        event = snmp.send(pdu, target);
        if(event == null){ myException(CLASSNAME, "n",  "getResponse", "Event is null!"); }
        if(!event.getResponse().getErrorStatusText().equalsIgnoreCase("Success")){ myException(CLASSNAME, "r",  "getResponse", "Failed to get response!"); }
        response = event.getResponse();
        if(response == null){ myException(CLASSNAME, "n", "getResponse", "Response is null!"); }
    }

    public static Variable getResponse(OID oid) throws IOException{

        getResponse();
        
        if(response.size() > 0){
            return response.get(0).getVariable();
        }
        myException(CLASSNAME, "i", "getResponse(oid)", "Unable to obtain response from server!");
        return null;
    }    
        
    public void printList(){
              
        int c = 0;
        Iterator<String> keySetIterator = hashMap.keySet().iterator();

        while(keySetIterator.hasNext()){
            String key = keySetIterator.next();
            System.out.println(c++ + ": " + hashMap.get(key));
        }
    }
    
    public void printPDU(PDU pdu) throws Exception{
        
        if(pdu != null){
            VariableBinding[] vbArr = pdu.toArray();
        
            for (VariableBinding vb : vbArr) {
                System.out.println(vb.toString());
            }
        }else{
            myException(CLASSNAME, "n", "printPDU", "PDU is null!");
        }
    }
    
    public static void myException(String className, String exType, String function, String msg) throws IOException{
        
        switch(exType){
            case "r":
            case "R":
                throw new RuntimeException("Runtime Exception in " + function + ": " + msg);
            case "i":
            case "I":
                throw new IOException("IO Exception in " + function + ": " + msg);
            case "n":
            case "N":
                throw new NullPointerException("Null Pointer Exception in " + function + ": " + msg);
            
            default:
                throw new RuntimeException("Runtime Exception in " + function + ": " + msg);
        }
    }
}