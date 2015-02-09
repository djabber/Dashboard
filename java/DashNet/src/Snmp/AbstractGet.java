package Snmp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.ScopedPDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public abstract class AbstractGet{   
        
    private final String CLASSNAME = "AbstractGet";
    
    public int hashCnt = 0;
    public int retries;
    public int timeout;
    public int nonRepeaters;
    public int maxRepetitions;
    public int maxSizeRequestPDU;
    public int version;
    public String community;
    public String address;
    public Snmp snmp; 
    public PDU pdu, response;
    public ScopedPDU scopedPDU;
    public ResponseEvent event;
    public CommunityTarget target;
    public HashMap<String, String> hashMap;
    
    public PDU getPDU(){ return pdu; }
    public PDU getScopedPDU(){ return scopedPDU; }
    public int getAsInt(OID oid) throws IOException{ return getResponse(oid).toInt(); }
    public void stop() throws IOException{ snmp.close(); }
    public void print(String msg){ System.out.println(msg); }
    
    public void init() throws IOException{

        hashMap = new HashMap<>();
        getTarget();
        getSnmp();
    }

    public String getAsString(OID oid) throws IOException{ 
        
        getPDU(oid, PDU.GET);
        return getResponse(oid).toString(); 
    }
    
    private void getTarget(){

        target = new CommunityTarget();
        target.setCommunity(new OctetString(community));
        target.setVersion(version);
        target.setAddress(new UdpAddress(address));
        target.setTimeout(timeout);
        target.setRetries(retries);
        target.setMaxSizeRequestPDU(maxSizeRequestPDU);
    }

    private void getSnmp() throws IOException{

        snmp = new Snmp(new DefaultUdpTransportMapping());
        snmp.listen();
    }

    public void getPDU(OID oid, int type){

        pdu = new PDU();
        pdu.setType(type);
        pdu.setMaxRepetitions(maxRepetitions);
        pdu.setNonRepeaters(nonRepeaters);
        pdu.add(new VariableBinding(oid));
    }

    public void getScopedPDU(OID[] oids, int type){
        
        scopedPDU = new ScopedPDU();
        
        for(OID oid : oids){
            scopedPDU.add(new VariableBinding(oid));
        }
        scopedPDU.setType(type);
        scopedPDU.setMaxRepetitions(maxRepetitions);
        scopedPDU.setNonRepeaters(nonRepeaters);
    }
    
    public void getResponse() throws IOException {

        if(pdu == null){  myException(CLASSNAME, "n",  "getResponse", "PDU is null!"); }
        if(target == null){  myException(CLASSNAME, "n",  "getResponse", "Target is null!"); }
        event = snmp.send(pdu, target);
        if(event == null){ myException(CLASSNAME, "n",  "getResponse", "Event is null!"); }
        if(!event.getResponse().getErrorStatusText().equalsIgnoreCase("Success")){ myException(CLASSNAME, "r",  "getResponse", "Failed to get response!"); }
        response = event.getResponse();
        if(response == null){ myException(CLASSNAME, "n", "getResponse", "Response is null!"); }
    }

    public Variable getResponse(OID oid) throws IOException{

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
    
    public void myException(String className, String exType, String function, String msg) throws IOException{
        
        switch(exType){
            case "r":
            case "R":
                throw new RuntimeException("Runtime Exception in " + function + ": " + msg);
            case "i":
            case "I":
                throw new IOException("IO Exception in " + function + ": " + msg);
            case "m":
            case "M":
                throw new MalformedURLException("Malformed URL Exception in " + function + ": " + msg);
            case "n":
            case "N":
                throw new NullPointerException("Null Pointer Exception in " + function + ": " + msg);
           
            default:
                throw new RuntimeException("Runtime Exception in " + function + ": " + msg);
        }
    }
}