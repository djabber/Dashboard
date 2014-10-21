package Snmp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.ScopedPDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SnmpRequest {

    private String address;
    private Snmp snmp;
    int depth; 
    List<OID> oids;

    public SnmpRequest(String address) throws IOException {
        
        depth = 0;
        this.address = address;
        this.oids = new ArrayList<>();
        start();
    }

    private void start() throws IOException {

        DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);
        transport.listen();
    }

    public void stop() throws IOException {
        snmp.close();
    }

    private Target createTarget() {

        Address targetAddress = GenericAddress.parse(address);
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("public"));
        target.setAddress(targetAddress);
        target.setRetries(3);
        target.setTimeout(1500);
        target.setVersion(SnmpConstants.version1);
        return target;
    }

    private PDU createGetPDU(OID oid) {

        PDU pdu = new PDU();
        pdu.setType(PDU.GET);
        pdu.add(new VariableBinding(oid));
        return pdu;
    }

    public Variable get(OID oid) throws IOException {

        PDU response = snmp.send(createGetPDU(oid), createTarget()).getResponse();
        if (response != null && response.size() > 0) {
            return response.get(0).getVariable();
        }
        throw new IOException("Unable to obtain response from server");
    }

    public void getBulk(){
        
        ScopedPDU pdu = new ScopedPDU();
        pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.2.1"))); // ifNumber
        pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.2.2.1.10"))); // ifInOctets
        pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.2.2.1.16"))); // ifOutOctets
        pdu.setType(PDU.GETBULK);
        pdu.setMaxRepetitions(50);
        
        // Get ifNumber only once
        pdu.setNonRepeaters(1);
        // set context non-default context (default context does not need to be set)
        pdu.setContextName(new OctetString("subSystemContextA"));
        // set non-default context engine ID (to use targets authoritative engine ID
        // use an empty (size == 0) octet string)
        pdu.setContextEngineID(OctetString.fromHexString("80:00:13:70:c0:a8:01:0d"));
    }
    
    public void walk(List<OID> oids) throws IOException, Exception {
        
        for(OID oid : oids){
            System.out.println(oid + " = " + getAsString(oid));
        }
    }

    public List<OID> buildOidList(String start, int d) {

        System.out.println("Building OID list...");
        depth = d;
        oids.add(new OID(start));
        buildOidListHelper(start, -1);
        return oids;
    }

    private void buildOidListHelper(String str, int i) {
        
        if (depth >= 0) {
            // Get end number from string
            String s = getNumString(str, "");

            // Remove end number from string
            str = str.substring(0, (str.length() - s.length()));
            i = Integer.parseInt(s);
            
            if (i >= 9) {
                str = str.concat((i + "."));
                str = str.concat(("" + 0));
                depth--;
                buildOidListHelper(str, 1);
            } else {
                
                str = str.concat(String.valueOf(++i));
                oids.add(new OID(str));
                buildOidListHelper(str, i);
            }
        }
    }

    private String getNumString(String str, String hold) {

        String c = Character.toString(str.charAt(str.length() - 1));            // Get last character
        str = str.substring(0, str.length() - 1);                               // Remove character from end

        if (c.equals(".")) {
            return hold;
        } else {
            return getNumString(str, (c + hold));
        }
    }
    
    public void printResponse(List<Variable> list) throws Exception{
        
        for(Variable v : list) {
            System.out.println(v.toString());
        }
    }

    public String getAsString(OID oid) throws IOException {
        return get(oid).toString();
    }

    public int getAsInt(OID oid) throws IOException {
        return get(oid).toInt();
    }
}
