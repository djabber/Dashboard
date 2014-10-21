package SnmpManager;

import java.util.List;
import org.snmp4j.CommunityTarget;
import org.snmp4j.Session;
import org.snmp4j.Target;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.util.PDUFactory;
import org.snmp4j.util.TreeEvent;
import org.snmp4j.util.TreeUtils;



public class TreeWalk{

    String address;
    
    public TreeWalk(String add){ 
        address = add;
        Session
        TreeUtils tree = new TreeUtils()
    }
    
    public void walkTree(String start, int depth){
        
        OID oid = new OID(start);
        List<TreeEvent> list = getSubtree(getTarget(), oid);
        
        for(TreeEvent e : list){
            VariableBinding[] vbArr = e.getVariableBindings();
            for(VariableBinding vb : vbArr){
                System.out.println(vb.toString());
            }
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

    
}
