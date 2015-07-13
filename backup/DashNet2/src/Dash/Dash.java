package Dash;

import Net.Client;
import SysInfo.SysInfo;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dash{

    public static void main(String[] args){

        try{
            
            //Snmp snmp = new Snmp();
            SysInfo sys = new SysInfo();
            Client c = new Client("localhost", 10000, sys.getSysInfo());
            
        } catch(Exception ex){
            Logger.getLogger(Dash.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}