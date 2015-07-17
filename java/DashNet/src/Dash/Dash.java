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
            Client c = new Client("147.26.195.136", 10000, sys.getSysInfo());
            //Client c = new Client("127.0.0.1", 10000, sys.getSysInfo());
            
            /*
            if(args[0] == null){ args[0] = "127.0.0.1"; }
            if(args[1] == null){ args[1] = String.valueOf(10000); }
            
            Client c = new Client(args[0], Integer.parseInt(args[1]), sys.getSysInfo());
            */
        } catch(Exception ex){
            Logger.getLogger(Dash.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}