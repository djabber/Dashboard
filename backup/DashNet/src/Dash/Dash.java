package Dash;

import Net.SysInfo;
import Net.Communicator;
import Net.Client;
import Snmp.OidParser;
import Snmp.Snmp;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dash{

    public static void main(String[] args){

        try{
            
            OidParser parser = new OidParser("http://oid-info.com/get/1.3.6.1.2.1.1.1");
            //Snmp snmp = new Snmp();
            //SysInfo sys = new SysInfo();
            //Client c = new Client("localhost", 10000, sys.getSysInfo());
            
        } catch(Exception ex){
            Logger.getLogger(Dash.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
