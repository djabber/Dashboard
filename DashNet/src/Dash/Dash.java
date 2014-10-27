package Dash;

import Net.SysInfo;
import Net.Communicator;
import Net.Client;
import Snmp.Snmp;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dash{

    public static void main(String[] args){

        try{
            
            Snmp snmp = new Snmp();
            SysInfo sys = new SysInfo();
            Client c = new Client("localhost", 10000, sys.getSysInfo());
            
        } catch(IOException ex){
            Logger.getLogger(Dash.class.getName()).log(Level.SEVERE, null, ex);
        } catch(Exception ex){
            Logger.getLogger(Dash.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}