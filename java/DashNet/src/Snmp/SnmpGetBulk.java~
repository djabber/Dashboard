package Snmp;

import com.agentpp.smiparser.SMIParser;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;


public class SnmpGetBulk extends Thread{

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
    private static String last;
    private static OID[] keys;
    private static String[] values;
    
    public SnmpGetBulk() throws IOException{
        
        hitCnt = 0;
        hashCnt = 0;
        keyCollisionCnt = 0;
        valueCollisionCnt = 0;
        hashMap = new HashMap();
        init();
    }
    
    public HashMap getHashMap(){ return hashMap; }
    public int getHashMapCnt(){ return hashCnt; }
    public String getLastOID(){ return last; }

    private void init() throws IOException{
    
        getTarget();
        getSnmp();
        //getResponse();
    }
    
    private void getTarget(){
    
        target = new CommunityTarget();
        target.setCommunity(new OctetString("txssc"));
        target.setVersion(SnmpConstants.version2c);
        target.setAddress(new UdpAddress("147.26.195.92/161"));
        target.setTimeout(5000);    
        target.setRetries(3);
        target.setMaxSizeRequestPDU(65535);
    }
    
    private void getSnmp() throws IOException{
        
        snmp = new Snmp(new DefaultUdpTransportMapping());
        snmp.listen();            
    }
    
    private void getPDU(String oid){
        
        pdu = new PDU();
        pdu.setType(PDU.GETBULK);
        pdu.setMaxRepetitions(50); //2500);
        pdu.setNonRepeaters(0);
        pdu.add(new VariableBinding(new OID(oid)));            
    }
    
    private void getResponse() throws IOException{
        
        ResponseEvent responseEvent = snmp.send(pdu, target);
        response = responseEvent.getResponse();   
    }
    
    @Override
    public void run(){
        
        //getBulk();
    }
    
    public void getBulk2(String oid) throws IOException{
        
          

        do{
            init();
        getPDU(oid);     
        ResponseEvent response = snmp.getBulk(pdu,target);
        
        if(response != null) {
            if(response.getResponse().getErrorStatusText().equalsIgnoreCase("Success")) {
                PDU pduResponse = response.getResponse();
                
                Vector<? extends VariableBinding> vbs = pduResponse.getVariableBindings();
                for (VariableBinding vb : vbs) {
                    String v = vb.getVariable().toString(); //vb.getVariable().getSyntaxString()
                    if(!isNum(v)){
                        //System.out.println("vb = " + vb + ", oid = " + vb.getOid() + ", syntax = " + vb.getSyntax() + ", variable = " + vb.getVariable() + "\n");
                        String k = vb.getOid().toString();
                        if(! hashMap.containsKey(k)){
                            if(!hashMap.containsValue(v)){
                                //System.out.println("v = " + v);
                                hashMap.put(k, v);
                                last = k;
                                hashCnt++;
                            }else{
                                valueCollisionCnt++;
                            }
                        }else{
                            keyCollisionCnt++;
                        }
                        hitCnt++;
                    }
                }
            }
        }
    }while(response != null);
        System.out.println("response was null");
    }
    
    private boolean isNum(String s){
 
        for(char c : s.toCharArray()){
            if(!Character.isDigit(c)) return false;
        }
        return true;
    }
        
    public void getBulk(String oid) throws IOException{
      
        init();
        getPDU(oid);
        getResponse();
        
        if (response == null) {
            System.out.println("TimeOut...");
        }else{
            if (response.getErrorStatus() == PDU.noError){
                Vector<? extends VariableBinding> vbs = response.getVariableBindings();
                for (VariableBinding vb : vbs) {
                    String v = vb + " ," + vb.getVariable().getSyntaxString();
                    String k = vb.getOid().toString();
                    if(! hashMap.containsKey(k)){
                        if(!hashMap.containsValue(v)){
                            hashMap.put(k, v);
                            last = k;
                            hashCnt++;
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
    }

  
    private void hashToArray(){
   
        int cnt = 0;
        keys = new OID[hashMap.size()];
        values = new String[hashMap.size()];
        Iterator<String> keySetIterator = hashMap.keySet().iterator();
        
        while(keySetIterator.hasNext()){
            String key = keySetIterator.next();
            keys[cnt] = new OID(key);
            values[cnt++] = hashMap.get(key).toString();
        }
    }
    
    public void sortMap(){
        
        int c = 0;
        Map sortedMap = new TreeMap(hashMap);
        
        Iterator<String> keySetIterator = sortedMap.keySet().iterator(); 

        while(keySetIterator.hasNext()){
            String key = keySetIterator.next();
            System.out.println(c++ + ": " + key + " = " + hashMap.get(key));
        }
    }
        
    public void writeList(String filePath) throws IOException{
        
        File file = new File(filePath);
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        Iterator<String> keySetIterator = hashMap.keySet().iterator();
        
        if(!file.exists()){ file.createNewFile(); }
        
        while(keySetIterator.hasNext()){
            String key = keySetIterator.next();
            bw.write( (hashMap.get(key) + "\n") );
        }
        bw.close();
    }
    
    public void printList(){
      
        sortMap();
        
        int c = 0;
        
        Iterator<String> keySetIterator = hashMap.keySet().iterator();

        while(keySetIterator.hasNext()){
            String key = keySetIterator.next();
            System.out.println(c++ + ": " + hashMap.get(key));
        }
        int totalCollisionCnt = keyCollisionCnt + valueCollisionCnt;
        int totalCnt = hashCnt + totalCollisionCnt;
        int diff = (hitCnt - totalCnt);
        System.out.println("\n\nHash Count = " + hashCnt + "\n\nTotals: " + "\n\tTotal Collision Count = " + totalCollisionCnt + "\n\tHash " + hashCnt + " + " + " Collision Count " + totalCollisionCnt + " = " + totalCnt + "\n\tTotal Hit Count = " + hitCnt + "\n\tDifference = " + diff);
    }
}