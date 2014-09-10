package dashnet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DashNet{

    public static void main(String[] args){
        
        try{
            
            Client client = new Client();
            SysInfo sys = new SysInfo();
            String data = sys.getSysInfo();
            
            client.sendInfo(data);
            
        }catch(IOException ex){
            Logger.getLogger(DashNet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}